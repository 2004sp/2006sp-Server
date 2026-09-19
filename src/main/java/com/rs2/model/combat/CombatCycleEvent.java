package com.rs2.model.combat;

import com.rs2.bot.combat.BotPvpCombatHandler;
import com.rs2.model.Entity;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.attack.CombatAttackState;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.smithing.SmithingHandler;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class CombatCycleEvent
extends CycleEvent {
    private Entity attacker;
    private Entity target;
    private int actionSequence;

    public CombatCycleEvent(Entity entity, Entity entity2) {
        this.attacker = entity;
        this.target = entity2;
        this.actionSequence = entity.nextActionSequence();
    }

    @Override
    public final void execute(CycleEventContainer container) {
        try {
            if (!this.attacker.isCurrentActionSequence(this.actionSequence) || this.attacker.getCombatTarget() == null) {
                if (this.attacker.isPlayer()) {
                    Player player = (Player)this.attacker;
                    if (player.getUsername().toLowerCase().equals("mod test")) {
                        System.out.println("check combat cycle3 " + this.attacker.getCombatTarget() + " " + this.target.isDead());
                    }
                }
                container.stop();
                return;
            }
            AttackValidationResult attackValidationResult = CombatCycleEvent.validateAttack(this.attacker, this.target);
            if (attackValidationResult != AttackValidationResult.VALID) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("combat cycle stop attacker-validation=" + attackValidationResult + " attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target));
                }
                container.stop();
                CombatManager.stopCombat(this.attacker);
                if (this.attacker.isPlayer()) {
                    Player player = (Player)this.attacker;
                    if (player.currentBotTask != null) {
                        player.interactWithBotNpcTargets(player.botInteractionTargetIds);
                    }
                    switch (attackValidationResult) {
                        case INVALID_TARGET_LOCATION: {
                            player.packetSender.sendGameMessage("You can't attack that npc from here.");
                            return;
                        }
                        case WILDERNESS_LEVEL_MISMATCH: {
                            player.packetSender.sendGameMessage("Your level difference is too great!");
                            player.packetSender.sendGameMessage("You need to move deeper into the Wilderness.");
                            CombatManager.stopCombat(player);
                            return;
                        }
                        case ALREADY_IN_COMBAT: {
                            player.packetSender.sendGameMessage("You are already under attack!");
                            return;
                        }
                        case NOT_DUEL_OPPONENT: {
                            player.packetSender.sendGameMessage("That player is not your opponent!");
                            return;
                        }
                        case TARGET_NOT_IN_WILDERNESS: {
                            player.packetSender.sendGameMessage("That player is not in the wilderness!");
                        }
                    }
                }
                return;
            }
            attackValidationResult = CombatCycleEvent.validateAttack(this.target, this.attacker);
            if (attackValidationResult != AttackValidationResult.VALID) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("combat cycle stop target-validation=" + attackValidationResult + " attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target));
                }
                container.stop();
                CombatManager.stopCombat(this.attacker);
                if (this.attacker.isPlayer()) {
                    Player player = (Player)this.attacker;
                    if (player.currentBotTask != null) {
                        player.interactWithBotNpcTargets(player.botInteractionTargetIds);
                    }
                    switch (attackValidationResult) {
                        case WILDERNESS_LEVEL_MISMATCH: {
                            player.packetSender.sendGameMessage("Your level difference is too great!");
                            player.packetSender.sendGameMessage("You need to move deeper into the Wilderness.");
                            CombatManager.stopCombat(player);
                            return;
                        }
                        case ALREADY_IN_COMBAT: {
                            player.packetSender.sendGameMessage("That " + (this.target.isPlayer() ? "player" : "monster") + " is already in combat!");
                            return;
                        }
                        case NOT_DUEL_OPPONENT: {
                            player.packetSender.sendGameMessage("That player is not your opponent!");
                        }
                    }
                }
                return;
            }
            if (this.attacker.isPlayer() && this.target.isPlayer()) {
                Player player = (Player)this.attacker;
                Player targetPlayer = (Player)this.target;
                if (player.botEnabled || targetPlayer.botEnabled) {
                    if (player.botCombatState != null) {
                        return;
                    }
                    BotPvpCombatHandler.startBotPvpCombatTicks(player, targetPlayer);
                }
            }
            if (this.attacker.isPlayer() && this.target.isNpc()) {
                Player player = (Player)this.attacker;
                Npc targetNpc = (Npc)this.target;
                if (player.botEnabled) {
                    if (player.currentBotTask == null) {
                        return;
                    }
                    if (!player.botTaskState.equals("do task")) {
                        return;
                    }
                    player.currentBotTask.startNpcCombatTick(player, targetNpc);
                }
            }
            int distance = GameUtil.getDistance(this.attacker.getPosition(), this.target.getPosition());
            List<SmithingHandler> attackOptions = new LinkedList<SmithingHandler>();
            int usableAttackCount = this.attacker.collectCombatAttackOptions(attackOptions, this.target, distance);
            CombatAttack movementAttack = null;
            boolean attackDelayElapsed = this.attacker.getAttackDelayTimer().hasElapsed();
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("combat cycle tick attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " distance=" + distance + " options=" + attackOptions.size() + " usable=" + usableAttackCount + " delayElapsed=" + attackDelayElapsed);
            }
            if (attackDelayElapsed) {
                Collections.shuffle(attackOptions);
            }
            Iterator<SmithingHandler> iterator = attackOptions.iterator();
            while (iterator.hasNext()) {
                SmithingHandler attackOption = iterator.next();
                CombatAttackState combatAttackState = attackOption.getState();
                if (this.attacker.isStunned()) {
                    combatAttackState = CombatAttackState.b;
                }
                this.attacker.getUpdateState().setFaceEntity(this.target.getEncodedIndex());
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("combat cycle option attacker=" + GameplayTrace.describe(this.attacker) + " state=" + combatAttackState + " type=" + attackOption.getAttack().getCombatType() + " range=" + attackOption.getAttack().getAttackRange());
                }
                if (combatAttackState == CombatAttackState.b) {
                    movementAttack = attackOption.getAttack();
                    continue;
                }
                if (combatAttackState != CombatAttackState.c) {
                    continue;
                }
                if (!this.attacker.isPlayer()) {
                    this.attacker.setAttackRange(attackOption.getAttack().getAttackRange());
                }
                if (attackDelayElapsed) {
                    int delayTicks = attackOption.getAttack().execute(container);
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("combat cycle execute attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " type=" + attackOption.getAttack().getCombatType() + " delayTicks=" + delayTicks);
                    }
                    if (delayTicks == -1) {
                        CombatManager.stopCombat(this.attacker);
                        if (this.attacker.isPlayer()) {
                            Player player = (Player)this.attacker;
                            if (player.getUsername().toLowerCase().equals("mod test")) {
                                System.out.println("check combat cycle2");
                            }
                        }
                        container.stop();
                        return;
                    }
                    this.target.getRecentCombatTimer().setTargetDelay(this.attacker, 17);
                    this.target.getSingleCombatTimer().setTargetDelay(this.attacker, 8);
                    this.attacker.setAttackDelayTicks(delayTicks);
                }
                return;
            }
            if (movementAttack == null) {
                if (usableAttackCount <= attackOptions.size()) {
                    if (GameplayTrace.enabled()) {
                        GameplayTrace.log("combat cycle stop no-movement-attack attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " usable=" + usableAttackCount + " options=" + attackOptions.size());
                    }
                    CombatManager.stopCombat(this.attacker);
                    if (this.attacker.isPlayer()) {
                        Player player = (Player)this.attacker;
                        if (player.getUsername().toLowerCase().equals("mod test")) {
                            System.out.println("check combat cycle1");
                        }
                    }
                    container.stop();
                }
                this.attacker.getMovementQueue().clear();
                return;
            }
            if (!this.attacker.isPlayer()) {
                this.attacker.setAttackRange(movementAttack.getAttackRange());
            }
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("combat cycle needs-movement attacker=" + GameplayTrace.describe(this.attacker) + " target=" + GameplayTrace.describe(this.target) + " movementType=" + movementAttack.getCombatType() + " range=" + movementAttack.getAttackRange());
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    @Override
    public final void onStop() {
    }

    public static AttackValidationResult validateAttack(Entity entity, Entity entity2) {
        if (entity2 == null || entity2.isDead()) {
            return AttackValidationResult.INVALID_TARGET;
        }
        boolean castleWarsCrossLevelViewport = entity.isPlayer() && entity2.isPlayer()
                && CastleWarsManager.isCastleWallCrossLevelPair(
                        (Player)entity, (Player)entity2);
        if (entity2.isPlayer() && !((Player)entity2).isRegistered()
                || !entity2.getPosition().isWithinViewport(entity.getPosition())
                && !castleWarsCrossLevelViewport) {
            return AttackValidationResult.INVALID_TARGET;
        }
        if (entity2.getMaxHitpoints() <= 0) {
            return AttackValidationResult.INVALID_TARGET;
        }
        if (entity2.isPlayer() && ((Player)entity2).logoutPacketSent) {
            return AttackValidationResult.INVALID_TARGET_LOCATION;
        }
        if (entity2.isNpc() && (((Npc)entity2).getNpcId() == 2440 ? entity.getPosition().getY() < 10141 : (((Npc)entity2).getNpcId() == 2443 ? entity.getPosition().getX() < 2543 : ((Npc)entity2).getNpcId() == 2446 && entity.getPosition().getY() > 10145))) {
            return AttackValidationResult.INVALID_TARGET_LOCATION;
        }
        boolean castleWarsCombat = entity.isPlayer() && entity2.isPlayer()
                && CastleWarsManager.isInGame((Player)entity)
                && CastleWarsManager.isInGame((Player)entity2);
        if (!castleWarsCombat
                && !(entity.isInMultiCombatArea() && entity2.isInMultiCombatArea()
                || entity.getSingleCombatTimer().getTarget() == null
                || entity.getSingleCombatTimer().hasElapsed()
                || entity.getSingleCombatTimer().getTarget() == entity2)) {
            return AttackValidationResult.ALREADY_IN_COMBAT;
        }
        int value = entity.isPlayer() && entity2.isPlayer() ? 1 : 0;
        if (value != 0) {
            if (castleWarsCombat) {
                if (!CastleWarsManager.areOpponents((Player)entity, (Player)entity2)) {
                    return AttackValidationResult.NOT_DUEL_OPPONENT;
                }
                return AttackValidationResult.VALID;
            }
            if (entity.isInDuelArena() && ((Player)entity).getDuelSession().getOpponent() != entity2) {
                return AttackValidationResult.NOT_DUEL_OPPONENT;
            }
            if (entity.isInWilderness()) {
                int value2;
                int value3;
                value = entity.getWildernessLevel();
                if (value < Math.abs((value3 = ((Player)entity).getCombatLevel()) - (value2 = ((Player)entity2).getCombatLevel()))) {
                    return AttackValidationResult.WILDERNESS_LEVEL_MISMATCH;
                }
            } else if (entity.isPlayer() && !entity.isInDuelArena()) {
                return AttackValidationResult.TARGET_NOT_IN_WILDERNESS;
            }
        }
        return AttackValidationResult.VALID;
    }
}
