package com.rs2.bot;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.gameplay.castlewars.CastleWarsEngineeringManager;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.path.PathFinder;

import java.util.IdentityHashMap;
import java.util.Map;

public final class CastleWarsBotRoleAi {
    private static final Map<BotPlayer, RoleState> states =
            new IdentityHashMap<BotPlayer, RoleState>();

    private CastleWarsBotRoleAi() {
    }

    public static void clear(BotPlayer bot) {
        states.remove(bot);
    }

    /**
     * @return true when this bot has a specialist role and this class owns its movement.
     *         False means the normal flag-runner AI should process it.
     */
    public static boolean process(BotPlayer bot) {
        CastleWarsManager.Team team = CastleWarsManager.getGameTeam(bot);
        if (team == null || bot.isDead() || !bot.isRegistered()) {
            states.remove(bot);
            return false;
        }

        RoleState state = states.get(bot);
        if (state == null || state.team != team
                || state.primaryCombatStyle != bot.botPrimaryCombatStyle) {
            state = new RoleState(team, bot.botPrimaryCombatStyle);
            states.put(bot, state);
        }
        if (state.role == Role.ATTACKER) {
            return false;
        }

        if (CastleWarsManager.isInTeamSpawnArea(bot, team)
                && !CastleWarsManager.isCarryingEnemyFlag(bot)
                && state.phase != Phase.SPAWN_SUPPLY
                && state.phase != Phase.LEAVE_SPAWN
                && state.phase != Phase.DESCEND_HOME
                && state.phase != Phase.DEFENDER_CLIMB) {
            state.resetForSpawn();
        }

        if (shouldUseBandage(bot)) {
            CastleWarsManager.useBandage(bot);
        }

        boolean prioritizeTraversal = isTraversalPhase(state.phase);
        if (prioritizeTraversal && bot.getCombatTarget() != null) {
            CombatManager.stopCombat(bot);
        }

        if (CastleWarsManager.isCarryingEnemyFlag(bot)) {
            Entity combatTarget = bot.getCombatTarget();
            if (combatTarget != null && !combatTarget.isDead()) {
                CombatManager.stopCombat(bot);
            }
        } else if (!CastleWarsManager.isInTeamSpawnArea(bot, team)
                && !prioritizeTraversal) {
            if (hasActiveOpponent(bot)) {
                return true;
            }
            int engageRadius = state.role == Role.MIDFIGHTER ? 16
                    : state.role == Role.DEFENDER ? 12 : 8;
            if (tryEngageNearbyOpponent(bot, state, engageRadius)) {
                return true;
            }
        }

        Position enemyBarricade = CastleWarsEngineeringManager.findNearestEnemyBarricade(bot, 3);
        if (enemyBarricade != null
                && bot.getInventoryManager().getItemAmount(
                        CastleWarsEngineeringManager.EXPLOSIVE_POTION_ID) > 0) {
            if (CastleWarsEngineeringManager.destroyBarricadeWithExplosive(bot, enemyBarricade)) {
                CastleWarsBotChat.sayEngineering(bot);
                state.delayTicks = 2;
                return true;
            }
        }

        if (state.delayTicks > 0) {
            --state.delayTicks;
            return true;
        }

        switch (state.phase) {
            case SPAWN_SUPPLY:
                processSpawnSupply(bot, state);
                break;
            case LEAVE_SPAWN:
                processLeaveSpawn(bot, state);
                break;
            case DESCEND_HOME:
                processDescendHome(bot, state);
                break;
            case GROUND_SUPPLY:
                processGroundSupply(bot, state);
                break;
            case EXIT_HOME:
                processGroundCastleExit(bot, state);
                break;
            case DEFENDER_CLIMB:
                processOwnFlagClimb(bot, state);
                break;
            case DEFEND_FLAG:
                processDefender(bot, state);
                break;
            case CATAPULT_MOVE:
                processCatapult(bot, state);
                break;
            case CATAPULT_ROAM:
                processCatapultRoam(bot, state);
                break;
            case MID_RUSH:
                processMidRush(bot, state);
                break;
            case MID_FIGHT:
                processMidFight(bot, state);
                break;
            case UNDERGROUND_DESCEND:
                processUndergroundDescent(bot, state);
                break;
            case UNDERGROUND_OUT:
                processUndergroundCross(bot, state, false);
                break;
            case ENEMY_CLIMB:
                processCastleClimb(bot, state, opposite(state.team), true, Phase.TAKE_FLAG);
                break;
            case TAKE_FLAG:
                processTakeFlag(bot, state);
                break;
            case ENEMY_DESCEND:
                processCastleClimb(bot, state, opposite(state.team), false, Phase.UNDERGROUND_RETURN_DESCEND);
                break;
            case UNDERGROUND_RETURN_DESCEND:
                processUndergroundReturnDescent(bot, state);
                break;
            case UNDERGROUND_BACK:
                processUndergroundCross(bot, state, true);
                break;
            case HOME_CLIMB:
                processCastleClimb(bot, state, state.team, true, Phase.CAPTURE_FLAG);
                break;
            case CAPTURE_FLAG:
                processCapture(bot, state);
                break;
            default:
                state.resetForSpawn();
                break;
        }
        return true;
    }

