package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.tasks.WildernessRuniteMineEscapeMonitorTickTask;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.mining.MineableRockDefinition;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class WildernessRuniteMineBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3096, 3494, 0);
    private static BotRoute taskRoute = new BotRoute(new Position[]{new Position(3092, 3500, 0), new Position(3083, 3501, 0), new Position(3075, 3506, 0), new Position(3073, 3521, 0), new Position(3061, 3533, 0), new Position(3061, 3550, 0), new Position(3060, 3565, 0), new Position(3062, 3583, 0), new Position(3062, 3598, 0), new Position(3063, 3617, 0), new Position(3064, 3629, 0), new Position(3065, 3637, 0), new Position(3060, 3644, 0), new Position(3060, 3659, 0), new Position(3062, 3670, 0), new Position(3069, 3682, 0), new Position(3074, 3694, 0), new Position(3074, 3711, 0), new Position(3073, 3730, 0), new Position(3067, 3746, 0), new Position(3057, 3758, 0), new Position(3044, 3766, 0), new Position(3032, 3777, 0), new Position(3030, 3793, 0), new Position(3021, 3807, 0), new Position(3012, 3821, 0), new Position(3007, 3833, 0), new Position(3007, 3848, 0), new Position(3009, 3863, 0), new Position(3020, 3875, 0), new Position(3032, 3884, 0), new Position(3047, 3885, 0), new Position(3057, 3885, 0)});

    public WildernessRuniteMineBotTask(int value2) {
        super(routeStartPosition, taskRoute, 0, false, 1);
        this.usesEscapeMonitor = true;
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[14] >= 85;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        arrayList.add(new ItemStack(ItemCombinationHandler.getOwnedOrFallbackGatheringTool(player, 14).getToolItemId(), 1));
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
                    if (player.ownsItemAmount(value, 4)) {
                        initialValue = value;
                        break;
                    }
                    ++index2;
                }
                if (initialValue != -1) break;
            }
            ++index;
        }
        arrayList.add(new ItemStack(initialValue, 4));
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        int[] integerValues = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.RUNITE});
        int length = integerValues.length;
        int index = 0;
        while (index < length) {
            int value = integerValues[index];
            player.botInteractionTargetIds.add(value);
            ++index;
        }
    }

    @Override
    public final void startEscapeMonitor(Player player) {
        World.getTaskScheduler().schedule(new WildernessRuniteMineEscapeMonitorTickTask(this, 2, (Player)player));
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        if (player.getSkillManager().getCurrentLevels()[14] >= 41 && player.getSkillManager().getCurrentLevels()[0] >= 40) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1275));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 31 && player.getSkillManager().getCurrentLevels()[0] >= 30) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1271));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 21 && player.getSkillManager().getCurrentLevels()[0] >= 20) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1273));
        } else if (player.getSkillManager().getCurrentLevels()[14] >= 6 && player.getSkillManager().getCurrentLevels()[0] >= 5) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1269));
        } else {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(GameUtil.randomInt(2) == 0 ? 1267 : 1265));
        }
        player.botFoodItemId = 379;
        player.getBankContainer().addToTab(new ItemStack(player.botFoodItemId, 1000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(player.botFoodItemId, 4)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        if (SpellDefinition.VARROCK_TELEPORT.getRequiredLevel() <= player.getSkillManager().getCurrentLevels()[6]) {
            BotCombatHelper.grantBotSpellRunes(player, SpellDefinition.VARROCK_TELEPORT, 1);
        }
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
        if ((value = value - value / 5 + GameUtil.randomInt(value2)) < 85) {
            value = 85;
        }
        BotCombatHelper.setBotSkillLevel(player, 14, value);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }
}

