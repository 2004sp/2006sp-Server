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
import com.rs2.model.skill.woodcutting.TreeDefinition;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class VarrockEastOakWoodcuttingBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3254, 3420, 0);
    private static BotRoute taskRoute = new BotRoute(new Position[]{new Position(3254, 3428, 0), new Position(3265, 3429, 0), new Position(3273, 3429, 0)});

    public VarrockEastOakWoodcuttingBotTask(int value3) {
        super(routeStartPosition, taskRoute, 0, false, 6);
        int value2 = 3439;
        VarrockEastOakWoodcuttingBotTask varrockEastOakWoodcuttingBotTask = this;
        this.targetMaxY = value2;
        value2 = 3414;
        varrockEastOakWoodcuttingBotTask = this;
        this.targetMinY = value2;
        value2 = 3274;
        varrockEastOakWoodcuttingBotTask = this;
        this.targetMinX = value2;
        value2 = 3284;
        varrockEastOakWoodcuttingBotTask = this;
        this.targetMaxX = value2;
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        return player.getSkillManager().getCurrentLevels()[8] >= 15;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getSkillManager().getCurrentLevels()[8] < 30;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        arrayList.add(new ItemStack(ItemCombinationHandler.getOwnedOrFallbackGatheringTool(player, 8).getToolItemId(), 1));
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        int[] integerValues = TreeDefinition.collectObjectIds(new TreeDefinition[]{TreeDefinition.OAK});
        int length = integerValues.length;
        int index = 0;
        while (index < length) {
            int value = integerValues[index];
            player.botInteractionTargetIds.add(value);
            ++index;
        }
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        if (player.getSkillManager().getCurrentLevels()[8] >= 41 && player.getSkillManager().getCurrentLevels()[0] >= 40) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1359));
        } else if (player.getSkillManager().getCurrentLevels()[8] >= 31 && player.getSkillManager().getCurrentLevels()[0] >= 30) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1357));
        } else if (player.getSkillManager().getCurrentLevels()[8] >= 21 && player.getSkillManager().getCurrentLevels()[0] >= 20) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1355));
        } else if (player.getSkillManager().getCurrentLevels()[8] >= 11 && player.getSkillManager().getCurrentLevels()[0] >= 10) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1361));
        } else if (player.getSkillManager().getCurrentLevels()[8] >= 6 && player.getSkillManager().getCurrentLevels()[0] >= 5) {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(1353));
        } else {
            player.getEquipmentManager().getContainer().setItem(3, new ItemStack(GameUtil.randomInt(2) == 0 ? 1349 : 1351));
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
        if ((value = value - value / 5 + GameUtil.randomInt(value2)) < 15) {
            value = 15;
        }
        BotCombatHelper.setBotSkillLevel(player, 8, value);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }
}

