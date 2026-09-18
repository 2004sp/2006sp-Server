package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class WaterRuneRunecraftingBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3092, 3245, 0);
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3093, 3247, 0), new Position(3098, 3247, 0), new Position(3098, 3239, 0), new Position(3104, 3235, 0), new Position(3114, 3228, 0), new Position(3127, 3225, 0), new Position(3138, 3217, 0), new Position(3146, 3207, 0), new Position(3152, 3200, 0), new Position(3157, 3184, 0), new Position(3165, 3169, 0), new Position(3178, 3165, 0)}), new BotRoute(new Position[]{new Position(2722, 4833, 0)})};

    public WaterRuneRunecraftingBotTask(int value2) {
        super(routeStartPosition, taskRouteSegments, 0, false, 1);
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[20] >= 5;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getSkillManager().getCurrentLevels()[20] < 9 || !player.ownsItem(5535) && !player.ownsItem(1440);
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        if (player.ownsItem(5531) && ItemDefinition.isDefined(5531)) {
            arrayList.add(new ItemStack(5531, 1));
        } else {
            arrayList.add(new ItemStack(1444, 1));
        }
        if (!player.ownsItemAmount(1436, 500) && player.ownsItemAmount(7936, 500) && ItemDefinition.isDefined(7936)) {
            arrayList.add(new ItemStack(7936, 500));
        } else {
            arrayList.add(new ItemStack(1436, 500));
        }
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(2480);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.getBankContainer().addToTab(new ItemStack(1436, 5000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(5531), new ItemStack(1436, 28)};
        if (GameUtil.randomInt(2) == 0 || !ItemDefinition.isDefined(5531)) {
            itemStackArray = new ItemStack[]{new ItemStack(1444), new ItemStack(1436, 27)};
        }
        player.botTaskRequiredItems = itemStackArray;
        if (itemStackArray[0].getId() == 5531) {
            player.getEquipmentManager().getContainer().setItem(0, itemStackArray[0]);
        } else {
            player.getInventoryManager().addItem(itemStackArray[0]);
        }
        player.getInventoryManager().addItem(itemStackArray[1]);
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
        if ((value = value - value / 5 + GameUtil.randomInt(value2)) < 5) {
            value = 5;
        }
        BotCombatHelper.setBotSkillLevel(player, 20, value);
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
        player.continueBotRoute();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.setAutoRetaliate(false);
        player.botTaskState = "walk to bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = taskRouteSegments.length - 2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(2467);
        player.interactWithBotObjectTargets(arrayList);
        player.botRouteActionPending = true;
        player.continueBotRoute();
    }

    @Override
    public final void continueWalkToTask(Player player, int value2) {
        player.setAutoRetaliate(true);
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int value2) {
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        if (player.botPathSegmentIndex == taskRouteSegments.length - 2 && player.botPathWaypointIndex == 0) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(2467);
            player.interactWithBotObjectTargets(arrayList);
            player.botRouteActionPending = true;
        }
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
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1) {
                player.botTaskState = "walk to task";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(2454);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
            }
        }
    }
}

