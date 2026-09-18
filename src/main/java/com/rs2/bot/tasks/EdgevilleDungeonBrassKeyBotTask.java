package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.tasks.BrassKeyDungeonEntryTickTask;
import com.rs2.bot.tasks.BrassKeyPickupTickTask;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class EdgevilleDungeonBrassKeyBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3094, 3489, 0);
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3090, 3490, 0), new Position(3090, 3487, 0), new Position(3094, 3482, 0), new Position(3094, 3473, 0), new Position(3096, 3468, 0)}), new BotRoute(new Position[]{new Position(3096, 9879, 0), new Position(3095, 9887, 0), new Position(3095, 9901, 0), new Position(3103, 9909, 0)}), new BotRoute(new Position[]{new Position(3104, 9909, 0), new Position(3121, 9909, 0), new Position(3129, 9911, 0), new Position(3139, 9914, 0), new Position(3142, 9903, 0), new Position(3150, 9894, 0), new Position(3150, 9872, 0), new Position(3146, 9870, 0)}), new BotRoute(new Position[]{new Position(3145, 9870, 0), new Position(3138, 9870, 0), new Position(3135, 9873, 0), new Position(3120, 9872, 0), new Position(3118, 9864, 0), new Position(3128, 9863, 0)})};
    final Position brassKeySpawnPosition = new Position(3128, 9863, 0);

    public EdgevilleDungeonBrassKeyBotTask(int value2) {
        super(routeStartPosition, taskRouteSegments, 0, false, 1);
        this.usesCustomTaskAction = true;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getCombatLevel() >= 23;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getCombatLevel() <= 60;
    }

    @Override
    public final void startCustomTaskAction(Player player) {
        ((Player)player).refreshRegionState();
        World.getTaskScheduler().schedule(new BrassKeyPickupTickTask(this, 2, (Player)player));
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 1 + GameUtil.randomInt(99);
        int value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 0, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 2, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 1, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 4, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, value - (value / 5 << 1) + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        if ((value = value - value / 5 + GameUtil.randomInt(value2)) < 33) {
            value = 33;
        }
        BotCombatHelper.setBotSkillLevel(player, 6, value);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = 1558;
        player.continueBotRoute();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = taskRouteSegments.length - 1;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = 1558;
        player.continueBotRoute();
    }

    @Override
    public final void continueWalkToTask(Player player, int value2) {
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = 1558;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int value2) {
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = 1558;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void advanceTaskRouteSegment(Player player, boolean continuing) {
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && continuing) {
            if (!continuing) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
            if (!continuing) {
                player.botPathWaypointIndex = 0;
            }
            int regionId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == 1 && player.botTaskState.equals("walk towards task") && regionId == 12342) {
                Object value = new ArrayList<Integer>();
                ((ArrayList)value).add(1568);
                player.botRouteActionPending = true;
                if (player.interactWithBotObjectTargetsNoRetry((ArrayList)value, false)) {
                    World.getTaskScheduler().schedule(new BrassKeyDungeonEntryTickTask(this, 3, player));
                } else {
                    ((ArrayList)value).clear();
                    ((ArrayList)value).add(1570);
                    player.interactWithBotObjectTargetsNoRetry((ArrayList)value, false);
                }
            }
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1) {
                player.botTaskState = "walk to task";
                return;
            }
        } else if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && continuing) {
            if (!continuing) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
            if (!continuing) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == 0 && player.botTaskState.equals("walk towards bank")) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1755);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                player.botTaskState = "walk to bank";
            }
        }
    }
}

