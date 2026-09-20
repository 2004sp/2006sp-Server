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

public final class EdgevilleDungeonHillGiantCombatBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3185, 3436, 0);
    private static int[] ignoredLootItemIds = new int[]{1013, 1139, 1203, 1422, 1789, 1917};
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3182, 3432, 0), new Position(3163, 3426, 0), new Position(3149, 3426, 0), new Position(3135, 3432, 0), new Position(3127, 3441, 0), new Position(3115, 3449, 0)}), new BotRoute(new Position[]{new Position(3115, 3450, 0)}), new BotRoute(new Position[]{new Position(3116, 9843, 0), new Position(3114, 9836, 0)})};

    public EdgevilleDungeonHillGiantCombatBotTask(int value2) {
        super(routeStartPosition, taskRouteSegments, 1, false, 10);
        boolean enabled = true;
        EdgevilleDungeonHillGiantCombatBotTask edgevilleDungeonHillGiantCombatBotTask = this;
        this.combatTask = true;
        int[] integerValues = ignoredLootItemIds;
        edgevilleDungeonHillGiantCombatBotTask = this;
        ((BotTaskDefinition)this).ignoredLootItemIds = integerValues;
        super.addLootSellShopIds(new int[]{151});
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(117);
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
        arrayList.add(new ItemStack(983, 1));
        return arrayList;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botFoodItemId = 379;
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        Object value = new ItemStack[]{new ItemStack(983), new ItemStack(player.botFoodItemId, 20)};
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
        int value = 20 + GameUtil.randomInt(20);
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

    private static boolean hasValidRouteSegmentIndex(Player player) {
        return player.botPathSegmentIndex >= 0 && player.botPathSegmentIndex < taskRouteSegments.length;
    }

    private static void recoverRouteState(Player player, boolean walkingToBank) {
        int previousSegmentIndex = player.botPathSegmentIndex;
        int bestSegmentIndex = 0;
        int bestWaypointIndex = 0;
        int bestDistance = Integer.MAX_VALUE;

        for (int segmentIndex = 0; segmentIndex < taskRouteSegments.length; ++segmentIndex) {
            BotRoute route = walkingToBank ? taskRouteSegments[segmentIndex].reversed() : taskRouteSegments[segmentIndex];
            for (int waypointIndex = 0; waypointIndex < route.waypoints.length; ++waypointIndex) {
                int distance = GameUtil.getDistance(player.getPosition(), route.waypoints[waypointIndex]);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestSegmentIndex = segmentIndex;
                    bestWaypointIndex = waypointIndex;
                }
            }
        }

        player.botPathSegmentIndex = bestSegmentIndex;
        player.botPathWaypointIndex = bestWaypointIndex;
        player.currentBotRoute = walkingToBank ? taskRouteSegments[bestSegmentIndex].reversed() : taskRouteSegments[bestSegmentIndex];
        player.botTargetNpcId = 1804;

        if (walkingToBank) {
            player.botTaskState = bestSegmentIndex == 0 ? "walk to bank" : "walk towards bank";
        } else {
            player.botTaskState = bestSegmentIndex == taskRouteSegments.length - 1 ? "walk to task" : "walk towards task";
        }

        System.out.println(
            "Recovered hill giant bot route for " + player.getUsername()
                + " from segment " + previousSegmentIndex
                + " to segment " + bestSegmentIndex
                + ", waypoint " + bestWaypointIndex
                + " at " + player.getPosition()
        );
    }

    private static void validateRouteState(Player player, boolean walkingToBank) {
        if (!hasValidRouteSegmentIndex(player)) {
            recoverRouteState(player, walkingToBank);
            return;
        }

        BotRoute route = walkingToBank ? taskRouteSegments[player.botPathSegmentIndex].reversed() : taskRouteSegments[player.botPathSegmentIndex];
        if (player.botPathWaypointIndex < 0 || player.botPathWaypointIndex >= route.waypoints.length) {
            recoverRouteState(player, walkingToBank);
            return;
        }

        player.currentBotRoute = route;
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
        validateRouteState(player, false);
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void continueWalkToBank(Player player, int value2) {
        player.setAutoRetaliate(false);
        player.botPathWaypointIndex = value2;
        validateRouteState(player, true);
        this.advanceTaskRouteSegment(player, true);
    }

    @Override
    public final void advanceTaskRouteSegment(Player player, boolean continuing) {
        if (player.botTaskState.equals("walk towards task") || (player.botTaskState.equals("walk to task") && continuing)) {
            if (!continuing) {
                if (!hasValidRouteSegmentIndex(player) || player.botPathSegmentIndex >= taskRouteSegments.length - 1) {
                    recoverRouteState(player, false);
                    return;
                }
                ++player.botPathSegmentIndex;
                player.botPathWaypointIndex = 0;
            }

            if (!hasValidRouteSegmentIndex(player)) {
                recoverRouteState(player, false);
                return;
            }

            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex];
            int regionId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1) {
                player.botTaskState = "walk to task";
                if (regionId == 12341) {
                    ArrayList<Integer> arrayList = new ArrayList<Integer>();
                    arrayList.add(1754);
                    player.interactWithBotObjectTargets(arrayList);
                    player.botRouteActionPending = true;
                    return;
                }
            }

            player.botTargetNpcId = 1804;
            return;
        }

        if (player.botTaskState.equals("walk towards bank") || (player.botTaskState.equals("walk to bank") && continuing)) {
            if (!continuing) {
                if (!hasValidRouteSegmentIndex(player) || player.botPathSegmentIndex <= 0) {
                    recoverRouteState(player, true);
                    return;
                }
                --player.botPathSegmentIndex;
                player.botPathWaypointIndex = 0;
            }

            if (!hasValidRouteSegmentIndex(player)) {
                recoverRouteState(player, true);
                return;
            }

            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
            int regionId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
            if (player.botPathSegmentIndex == taskRouteSegments.length - 2 && regionId == 12441) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1755);
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                player.botTaskState = "walk towards bank";
                return;
            }

            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
            } else {
                player.botTaskState = "walk towards bank";
            }
            player.botTargetNpcId = 1804;
        }
    }
}

