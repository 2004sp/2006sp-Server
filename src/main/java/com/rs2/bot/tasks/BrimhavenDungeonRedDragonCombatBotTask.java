package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class BrimhavenDungeonRedDragonCombatBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(2445, 5178, 0);
    private static int[] ignoredLootItemIds = new int[]{1897};
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(2445, 5172, 0), new Position(2460, 5169, 0), new Position(2474, 5168, 0)}), new BotRoute(new Position[]{new Position(2858, 9571, 0)}), new BotRoute(new Position[]{new Position(2850, 3164, 0), new Position(2834, 3159, 0), new Position(2827, 3170, 0), new Position(2816, 3182, 0)}), new BotRoute(new Position[]{new Position(2815, 3182, 0), new Position(2801, 3179, 0), new Position(2789, 3179, 0), new Position(2780, 3186, 0), new Position(2766, 3185, 0), new Position(2757, 3173, 0), new Position(2750, 3158, 0)}), new BotRoute(new Position[]{new Position(2706, 9564, 0), new Position(2691, 9564, 0)}), new BotRoute(new Position[]{new Position(2689, 9564, 0), new Position(2675, 9566, 0), new Position(2670, 9572, 0), new Position(2662, 9568, 0), new Position(2649, 9562, 0)}), new BotRoute(new Position[]{new Position(2647, 9557, 0), new Position(2646, 9539, 0), new Position(2641, 9522, 0), new Position(2648, 9510, 0), new Position(2653, 9503, 0), new Position(2665, 9503, 0), new Position(2672, 9499, 0)}), new BotRoute(new Position[]{new Position(2674, 9499, 0), new Position(2682, 9506, 0)}), new BotRoute(new Position[]{new Position(2687, 9506, 0), new Position(2705, 9517, 0)})};

    public BrimhavenDungeonRedDragonCombatBotTask(int value3) {
        super(routeStartPosition, taskRouteSegments, 1, true, 2);
        boolean enabled = true;
        BrimhavenDungeonRedDragonCombatBotTask brimhavenDungeonRedDragonCombatBotTask = this;
        this.combatTask = true;
        enabled = true;
        brimhavenDungeonRedDragonCombatBotTask = this;
        this.usesDepositBox = true;
        int[] integerValues = ignoredLootItemIds;
        brimhavenDungeonRedDragonCombatBotTask = this;
        ((BotTaskDefinition)this).ignoredLootItemIds = integerValues;
        super.addLootSellShopIds(new int[]{2});
        int value2 = 336;
        brimhavenDungeonRedDragonCombatBotTask = this;
        this.minimumServerRevision = 336;
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[16] >= 40;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(53);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botFoodItemId = 385;
        player.getBankContainer().addToTab(new ItemStack(995, 100000), 0);
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(995, 875), new ItemStack(1359), new ItemStack(player.botFoodItemId, 20)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        player.getInventoryManager().addItem(itemStackArray[1]);
        player.getInventoryManager().addItem(itemStackArray[2]);
        BotCombatLoadoutManager.prepareMeleeLoadout(player, true);
        BotCombatLoadoutManager.equipGlovesAndBoots(player);
        BotCombatLoadoutManager.equipRandomCape(player);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 60 + GameUtil.randomInt(40);
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
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        BotCombatHelper.setBotSkillLevel(player, 8, 60);
        BotCombatHelper.setBotSkillLevel(player, 16, 40);
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
        player.botTaskState = "walk towards bank";
        player.botPathWaypointIndex = 0;
        player.botPathSegmentIndex = taskRouteSegments.length - 1;
        player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
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
            if (player.botPathSegmentIndex == 1) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(9359);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 2) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1764);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 3) {
                player.botTargetNpcId = 1596;
                return;
            }
            if (player.botPathSegmentIndex == 4) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(5083);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 5) {
                player.botTargetNpcId = 5103;
                return;
            }
            if (player.botPathSegmentIndex == 6) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(5110);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 7) {
                player.botTargetNpcId = 5105;
                return;
            }
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1) {
                player.botTaskState = "walk to task";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(5088);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
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
            if (player.botPathSegmentIndex == 0) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(9358);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                player.botTaskState = "walk to bank";
                return;
            }
            if (player.botPathSegmentIndex == 1) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(492);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 2) {
                player.botTargetNpcId = 1596;
                return;
            }
            if (player.botPathSegmentIndex == 3) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(5084);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 4) {
                player.botTargetNpcId = 5103;
                return;
            }
            if (player.botPathSegmentIndex == 5) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(5111);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 6) {
                player.botTargetNpcId = 5105;
                return;
            }
            if (player.botPathSegmentIndex == 7) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(5090);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
            }
        }
    }
}

