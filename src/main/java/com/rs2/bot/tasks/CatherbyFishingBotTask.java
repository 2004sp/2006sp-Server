package com.rs2.bot.tasks;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class CatherbyFishingBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(2809, 3441, 0);
    private static BotRoute taskRoute = new BotRoute(new Position[]{new Position(2809, 3437, 0), new Position(2816, 3436, 0), new Position(2830, 3436, 0), new Position(2842, 3435, 0)});

    public CatherbyFishingBotTask(int value2) {
        super(routeStartPosition, taskRoute, 1, true, 2);
    }

    @Override
    public final boolean meetsUnlockRequirements(Player player) {
        if (player.getCombatLevel() <= 20) {
            return false;
        }
        return player.getSkillManager().getCurrentLevels()[10] >= 40 && player.getSkillManager().getCurrentLevels()[7] >= 40;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        if (player.getSkillManager().getCurrentLevels()[10] >= 76 && player.getSkillManager().getCurrentLevels()[7] >= 80) {
            arrayList.add(new ItemStack(311, 1));
        } else {
            arrayList.add(new ItemStack(301, 1));
        }
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        if (player.botTaskRequiredItems[0].getId() == 311) {
            player.botInteractionTargetIds.add(313);
            return;
        }
        if (player.botTaskRequiredItems[0].getId() == 301) {
            player.botInteractionTargetIds.add(312);
            return;
        }
        if (player.botTaskRequiredItems[0].getId() == 305) {
            player.botInteractionTargetIds.add(313);
        }
    }

    @Override
    public final int getInteractionOption(Player player) {
        if (player.botTaskRequiredItems[0].getId() == 311) {
            return 2;
        }
        return 1;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        ItemStack[] itemStackArray = player.getSkillManager().getCurrentLevels()[10] >= 76 ? new ItemStack[]{new ItemStack(311)} : (player.getSkillManager().getCurrentLevels()[10] >= 40 ? new ItemStack[]{new ItemStack(301)} : new ItemStack[]{new ItemStack(305)});
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
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
        if ((value = value - value / 5 + GameUtil.randomInt(value2)) < 16) {
            value = 16;
        }
        BotCombatHelper.setBotSkillLevel(player, 10, value);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }
}

