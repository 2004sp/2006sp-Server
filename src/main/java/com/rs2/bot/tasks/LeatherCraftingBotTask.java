package com.rs2.bot.tasks;

import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class LeatherCraftingBotTask
extends BotTaskDefinition {
    private static Position leatherCraftingStartPosition = new Position(3269, 3167, 0);

    public LeatherCraftingBotTask(int value2) {
        super(0, false, 1);
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(2728);
    }

    @Override
    public final ArrayList getRequiredItems(Player player) {
        ArrayList<ItemStack> requiredItems = new ArrayList<ItemStack>();
        requiredItems.add(new ItemStack(1734, 200));
        requiredItems.add(new ItemStack(1733, 1));
        return requiredItems;
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botTaskItemId = 1741;
        player.getBankContainer().addToTab(new ItemStack(player.botTaskItemId, 5000), 0);
        player.getBankContainer().addToTab(new ItemStack(1734, 5000), 0);
        ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(1733, 1), new ItemStack(1734, 26), new ItemStack(player.botTaskItemId, 26)};
        player.botTaskRequiredItems = itemStackArray;
        player.moveTo(leatherCraftingStartPosition);
        player.getInventoryManager().addItem(itemStackArray[0]);
        player.getInventoryManager().addItem(itemStackArray[1]);
        player.getInventoryManager().addItem(itemStackArray[2]);
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
        player.getSkillManager().getExperience()[3] = BotCombatHelper.calculateBotHitpointsExperience(player);
        int[] skillManager = player.getSkillManager().getCurrentLevels();
        player.getSkillManager();
        skillManager[3] = SkillManager.getLevelForExperience(player.getSkillManager().getExperience()[3]);
        player.getSkillManager().refreshAllSkills();
    }

    @Override
    public final void startWalkToTask(Player player) {
        player.botTaskState = "do task";
        if (player.getInventoryManager().getContainer().indexOfItem(player.botTaskItemId) == -1 || player.getInventoryManager().getContainer().indexOfItem(1733) == -1) {
            this.startWalkToBank(player);
            return;
        }
        GameplayHelper.handleLeatherCraftingItemUse(player, 1733, player.botTaskItemId, player.getInventoryManager().getContainer().indexOfItem(1733), player.getInventoryManager().getContainer().indexOfItem(player.botTaskItemId));
        GameplayHelper.handleCraftedArmorButton(player, -1, 26);
    }

    @Override
    public final void startWalkToBank(Player player) {
        player.botTaskState = "empty inventory";
        if (player.currentBotTask.usesDepositBox) {
            player.botInteractionOption = 2;
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(2619);
            player.interactWithBotNpcTargets(arrayList);
            return;
        }
        player.botInteractionOption = 2;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(2213);
        arrayList.add(11758);
        player.interactWithBotObjectTargets(arrayList);
    }

    @Override
    public final void continueWalkToTask(Player player, int value2) {
        this.startWalkToTask(player);
    }

    @Override
    public final void continueWalkToBank(Player player, int value2) {
        this.startWalkToBank(player);
    }
}

