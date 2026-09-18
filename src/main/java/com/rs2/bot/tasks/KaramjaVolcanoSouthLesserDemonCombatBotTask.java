package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class KaramjaVolcanoSouthLesserDemonCombatBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3012, 3355, 0);
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3012, 3359, 0), new Position(3008, 3359, 0), new Position(3008, 3342, 0), new Position(3008, 3323, 0), new Position(3007, 3305, 0), new Position(3007, 3289, 0), new Position(3011, 3272, 0), new Position(3016, 3262, 0), new Position(3020, 3249, 0), new Position(3028, 3241, 0), new Position(3028, 3224, 0)}), new BotRoute(new Position[]{new Position(2946, 3147, 0), new Position(2932, 3149, 0), new Position(2916, 3152, 0), new Position(2898, 3156, 0), new Position(2880, 3156, 0), new Position(2862, 3166, 0)}), new BotRoute(new Position[]{new Position(2851, 9576, 0), new Position(2840, 9581, 0), new Position(2836, 9566, 0)})};
    private static int[] ignoredLootItemIds = new int[]{592, 1141, 1157, 1295, 1325, 1353, 1993};

    public KaramjaVolcanoSouthLesserDemonCombatBotTask(int value2) {
        super(routeStartPosition, taskRouteSegments, 1, false, 2);
        boolean enabled = true;
        KaramjaVolcanoSouthLesserDemonCombatBotTask karamjaVolcanoSouthLesserDemonCombatBotTask = this;
        this.combatTask = true;
        int[] integerValues = ignoredLootItemIds;
        karamjaVolcanoSouthLesserDemonCombatBotTask = this;
        ((BotTaskDefinition)this).ignoredLootItemIds = integerValues;
        super.addLootSellShopIds(new int[]{56, 94});
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(82);
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getCombatLevel() >= 70;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        int initialValue = -1;
        FoodDefinition[] foodDefinitionArray = FoodDefinition.values();
        int length = foodDefinitionArray.length;
        int index = 0;
        while (index < length) {
            FoodDefinition foodDefinition = foodDefinitionArray[index];
            if (foodDefinition.getHealAmount() >= 10) {
                int[] itemIds = foodDefinition.getItemIds();
                int length2 = itemIds.length;
                int index2 = 0;
                while (index2 < length2) {
                    int value = itemIds[index2];
                    if (player.ownsItemAmount(value, 16)) {
                        initialValue = value;
                        break;
                    }
                    ++index2;
                }
                if (initialValue != -1) break;
            }
            ++index;
        }
        arrayList.add(new ItemStack(initialValue, 16));
        player.botFoodItemId = initialValue;
        arrayList.add(new ItemStack(995, 1000));
        return arrayList;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botFoodItemId = 379;
        player.getBankContainer().addToTab(new ItemStack(995, 10000), 0);
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        Object value = new ItemStack[]{new ItemStack(995, 60), new ItemStack(player.botFoodItemId, 20)};
        player.botTaskRequiredItems = (ItemStack[])value;
        player.getInventoryManager().addItem(((ItemStack[])value)[0]);
        player.getInventoryManager().addItem(((ItemStack[])value)[1]);
        value = player;
        GameplayHelper.prepareBotCombatStyle((Player)value, -1);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 40 + GameUtil.randomInt(30);
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
            if (player.botPathSegmentIndex == 1 && regionId == 12082) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(377);
                player.botInteractionOption = 1;
                player.interactWithBotNpcTargets(arrayList);
                player.botRouteActionPending = true;
                player.botRouteTravelPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 2 && regionId == 11313) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(492);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
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
            int regionId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == 0 && regionId == 11825) {
                player.botTaskState = "walk to bank";
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(380);
                player.botInteractionOption = 1;
                player.interactWithBotNpcTargets(arrayList);
                player.botRouteActionPending = true;
                player.botRouteTravelPending = true;
                return;
            }
            if (player.botPathSegmentIndex == 1 && regionId == 11413) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1764);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
            }
        }
    }
}

