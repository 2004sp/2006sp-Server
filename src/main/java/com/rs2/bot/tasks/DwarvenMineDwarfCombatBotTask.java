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

public final class DwarvenMineDwarfCombatBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(2946, 3368, 0);
    private static BotRoute[] taskRouteSegments = new BotRoute[]{new BotRoute(new Position[]{new Position(2946, 3374, 0), new Position(2954, 3379, 0), new Position(2961, 3384, 0), new Position(2967, 3396, 0), new Position(2975, 3410, 0), new Position(2987, 3421, 0), new Position(2989, 3428, 0), new Position(3005, 3433, 0), new Position(3022, 3437, 0), new Position(3031, 3447, 0), new Position(3040, 3458, 0), new Position(3042, 3470, 0), new Position(3033, 3470, 0), new Position(3022, 3462, 0), new Position(3016, 3455, 0)}), new BotRoute(new Position[]{new Position(3020, 9839, 0)})};

    public DwarvenMineDwarfCombatBotTask(int value2) {
        super(routeStartPosition, taskRouteSegments, 1, false, 1);
        value2 = 1;
        DwarvenMineDwarfCombatBotTask dwarvenMineDwarfCombatBotTask = this;
        this.combatTask = true;
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getCombatLevel() > 7;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getCombatLevel() < 16;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(118);
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
                if (player.ownsItemAmount(value, 16)) {
                    initialValue = value;
                    break;
                }
                ++index2;
            }
            if (initialValue != -1) break;
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
        player.botFoodItemId = 315;
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        Object value = new ItemStack[]{new ItemStack(player.botFoodItemId, 20)};
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
        int value = 3 + GameUtil.randomInt(8);
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
            if (player.botPathSegmentIndex == taskRouteSegments.length - 1) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                int value = !GameplayHelper.isObjectDefinitionIdValid(11867) ? 1570 : 11867;
                arrayList.add(value);
                player.botInteractionOption = 1;
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
            if (player.botPathSegmentIndex == 0 && regionId == 12185) {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                arrayList.add(1755);
                player.botInteractionOption = 1;
                player.interactWithBotObjectTargets(arrayList);
                player.botRouteActionPending = true;
                player.botTaskState = "walk to bank";
            }
        }
    }
}

