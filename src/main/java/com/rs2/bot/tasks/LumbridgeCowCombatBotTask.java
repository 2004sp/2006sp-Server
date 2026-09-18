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

public final class LumbridgeCowCombatBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3269, 3167, 0);
    private static int[] ignoredLootItemIds = new int[]{2132};
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(3273, 3167, 0), new Position(3275, 3176, 0), new Position(3270, 3178, 0), new Position(3269, 3193, 0), new Position(3269, 3212, 0), new Position(3268, 3228, 0)}), new BotRoute(new Position[]{new Position(3267, 3228, 0), new Position(3258, 3247, 0), new Position(3252, 3254, 0), new Position(3252, 3267, 0)}), new BotRoute(new Position[]{new Position(3253, 3267, 0), new Position(3258, 3277, 0)})};

    public LumbridgeCowCombatBotTask(int value2) {
        super(routeStartPosition, taskRouteSegments, 1, false, 10);
        boolean enabled = true;
        LumbridgeCowCombatBotTask lumbridgeCowCombatBotTask = this;
        this.combatTask = true;
        int[] integerValues = ignoredLootItemIds;
        lumbridgeCowCombatBotTask = this;
        ((BotTaskDefinition)this).ignoredLootItemIds = integerValues;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getCombatLevel() <= 30;
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
            int[] itemIds = foodDefinition.getItemIds();
            int length2 = itemIds.length;
            int index2 = 0;
            while (index2 < length2) {
                int value = itemIds[index2];
                if (player.ownsItemAmount(value, 10)) {
                    initialValue = value;
                    break;
                }
                ++index2;
            }
            if (initialValue != -1) break;
            ++index;
        }
        arrayList.add(new ItemStack(initialValue, 10));
        player.botFoodItemId = initialValue;
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(81);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botFoodItemId = 315;
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        Object value = new ItemStack[]{new ItemStack(player.botFoodItemId, 8)};
        player.botTaskRequiredItems = (ItemStack[])value;
        player.getInventoryManager().addItem(((ItemStack[])value)[0]);
        value = player;
        GameplayHelper.prepareBotCombatStyle((Player)value, -1);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 1 + GameUtil.randomInt(10);
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

    /*
     * Enabled aggressive block sorting
     */
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
            if (player.botPathSegmentIndex != taskRouteSegments.length - 1) {
                player.botTargetNpcId = 2883;
                return;
            }
            player.botTaskState = "walk to task";
        } else {
            if (!player.botTaskState.equals("walk towards bank") && (!player.botTaskState.equals("walk to bank") || !enabled2)) return;
            if (!enabled2) {
                --player.botPathSegmentIndex;
            }
            player.currentBotRoute = taskRouteSegments[player.botPathSegmentIndex].reversed();
            if (!enabled2) {
                player.botPathWaypointIndex = 0;
            }
            if (player.botPathSegmentIndex == 0) {
                player.botTaskState = "walk to bank";
                player.botTargetNpcId = 2883;
                return;
            }
        }
        player.botTargetNpcId = 1553;
    }
}

