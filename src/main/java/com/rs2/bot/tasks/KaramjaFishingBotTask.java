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

public final class KaramjaFishingBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3012, 3355, 0);
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3012, 3359, 0), new Position(3008, 3359, 0), new Position(3008, 3342, 0), new Position(3008, 3323, 0), new Position(3007, 3305, 0), new Position(3007, 3289, 0), new Position(3011, 3272, 0), new Position(3016, 3262, 0), new Position(3020, 3249, 0), new Position(3028, 3241, 0), new Position(3028, 3224, 0)}), new BotRoute(new Position[]{new Position(2946, 3147, 0), new Position(2932, 3149, 0), new Position(2916, 3153, 0), new Position(2922, 3163, 0), new Position(2923, 3171, 0)})};

    public KaramjaFishingBotTask(int value3) {
        super(routeStartPosition, taskRouteSegments, 1, false, 4);
        int value2 = 10;
        KaramjaFishingBotTask karamjaFishingBotTask = this;
        this.targetSearchRadius = value2;
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[10] >= 40 && player.getSkillManager().getCurrentLevels()[7] >= 40;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        if (player.getSkillManager().getCurrentLevels()[10] >= 50 && player.getSkillManager().getCurrentLevels()[7] >= 45) {
            arrayList.add(new ItemStack(311, 1));
        } else {
            arrayList.add(new ItemStack(301, 1));
        }
        arrayList.add(new ItemStack(995, 1000));
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(312);
    }

    @Override
    public final int getInteractionOption(Player player) {
        if (player.botTaskRequiredItems[0].getId() == 311) {
            return 2;
        }
        return 1;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.getBankContainer().addToTab(new ItemStack(995, 10000), 0);
        ItemStack[] itemStackArray = player.getSkillManager().getCurrentLevels()[10] >= 50 ? new ItemStack[]{new ItemStack(311), new ItemStack(995, 60)} : new ItemStack[]{new ItemStack(301), new ItemStack(995, 60)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
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
        if ((value = value - value / 5 + GameUtil.randomInt(value2)) < 40) {
            value = 40;
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
        player.botTaskState = "walk towards task";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = 0;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        player.continueBotRoute();
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = taskRouteSegments.length - 1;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
        player.continueBotRoute();
    }

    @Override
    public final void continueWalkToTask(Player player, int value2) {
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int value2) {
        player.botPathWaypointIndex = value2;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
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
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1 && player.botTaskState.equals("walk towards task")) {
                player.botTaskState = "walk to task";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(377);
                player.botInteractionOption = 1;
                player.interactWithBotNpcTargets(arrayList);
                player.botRouteActionPending = true;
                player.botRouteTravelPending = true;
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
            if (player.botPathSegmentIndex == 0 && player.botTaskState.equals("walk towards bank")) {
                player.botTaskState = "walk to bank";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(380);
                player.botInteractionOption = 1;
                player.interactWithBotNpcTargets(arrayList);
                player.botRouteActionPending = true;
                player.botRouteTravelPending = true;
            }
        }
    }
}

