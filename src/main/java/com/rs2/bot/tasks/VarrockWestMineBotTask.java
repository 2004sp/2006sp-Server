package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.skill.mining.MineableRockDefinition;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class VarrockWestMineBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3185, 3436, 0);
    private static BotRoute taskRoute = new BotRoute(new Position[]{new Position(3182, 3432, 0), new Position(3171, 3425, 0), new Position(3171, 3406, 0), new Position(3171, 3396, 0), new Position(3180, 3381, 0)});

    public VarrockWestMineBotTask(int value2) {
        super(routeStartPosition, taskRoute, 0, false, 3);
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[14] >= 15;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        arrayList.add(new ItemStack(ItemCombinationHandler.getOwnedOrFallbackGatheringTool(player, 14).getToolItemId(), 1));
        return arrayList;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getSkillManager().getCurrentLevels()[13] < 30;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        int value;
        int value2;
        int value3;
        int[] integerValues;
        player.botInteractionTargetIds.clear();
        if (player.getSkillManager().getCurrentLevels()[14] >= 20) {
            integerValues = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.SILVER});
            value3 = integerValues.length;
            value2 = 0;
            while (value2 < value3) {
                value = integerValues[value2];
                player.botInteractionTargetIds.add(value);
                ++value2;
            }
        }
        if (player.getSkillManager().getCurrentLevels()[14] >= 15) {
            integerValues = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.IRON});
            value3 = integerValues.length;
            value2 = 0;
            while (value2 < value3) {
                value = integerValues[value2];
                player.botInteractionTargetIds.add(value);
                ++value2;
            }
        }
        if (player.botMode != 4) {
            integerValues = MineableRockDefinition.collectObjectIds(new MineableRockDefinition[]{MineableRockDefinition.CLAY, MineableRockDefinition.TIN});
            value3 = integerValues.length;
            value2 = 0;
            while (value2 < value3) {
                value = integerValues[value2];
                player.botInteractionTargetIds.add(value);
                ++value2;
            }
        }
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
        BotCombatHelper.setBotSkillLevel(player, 14, value - value / 5 + GameUtil.randomInt(value2));
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }
}

