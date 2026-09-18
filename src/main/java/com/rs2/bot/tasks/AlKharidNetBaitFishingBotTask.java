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

public final class AlKharidNetBaitFishingBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(3269, 3166, 0);
    private static BotRoute taskRoute = new BotRoute(new Position[]{new Position(3273, 3166, 0), new Position(3273, 3155, 0), new Position(3272, 3145, 0)});

    public AlKharidNetBaitFishingBotTask(int value3) {
        super(routeStartPosition, taskRoute, 1, false, 4);
        int value2 = 20;
        AlKharidNetBaitFishingBotTask alKharidNetBaitFishingBotTask = this;
        this.targetSearchRadius = value2;
    }

    @Override
    public final boolean isWithinProgressionRange(Player player) {
        return player.getSkillManager().getCurrentLevels()[10] < 20 || player.getSkillManager().getCurrentLevels()[7] < 15;
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        if (player.getSkillManager().getCurrentLevels()[10] >= 5) {
            arrayList.add(new ItemStack(307, 1));
            arrayList.add(new ItemStack(313, 200));
        } else {
            arrayList.add(new ItemStack(303, 1));
        }
        return arrayList;
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(316);
    }

    @Override
    public final int getInteractionOption(Player player) {
        if (player.botTaskRequiredItems[0].getId() == 307) {
            return 2;
        }
        return 1;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        ItemStack[] itemStackArray;
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        if (player.getSkillManager().getCurrentLevels()[10] >= 5) {
            player.getBankContainer().addToTab(new ItemStack(313, 5000), 0);
            itemStackArray = new ItemStack[]{new ItemStack(307), new ItemStack(313, 30)};
            player.getInventoryManager().addItem(itemStackArray[0]);
            player.getInventoryManager().addItem(itemStackArray[1]);
        } else {
            itemStackArray = new ItemStack[]{new ItemStack(303)};
            player.getInventoryManager().addItem(itemStackArray[0]);
        }
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void prepareTaskCombatLoadout(Player player) {
        GameplayHelper.resetBotSkillsToBase(player);
        int value = 1 + GameUtil.randomInt(40);
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
        value = value - value / 5 + GameUtil.randomInt(value2);
        BotCombatHelper.setBotSkillLevel(player, 10, value);
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }
}

