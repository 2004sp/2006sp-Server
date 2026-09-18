package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class AlKharidFlyFishingBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3269, 3167, 0);
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3273, 3167, 0), new Position(3275, 3176, 0), new Position(3270, 3178, 0), new Position(3269, 3193, 0), new Position(3269, 3212, 0), new Position(3268, 3228, 0)}), new BotRoute(new Position[]{new Position(3267, 3228, 0), new Position(3259, 3234, 0), new Position(3259, 3241, 0), new Position(3250, 3252, 0), new Position(3241, 3252, 0)})};

    public AlKharidFlyFishingBotTask(int value2) {
        super(routeStartPosition, taskRouteSegments, 1, false, 2);
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[10] >= 20 && player.getSkillManager().getCurrentLevels()[7] >= 15;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getSkillManager().getCurrentLevels()[10] < 40 || player.getSkillManager().getCurrentLevels()[7] < 40;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> requiredItems = new ArrayList<ItemStack>();
        requiredItems.add(new ItemStack(309, 1));
        requiredItems.add(new ItemStack(314, 200));
        return requiredItems;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(309);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.getBankContainer().addToTab(new ItemStack(314, 5000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(309), new ItemStack(314, 30)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        player.getInventoryManager().addItem(itemStackArray[1]);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 1 + GameUtil.randomInt(40);
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
        BotCombatHelper.setBotSkillLevel(player, 6, value - value / 5 + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        BotCombatHelper.setBotSkillLevel(player, 5, value - (value / 5 << 1) + GameUtil.randomInt(value2));
        value2 = value / 5 << 1;
        if (value2 == 0) {
            value2 = 2;
        }
        if ((value = value - value / 5 + GameUtil.randomInt(value2)) < 20) {
            value = 20;
        }
        BotCombatHelper.setBotSkillLevel(player, 10, value);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        player.setAutoRetaliate(true);
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = 2883;
        player.continueBotRoute();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.setAutoRetaliate(false);
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = taskRouteSegments.length - 1;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = 2883;
        player.continueBotRoute();
    }

    @Override
    public final void continueWalkToTask(Player player, int value2) {
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.botTargetNpcId = 2883;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int value2) {
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        player.botTargetNpcId = 2883;
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void advanceTaskRouteSegment(Player player, boolean enabled2) {
        if (player.botTaskState.equals("walk towards task") || player.botTaskState.equals("walk to task") && enabled2) {
            if (!enabled2) {
                ++player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
            if (!enabled2) {
                player.botPathWaypointIndex = 0;
            }
            player.botTargetNpcId = 2883;
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1) {
                player.botTaskState = "walk to task";
                return;
            }
        } else if (player.botTaskState.equals("walk towards bank") || player.botTaskState.equals("walk to bank") && enabled2) {
            if (!enabled2) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
            if (!enabled2) {
                player.botPathWaypointIndex = 0;
            }
            player.botTargetNpcId = 2883;
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
            }
        }
    }
}