    private static void processSpawnSupply(BotPlayer bot, RoleState state) {
        if (!CastleWarsManager.isInTeamSpawnArea(bot, state.team)) {
            if (state.role == Role.UNDERGROUND) {
                state.phase = Phase.UNDERGROUND_DESCEND;
            } else if (state.role == Role.MIDFIGHTER) {
                state.phase = Phase.MID_RUSH;
            } else if (state.role == Role.CATAPULT) {
                state.phase = Phase.CATAPULT_MOVE;
            } else {
                state.phase = Phase.DEFENDER_CLIMB;
            }
            return;
        }

        if (!state.bandagesStocked) {
            makeInventorySpace(bot, 10);
            int amount = 6 + GameUtil.randomInt(4);
            if (CastleWarsManager.giveBandages(bot, amount) > 0) {
                bot.getUpdateState().setAnimation(881);
            }
            state.bandagesStocked = true;
            state.delayTicks = 1 + GameUtil.randomInt(4);
            return;
        }

        if (!state.utilityStocked) {
            if (state.role == Role.UNDERGROUND) {
                makeInventorySpace(bot, 7);
                // Reserve the non-consumable tunnel tool before expendable supplies so
                // an unexpectedly full loadout can never leave an underground bot
                // without a pickaxe.
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.BRONZE_PICKAXE_ID, 1);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.EXPLOSIVE_POTION_ID, 4);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.BARRICADE_ITEM_ID, 1);
            } else if (state.role == Role.CATAPULT) {
                makeInventorySpace(bot, 8);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.ROCK_ITEM_ID, 6);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.EXPLOSIVE_POTION_ID, 1);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.TOOLKIT_ID, 1);
            } else if (state.role == Role.DEFENDER) {
                makeInventorySpace(bot, 5);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.BARRICADE_ITEM_ID, 3);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.EXPLOSIVE_POTION_ID, 2);
            }
            state.utilityStocked = true;
            state.delayTicks = 1 + GameUtil.randomInt(3);
            return;
        }

        if (state.role == Role.DEFENDER) {
            state.phase = Phase.DEFENDER_CLIMB;
            return;
        }

        state.phase = Phase.LEAVE_SPAWN;
        state.repathDelay = 0;
    }

    private static void processLeaveSpawn(BotPlayer bot, RoleState state) {
        if (state.team == CastleWarsManager.Team.SARADOMIN) {
            Position barrier = new Position(2426, 3079, 1);
            if (!reachInteractionApproach(bot, state, barrier)) {
                return;
            }
            CastleWarsManager.handleFirstObjectAction(bot,
                    CastleWarsManager.SARADOMIN_ENERGY_BARRIER_ID, 2426, 3080);
        } else {
            Position barrier = new Position(2373, 3127, 1);
            if (!reachInteractionApproach(bot, state, barrier)) {
                return;
            }
            CastleWarsManager.handleFirstObjectAction(bot,
                    CastleWarsManager.ZAMORAK_ENERGY_BARRIER_ID, 2373, 3126);
        }
        state.phase = Phase.DESCEND_HOME;
        state.repathDelay = 0;
    }

    private static void processDescendHome(BotPlayer bot, RoleState state) {
        if (bot.getPosition().getPlane() == 0) {
            state.phase = Phase.GROUND_SUPPLY;
            return;
        }
        if (state.team == CastleWarsManager.Team.SARADOMIN) {
            useTraversal(bot, state, new Position(2420, 3080, 1), 4415, 2419, 3080);
        } else {
            useTraversal(bot, state, new Position(2379, 3127, 1), 4415, 2380, 3127);
        }
        if (bot.getPosition().getPlane() == 0) {
            state.phase = Phase.GROUND_SUPPLY;
        }
    }

    private static void processGroundSupply(BotPlayer bot, RoleState state) {
        Position supply = state.team == CastleWarsManager.Team.SARADOMIN
                ? new Position(2426, 3075, 0)
                : new Position(2373, 3131, 0);
        if (!near(bot, supply, 4)) {
            walk(bot, state, supply);
            return;
        }

        if (!state.utilityStocked) {
            if (state.role == Role.UNDERGROUND) {
                makeInventorySpace(bot, 7);
                // Reserve the non-consumable tunnel tool before expendable supplies so
                // an unexpectedly full loadout can never leave an underground bot
                // without a pickaxe.
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.BRONZE_PICKAXE_ID, 1);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.EXPLOSIVE_POTION_ID, 4);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.BARRICADE_ITEM_ID, 1);
            } else if (state.role == Role.CATAPULT) {
                makeInventorySpace(bot, 8);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.ROCK_ITEM_ID, 6);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.EXPLOSIVE_POTION_ID, 1);
                CastleWarsEngineeringManager.giveSupply(bot,
                        CastleWarsEngineeringManager.TOOLKIT_ID, 1);
            }
            bot.getUpdateState().setAnimation(881);
            state.utilityStocked = true;
            state.delayTicks = 2 + GameUtil.randomInt(4);
        }

        if (state.role == Role.UNDERGROUND) {
            state.phase = Phase.UNDERGROUND_DESCEND;
        } else if (state.role == Role.CATAPULT) {
            state.phase = Phase.EXIT_HOME;
        } else {
            state.phase = Phase.EXIT_HOME;
        }
    }

    private static void processGroundCastleExit(BotPlayer bot, RoleState state) {
        Position approach = state.team == CastleWarsManager.Team.SARADOMIN
                ? new Position(2417, 3077, 0)
                : new Position(2382, 3130, 0);
        if (!reachInteractionApproach(bot, state, approach)) {
            return;
        }
        if (!CastleWarsManager.moveBotThroughGroundCastleStairs(bot, state.team, false)) {
            state.repathDelay = 0;
            return;
        }
        if (state.role == Role.CATAPULT) {
            state.phase = Phase.CATAPULT_MOVE;
        } else if (state.role == Role.MIDFIGHTER) {
            state.phase = Phase.MID_RUSH;
            CastleWarsBotChat.sayMid(bot);
        } else {
            state.phase = Phase.CATAPULT_ROAM;
        }
        state.repathDelay = 0;
    }

    private static void processOwnFlagClimb(BotPlayer bot, RoleState state) {
        int plane = bot.getPosition().getPlane();
        if (plane == 1) {
            Position ladderApproach = state.team == CastleWarsManager.Team.SARADOMIN
                    ? new Position(2429, 3074, 1)
                    : new Position(2370, 3133, 1);
            if (!reachInteractionApproach(bot, state, ladderApproach)) {
                return;
            }
            if (state.team == CastleWarsManager.Team.SARADOMIN) {
                CastleWarsManager.handleFirstObjectAction(bot,
                        CastleWarsManager.SARADOMIN_SPAWN_LADDER_ID, 2429, 3075);
            } else {
                CastleWarsManager.handleFirstObjectAction(bot,
                        CastleWarsManager.ZAMORAK_SPAWN_LADDER_ID, 2370, 3132);
            }
            return;
        }
        if (plane >= 3) {
            state.phase = Phase.DEFEND_FLAG;
            return;
        }
        processCastleClimb(bot, state, state.team, true, Phase.DEFEND_FLAG);
    }

    private static void processDefender(BotPlayer bot, RoleState state) {
        Position[] spots = state.team == CastleWarsManager.Team.SARADOMIN
                ? new Position[]{
                    new Position(2427, 3075, 3),
                    new Position(2428, 3076, 3),
                    new Position(2426, 3076, 3)}
                : new Position[]{
                    new Position(2372, 3132, 3),
                    new Position(2371, 3131, 3),
                    new Position(2373, 3130, 3)};

        if (state.barricadesPlaced < spots.length
                && bot.getInventoryManager().getItemAmount(
                        CastleWarsEngineeringManager.BARRICADE_ITEM_ID) > 0
                && CastleWarsEngineeringManager.getBarricadeCount(state.team) < 10) {
            Position spot = spots[state.barricadesPlaced];
            if (!near(bot, spot, 0)) {
                walk(bot, state, spot);
                return;
            }
            if (CastleWarsEngineeringManager.placeBarricade(bot)) {
                ++state.barricadesPlaced;
                CastleWarsBotChat.sayDefence(bot);
                state.delayTicks = 2;
                return;
            }
            ++state.barricadesPlaced;
        }

        CastleWarsManager.Team ownFlagTeam = state.team;
        Player enemyHolder = CastleWarsManager.getFlagHolder(ownFlagTeam);
        if (enemyHolder != null && !enemyHolder.isDead()
                && enemyHolder.getPosition().getPlane() == bot.getPosition().getPlane()
                && GameUtil.getDistance(bot.getPosition(), enemyHolder.getPosition()) <= 15) {
            CombatManager.startCombat(bot, enemyHolder);
            return;
        }

        Position flag = state.team == CastleWarsManager.Team.SARADOMIN
                ? new Position(2429, 3074, 3)
                : new Position(2370, 3133, 3);
        if (!near(bot, flag, 5) && GameUtil.randomInt(4) == 0) {
            walk(bot, state, flag);
        }
    }

    private static void processMidRush(BotPlayer bot, RoleState state) {
        if (bot.getPosition().getPlane() != 0) {
            return;
        }
        if (tryEngageNearbyOpponent(bot, state, 16)) {
            state.phase = Phase.MID_FIGHT;
            return;
        }

        Position rally = midRallyPoint(state);
        if (!near(bot, rally, 4)) {
            walk(bot, state, rally);
            return;
        }

        state.phase = Phase.MID_FIGHT;
        state.midPatrolTicks = 12 + GameUtil.randomInt(18);
        state.repathDelay = 0;
    }

    private static void processMidFight(BotPlayer bot, RoleState state) {
        if (bot.getPosition().getPlane() != 0) {
            state.phase = Phase.MID_RUSH;
            return;
        }

        if (tryEngageNearbyOpponent(bot, state, 18)) {
            state.midPatrolTicks = 12 + GameUtil.randomInt(18);
            return;
        }

        if (--state.midPatrolTicks <= 0) {
            state.midPatrolTicks = 12 + GameUtil.randomInt(18);
            state.routeVariant = GameUtil.randomInt(3);
            state.routeOffsetX = -4 + GameUtil.randomInt(9);
            state.routeOffsetY = -4 + GameUtil.randomInt(9);
            if (GameUtil.randomInt(5) == 0) {
                CastleWarsBotChat.sayMid(bot);
            }
        }

        Position patrol = midRallyPoint(state);
        if (!near(bot, patrol, 3)) {
            walk(bot, state, patrol);
        }
    }

    private static Position midRallyPoint(RoleState state) {
        int x;
        int y;
        if (state.routeVariant == 0) {
            x = 2400;
            y = 3104;
        } else if (state.routeVariant == 1) {
            x = 2396;
            y = 3100;
        } else {
            x = 2404;
            y = 3108;
        }
        return new Position(x + state.routeOffsetX, y + state.routeOffsetY, 0);
    }

    private static void processCatapult(BotPlayer bot, RoleState state) {
        Position catapult = state.team == CastleWarsManager.Team.SARADOMIN
                ? CastleWarsEngineeringManager.SARADOMIN_CATAPULT
                : CastleWarsEngineeringManager.ZAMORAK_CATAPULT;
        if (!near(bot, catapult, 3)) {
            walk(bot, state, catapult);
            return;
        }

        if (!CastleWarsEngineeringManager.isCatapultOperational(state.team)) {
            if (CastleWarsEngineeringManager.repairOwnCatapult(bot)) {
                CastleWarsBotChat.sayCatapult(bot);
                state.delayTicks = 4;
            }
            return;
        }

        if (bot.getInventoryManager().getItemAmount(
                CastleWarsEngineeringManager.ROCK_ITEM_ID) <= 0) {
            state.phase = Phase.CATAPULT_ROAM;
            return;
        }

        if (CastleWarsEngineeringManager.fireCatapult(bot)) {
            if (GameUtil.randomInt(3) == 0) {
                CastleWarsBotChat.sayCatapult(bot);
            }
            state.delayTicks = 4;
        }
    }

    private static void processCatapultRoam(BotPlayer bot, RoleState state) {
        if (tryEngageNearbyOpponent(bot, state, 10)) {
            return;
        }
        Position mid = new Position(
                2395 + GameUtil.randomInt(12),
                3099 + GameUtil.randomInt(12),
                0);
        if (GameUtil.randomInt(5) == 0) {
            walk(bot, state, mid);
        }
    }

    private static void processUndergroundDescent(BotPlayer bot, RoleState state) {
        if (bot.getPosition().getY() >= 9400) {
            state.phase = Phase.UNDERGROUND_OUT;
            state.undergroundStage = 0;
            state.repathDelay = 0;
            return;
        }

        if (state.team == CastleWarsManager.Team.SARADOMIN) {
            Position ladder = new Position(2430, 3082, 0);
            if (!near(bot, ladder, 1)) {
                walk(bot, state, ladder);
                return;
            }
            CastleWarsManager.handleFirstObjectAction(bot, 4912, 2430, 3082);
        } else {
            Position ladder = new Position(2369, 3125, 0);
            if (!near(bot, ladder, 1)) {
                walk(bot, state, ladder);
                return;
            }
            CastleWarsManager.handleFirstObjectAction(bot, 4912, 2369, 3125);
        }
    }

    private static void processUndergroundCross(BotPlayer bot, RoleState state, boolean returningHome) {
        if (bot.getPosition().getY() < 9400) {
            if (returningHome) {
                state.phase = Phase.HOME_CLIMB;
            } else {
                state.phase = Phase.ENEMY_CLIMB;
            }
            return;
        }

        int[] rocks = undergroundRockRoute(state.team, state.routeVariant, returningHome);
        Position center = undergroundCenter(state.routeVariant);
        Position exit = undergroundExit(state.team, returningHome);

        if (state.pendingCollapseRock >= 0) {
            Position previous = CastleWarsEngineeringManager.getRockslidePosition(state.pendingCollapseRock);
            int previousDistance = previous == null ? Integer.MAX_VALUE
                    : GameUtil.getDistance(bot.getPosition(), previous);
            if (previous != null
                    && previousDistance >= 2 && previousDistance <= 4
                    && !CastleWarsEngineeringManager.isRockslideCollapsed(state.pendingCollapseRock)
                    && bot.getInventoryManager().getItemAmount(
                            CastleWarsEngineeringManager.EXPLOSIVE_POTION_ID) > 1
                    && GameUtil.randomInt(3) == 0) {
                CastleWarsEngineeringManager.collapseRockslide(bot, state.pendingCollapseRock);
                state.pendingCollapseRock = -1;
                CastleWarsBotChat.sayUnderground(bot);
            }
        }

        if (state.undergroundStage == 0) {
            if (processRockPoint(bot, state, rocks[0])) {
                state.pendingCollapseRock = rocks[0];
                state.undergroundStage = 1;
            }
            return;
        }
        if (state.undergroundStage == 1) {
            if (!near(bot, center, 3)) {
                walk(bot, state, center);
                return;
            }
            state.undergroundStage = 2;
            state.repathDelay = 0;
            return;
        }
        if (state.undergroundStage == 2) {
            if (processRockPoint(bot, state, rocks[1])) {
                state.pendingCollapseRock = rocks[1];
                state.undergroundStage = 3;
            }
            return;
        }

        if (!near(bot, exit, 2)) {
            walk(bot, state, exit);
            return;
        }

        if (exit.getX() == 2430 && exit.getY() == 9482) {
            CastleWarsManager.handleFirstObjectAction(bot, 1757, 2430, 9482);
        } else {
            CastleWarsManager.handleFirstObjectAction(bot, 1757, 2369, 9525);
        }

        state.undergroundStage = 0;
        state.pendingCollapseRock = -1;
        state.repathDelay = 0;
        state.phase = returningHome ? Phase.HOME_CLIMB : Phase.ENEMY_CLIMB;
    }

    private static boolean processRockPoint(BotPlayer bot, RoleState state, int rockIndex) {
        Position rock = CastleWarsEngineeringManager.getRockslidePosition(rockIndex);
        if (rock == null) {
            return true;
        }
        if (!near(bot, rock, 3)) {
            walk(bot, state, rock);
            return false;
        }
        if (CastleWarsEngineeringManager.isRockslideCollapsed(rockIndex)) {
            if (!CastleWarsEngineeringManager.clearRockslide(bot, rockIndex)) {
                return false;
            }
            state.delayTicks = 2;
            CastleWarsBotChat.sayUnderground(bot);
        }
        return true;
    }

    private static void processUndergroundReturnDescent(BotPlayer bot, RoleState state) {
        if (bot.getPosition().getPlane() > 0) {
            processCastleClimb(bot, state, opposite(state.team), false, Phase.UNDERGROUND_RETURN_DESCEND);
            return;
        }

        CastleWarsManager.Team enemy = opposite(state.team);
        Position ladder = enemy == CastleWarsManager.Team.SARADOMIN
                ? new Position(2430, 3082, 0)
                : new Position(2369, 3125, 0);
        if (!near(bot, ladder, 1)) {
            walk(bot, state, ladder);
            return;
        }
        if (enemy == CastleWarsManager.Team.SARADOMIN) {
            CastleWarsManager.handleFirstObjectAction(bot, 4912, 2430, 3082);
        } else {
            CastleWarsManager.handleFirstObjectAction(bot, 4912, 2369, 3125);
        }
        state.undergroundStage = 0;
        state.phase = Phase.UNDERGROUND_BACK;
        state.repathDelay = 0;
    }

    private static void processTakeFlag(BotPlayer bot, RoleState state) {
        CastleWarsManager.Team enemy = opposite(state.team);
        Position flag = enemy == CastleWarsManager.Team.SARADOMIN
                ? new Position(2429, 3074, 3)
                : new Position(2370, 3133, 3);
        Position approach = CastleWarsManager.getNearestAdjacentInteractionTile(bot, flag);
        if (approach == null || !reachInteractionApproach(bot, state, approach)) {
            return;
        }

        if (CastleWarsManager.isFlagAtBase(enemy)
                && CastleWarsManager.takeEnemyFlag(bot)) {
            CastleWarsBotChat.sayFlagCarrier(bot);
        }
        state.phase = Phase.ENEMY_DESCEND;
        state.repathDelay = 0;
    }

    private static void processCapture(BotPlayer bot, RoleState state) {
        Position ownFlag = state.team == CastleWarsManager.Team.SARADOMIN
                ? new Position(2429, 3074, 3)
                : new Position(2370, 3133, 3);
        Position approach = CastleWarsManager.getNearestAdjacentInteractionTile(bot, ownFlag);
        if (approach == null || !reachInteractionApproach(bot, state, approach)) {
            return;
        }

        if (CastleWarsManager.isCarryingEnemyFlag(bot)
                && CastleWarsManager.tryCaptureFlag(bot)) {
            CastleWarsBotChat.sayScore(bot);
        }
        state.resetForSpawn();
    }

    private static void processCastleClimb(BotPlayer bot, RoleState state,
                                           CastleWarsManager.Team castleTeam,
                                           boolean up, Phase completePhase) {
        int plane = bot.getPosition().getPlane();
        if (up && plane >= 3) {
            state.phase = completePhase;
            state.repathDelay = 0;
            return;
        }
        if (!up && plane <= 0) {
            state.phase = completePhase;
            state.repathDelay = 0;
            return;
        }

        if (castleTeam == CastleWarsManager.Team.SARADOMIN) {
            if (up) {
                if (plane == 0) {
                    useTraversal(bot, state, new Position(2419, 3077, 0), 4417, 2419, 3078);
                } else if (plane == 1) {
                    useTraversal(bot, state, new Position(2427, 3081, 1), 4417, 2428, 3081);
                } else if (plane == 2) {
                    useTraversal(bot, state, new Position(2425, 3077, 2), 4417, 2425, 3074);
                }
            } else {
                if (plane == 3) {
                    useTraversal(bot, state, new Position(2426, 3074, 3), 4415, 2425, 3074);
                } else if (plane == 2) {
                    useTraversal(bot, state, new Position(2430, 3080, 2), 4415, 2430, 3081);
                } else if (plane == 1) {
                    useTraversal(bot, state, new Position(2420, 3080, 1), 4415, 2419, 3080);
                }
            }
        } else {
            if (up) {
                if (plane == 0) {
                    useTraversal(bot, state, new Position(2380, 3130, 0), 4418, 2380, 3127);
                } else if (plane == 1) {
                    useTraversal(bot, state, new Position(2372, 3126, 1), 4418, 2369, 3126);
                } else if (plane == 2) {
                    useTraversal(bot, state, new Position(2374, 3130, 2), 4418, 2374, 3131);
                }
            } else {
                if (plane == 3) {
                    useTraversal(bot, state, new Position(2373, 3133, 3), 4415, 2374, 3133);
                } else if (plane == 2) {
                    useTraversal(bot, state, new Position(2369, 3127, 2), 4415, 2369, 3126);
                } else if (plane == 1) {
                    useTraversal(bot, state, new Position(2379, 3127, 1), 4415, 2380, 3127);
                }
            }
        }
    }

    private static void useTraversal(BotPlayer bot, RoleState state, Position approach,
                                     int objectId, int objectX, int objectY) {
        if (!reachInteractionApproach(bot, state, approach)) {
            return;
        }
        CastleWarsManager.handleFirstObjectAction(bot, objectId, objectX, objectY);
        state.repathDelay = 0;
    }

    private static int[] undergroundRockRoute(CastleWarsManager.Team team,
                                               int routeVariant,
                                               boolean returningHome) {
        int[] outward;
        if (team == CastleWarsManager.Team.SARADOMIN) {
            outward = routeVariant == 0 ? new int[]{3, 0} : new int[]{2, 1};
        } else {
            outward = routeVariant == 0 ? new int[]{0, 3} : new int[]{1, 2};
        }
        if (!returningHome) {
            return outward;
        }
        return new int[]{outward[1], outward[0]};
    }

    private static Position undergroundCenter(int routeVariant) {
        return routeVariant == 0
                ? new Position(2398, 9499, 0)
                : new Position(2400, 9508, 0);
    }

    private static Position undergroundExit(CastleWarsManager.Team team, boolean returningHome) {
        CastleWarsManager.Team destination = returningHome ? team : opposite(team);
        return destination == CastleWarsManager.Team.SARADOMIN
                ? new Position(2430, 9482, 0)
                : new Position(2369, 9525, 0);
    }

    private static boolean isTraversalPhase(Phase phase) {
        switch (phase) {
            case LEAVE_SPAWN:
            case DESCEND_HOME:
            case EXIT_HOME:
            case DEFENDER_CLIMB:
            case UNDERGROUND_DESCEND:
            case UNDERGROUND_OUT:
            case ENEMY_CLIMB:
            case ENEMY_DESCEND:
            case UNDERGROUND_RETURN_DESCEND:
            case UNDERGROUND_BACK:
            case HOME_CLIMB:
                return true;
            default:
                return false;
        }
    }

    private static boolean shouldUseBandage(BotPlayer bot) {
        if (bot.getInventoryManager().getItemAmount(4049) <= 0) {
            return false;
        }
        return bot.getCurrentHitpoints() * 100 <= bot.getMaxHitpoints() * 60
                || bot.getRunEnergyPercent() <= 30;
    }

    private static void makeInventorySpace(BotPlayer bot, int desiredFreeSlots) {
        int free = bot.getInventoryManager().getContainer().getFreeSlots();
        if (free >= desiredFreeSlots) {
            return;
        }

        if (bot.botFoodItemId > 0) {
            int food = bot.getInventoryManager().getItemAmount(bot.botFoodItemId);
            int remove = Math.min(food, desiredFreeSlots - free);
            if (remove > 0) {
                bot.getInventoryManager().removeItem(new ItemStack(bot.botFoodItemId, remove));
                free = bot.getInventoryManager().getContainer().getFreeSlots();
            }
        }

        if (free < desiredFreeSlots) {
            int bandages = bot.getInventoryManager().getItemAmount(4049);
            int remove = Math.min(bandages, desiredFreeSlots - free);
            if (remove > 0) {
                bot.getInventoryManager().removeItem(new ItemStack(4049, remove));
            }
        }
    }

    private static boolean hasActiveOpponent(BotPlayer bot) {
        Entity target = bot.getCombatTarget();
        if (target == null || target.isDead() || !target.isPlayer()) {
            return false;
        }
        Player targetPlayer = (Player)target;
        if (!CastleWarsManager.areOpponents(bot, targetPlayer)) {
            CombatManager.stopCombat(bot);
            return false;
        }
        if (targetPlayer.getPosition().getPlane() != bot.getPosition().getPlane()) {
            CombatManager.stopCombat(bot);
            return false;
        }
        if (CastleWarsManager.getSteppingStoneShortcutWaypoint(
                bot.getPosition(), targetPlayer.getPosition()) != null) {
            CombatManager.stopCombat(bot);
            return false;
        }
        if (GameUtil.getDistance(bot.getPosition(), targetPlayer.getPosition()) > 12) {
            CombatManager.stopCombat(bot);
            return false;
        }
        return true;
    }

    private static boolean tryEngageNearbyOpponent(BotPlayer bot, RoleState state, int radius) {
        Player best = null;
        int bestDistance = Integer.MAX_VALUE;
        for (Player player : World.getPlayers()) {
            if (player == null || player == bot || player.isDead()
                    || !CastleWarsManager.areOpponents(bot, player)) {
                continue;
            }
            if (player.getPosition().getPlane() != bot.getPosition().getPlane()) {
                continue;
            }
            int distance = GameUtil.getDistance(bot.getPosition(), player.getPosition());
            if (distance <= radius && distance < bestDistance) {
                best = player;
                bestDistance = distance;
            }
        }
        if (best == null) {
            return false;
        }
        if (CastleWarsManager.getSteppingStoneShortcutWaypoint(
                bot.getPosition(), best.getPosition()) != null) {
            CombatManager.stopCombat(bot);
            walk(bot, state, best.getPosition());
            return true;
        }
        bot.getMovementQueue().setRunning(true);
        CombatManager.startCombat(bot, best);
        return true;
    }

    private static boolean reachInteractionApproach(BotPlayer bot, RoleState state,
                                                     Position approach) {
        if (approach == null || bot.getPosition().getPlane() != approach.getPlane()) {
            return false;
        }
        if (near(bot, approach, 0)) {
            return true;
        }
        if (GameUtil.isWithinDistance(bot.getPosition(), approach, 1)) {
            bot.getMovementQueue().reset();
            bot.moveTo(approach.copy());
            bot.getMovementQueue().clearMovementActions();
            state.repathDelay = 0;
            return false;
        }
        walk(bot, state, approach);
        return false;
    }

    private static void walk(BotPlayer bot, RoleState state, Position target) {
        if (bot.getPosition().getPlane() != target.getPlane() || bot.isMovementLocked()) {
            return;
        }
        if (CastleWarsEngineeringManager.tryHandleNearbyDoorForBot(bot)) {
            state.repathDelay = 0;
            return;
        }

        Position navigationTarget = target;
        Position steppingWaypoint = CastleWarsManager.getSteppingStoneShortcutWaypoint(
                bot.getPosition(), target);
        if (steppingWaypoint != null) {
            if (CastleWarsManager.jumpSteppingStone(bot, steppingWaypoint)) {
                state.repathDelay = 0;
                return;
            }
            navigationTarget = steppingWaypoint;
        }

        if (state.repathDelay > 0) {
            --state.repathDelay;
            return;
        }
        state.repathDelay = 3 + GameUtil.randomInt(4);
        bot.getMovementQueue().setRunning(true);
        PathFinder.findPath(bot, navigationTarget.getX(), navigationTarget.getY(), true, 0, 0);
        bot.getMovementQueue().clearMovementActions();
    }

    private static boolean near(BotPlayer bot, Position target, int distance) {
        return bot.getPosition().getPlane() == target.getPlane()
                && GameUtil.isWithinDistance(bot.getPosition(), target, distance);
    }

    private static CastleWarsManager.Team opposite(CastleWarsManager.Team team) {
        return team == CastleWarsManager.Team.SARADOMIN
                ? CastleWarsManager.Team.ZAMORAK
                : CastleWarsManager.Team.SARADOMIN;
    }

    private enum Role {
        ATTACKER,
        MIDFIGHTER,
        UNDERGROUND,
        DEFENDER,
        CATAPULT
    }

    private enum Phase {
        SPAWN_SUPPLY,
        LEAVE_SPAWN,
        DESCEND_HOME,
        GROUND_SUPPLY,
        EXIT_HOME,
        DEFENDER_CLIMB,
        DEFEND_FLAG,
        CATAPULT_MOVE,
        CATAPULT_ROAM,
        MID_RUSH,
        MID_FIGHT,
        UNDERGROUND_DESCEND,
        UNDERGROUND_OUT,
        ENEMY_CLIMB,
        TAKE_FLAG,
        ENEMY_DESCEND,
        UNDERGROUND_RETURN_DESCEND,
        UNDERGROUND_BACK,
        HOME_CLIMB,
        CAPTURE_FLAG
    }

    private static final class RoleState {
        private final CastleWarsManager.Team team;
        private final int primaryCombatStyle;
        private final Role role;
        private Phase phase;
        private boolean bandagesStocked;
        private boolean utilityStocked;
        private int routeVariant;
        private int delayTicks;
        private int repathDelay;
        private int undergroundStage;
        private int pendingCollapseRock;
        private int barricadesPlaced;
        private int routeOffsetX;
        private int routeOffsetY;
        private int midPatrolTicks;

        private RoleState(CastleWarsManager.Team team, int primaryCombatStyle) {
            this.team = team;
            this.primaryCombatStyle = primaryCombatStyle;
            int roll = GameUtil.randomInt(100);
            if (roll < 40) {
                this.role = Role.ATTACKER;
            } else if (roll < 65) {
                this.role = Role.MIDFIGHTER;
            } else if (roll < 80) {
                this.role = Role.UNDERGROUND;
            } else if (roll < 95) {
                this.role = Role.DEFENDER;
            } else {
                this.role = Role.CATAPULT;
            }
            resetForSpawn();
        }

        private void resetForSpawn() {
            this.phase = Phase.SPAWN_SUPPLY;
            this.bandagesStocked = false;
            this.utilityStocked = false;
            this.routeVariant = GameUtil.randomInt(3);
            this.routeOffsetX = -3 + GameUtil.randomInt(7);
            this.routeOffsetY = -3 + GameUtil.randomInt(7);
            this.midPatrolTicks = 12 + GameUtil.randomInt(18);
            this.delayTicks = GameUtil.randomInt(8);
            this.repathDelay = 0;
            this.undergroundStage = 0;
            this.pendingCollapseRock = -1;
            this.barricadesPlaced = 0;
        }
    }
}
