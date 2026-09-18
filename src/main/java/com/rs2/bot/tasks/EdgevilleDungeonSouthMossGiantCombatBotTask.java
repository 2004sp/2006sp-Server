package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.tasks.SouthMossGiantDungeonEntryTickTask;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class EdgevilleDungeonSouthMossGiantCombatBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3253, 3420, 0);
    private static int[] ignoredLootItemIds = new int[]{1389, 1969};
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3253, 3428, 0), new Position(3247, 3432, 0), new Position(3245, 3444, 0), new Position(3243, 3455, 0), new Position(3238, 3458, 0)}), new BotRoute(new Position[]{new Position(3243, 9870, 0), new Position(3244, 9890, 0), new Position(3246, 9892, 0)}), new BotRoute(new Position[]{new Position(3247, 9892, 0), new Position(3268, 9892, 0), new Position(3275, 9893, 0), new Position(3281, 9898, 0), new Position(3281, 9907, 0), new Position(3272, 9915, 0), new Position(3257, 9915, 0), new Position(3246, 9916, 0)}), new BotRoute(new Position[]{new Position(3245, 9916, 0), new Position(3241, 9911, 0)}), new BotRoute(new Position[]{new Position(3241, 9910, 0), new Position(3241, 9907, 0), new Position(3224, 9908, 0), new Position(3217, 9908, 0), new Position(3210, 9899, 0)}), new BotRoute(new Position[]{new Position(3210, 9897, 0), new Position(3208, 9890, 0), new Position(3188, 9890, 0), new Position(3180, 9895, 0), new Position(3172, 9892, 0), new Position(3169, 9884, 0)})};

    public EdgevilleDungeonSouthMossGiantCombatBotTask(int value3) {
        super(routeStartPosition, taskRouteSegments, 1, false, 2);
        boolean enabled = true;
        EdgevilleDungeonSouthMossGiantCombatBotTask edgevilleDungeonSouthMossGiantCombatBotTask = this;
        this.combatTask = true;
        super.setForcedCombatStyle(0);
        int[] integerValues = ignoredLootItemIds;
        edgevilleDungeonSouthMossGiantCombatBotTask = this;
        ((BotTaskDefinition)this).ignoredLootItemIds = integerValues;
        int value2 = 10;
        edgevilleDungeonSouthMossGiantCombatBotTask = this;
        this.targetSearchRadius = value2;
        super.addLootSellShopIds(new int[]{151});
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(112);
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getCombatLevel() >= 36;
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
            if (foodDefinition.getHealAmount() >= 7) {
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
        return arrayList;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botFoodItemId = 379;
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(player.botFoodItemId, 20)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        GameplayHelper.prepareBotCombatStyle(player, 0);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 30 + GameUtil.randomInt(20);
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
    public final void advanceTaskRouteSegment(Player player, boolean enabled2) {
        if (((Player)player).botTaskState.equals("walk towards task") || ((Player)player).botTaskState.equals("walk to task") && enabled2) {
            if (!enabled2) {
                ++((Player)player).botPathSegmentIndex;
            }
            ((Player)player).currentBotRoute = taskRouteSegments[((Player)player).botPathSegmentIndex];
            if (!enabled2) {
                ((Player)player).botPathWaypointIndex = 0;
            }
            int regionId = GameUtil.getRegionId(((Entity)player).getPosition().getX(), ((Entity)player).getPosition().getY());
            if (((Player)player).botPathSegmentIndex == 1 && ((Player)player).botPathWaypointIndex == 0 && regionId == 12854) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(881);
                ((Player)player).botRouteActionPending = true;
                if (((Player)player).interactWithBotObjectTargetsNoRetry(arrayList, false)) {
                    World.getTaskScheduler().schedule(new SouthMossGiantDungeonEntryTickTask(this, 3, (Player)player));
                    return;
                }
                arrayList.clear();
                arrayList.add(882);
                ((Player)player).interactWithBotObjectTargetsNoRetry(arrayList, false);
                return;
            }
            if (((Player)player).botPathSegmentIndex == taskRouteSegments.length - 1) {
                ((Player)player).botTaskState = "walk to task";
                ((Player)player).botTargetNpcId = 733;
                return;
            }
            ((Player)player).botTargetNpcId = 1530;
            return;
        }
        if (((Player)player).botTaskState.equals("walk towards bank") || ((Player)player).botTaskState.equals("walk to bank") && enabled2) {
            if (!enabled2) {
                --((Player)player).botPathSegmentIndex;
            }
            int regionId2 = GameUtil.getRegionId(((Entity)player).getPosition().getX(), ((Entity)player).getPosition().getY());
            ((Player)player).currentBotRoute = taskRouteSegments[((Player)player).botPathSegmentIndex].reversed();
            if (!enabled2) {
                ((Player)player).botPathWaypointIndex = 0;
            }
            if (((Player)player).botPathSegmentIndex == taskRouteSegments.length - 2) {
                ((Player)player).botTargetNpcId = 733;
                return;
            }
            if (((Player)player).botPathSegmentIndex == 0 && regionId2 == 12954) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1755);
                ((Player)player).interactWithBotObjectTargets(arrayList);
                ((Player)player).botRouteActionPending = true;
                ((Player)player).botTaskState = "walk to bank";
                return;
            }
            ((Player)player).botTargetNpcId = 1530;
        }
    }
}

