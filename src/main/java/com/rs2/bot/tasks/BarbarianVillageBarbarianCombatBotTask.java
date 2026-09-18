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

public final class BarbarianVillageBarbarianCombatBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3094, 3491, 0);
    private static BotRoute taskRoute = new BotRoute(new Position[]{new Position(3090, 3490, 0), new Position(3081, 3483, 0), new Position(3080, 3467, 0), new Position(3086, 3464, 0), new Position(3086, 3451, 0), new Position(3086, 3440, 0), new Position(3083, 3434, 0), new Position(3079, 3433, 0), new Position(3079, 3425, 0)});
    private static int[] ignoredLootItemIds = new int[]{956, 1379, 1592, 1917, 6814};

    public BarbarianVillageBarbarianCombatBotTask(int value2) {
        super(routeStartPosition, taskRoute, 1, false, 2);
        boolean enabled = true;
        BarbarianVillageBarbarianCombatBotTask barbarianVillageBarbarianCombatBotTask = this;
        this.combatTask = true;
        int[] integerValues = ignoredLootItemIds;
        barbarianVillageBarbarianCombatBotTask = this;
        ((BotTaskDefinition)this).ignoredLootItemIds = integerValues;
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getCombatLevel() >= 12;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getCombatLevel() < 26;
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
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(3246);
        player.botInteractionTargetIds.add(3247);
        player.botInteractionTargetIds.add(3249);
        player.botInteractionTargetIds.add(12);
        player.botInteractionTargetIds.add(17);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botFoodItemId = 333;
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
        int value = 15 + GameUtil.randomInt(5);
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
}

