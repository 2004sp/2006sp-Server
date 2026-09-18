package com.rs2.bot.shop;

import com.rs2.bot.BotRoute;
import com.rs2.bot.BotTaskDefinition;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.shop.CatherbyFishingShopTradeTickTask;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class CatherbyFishingShopBotTask
extends BotTaskDefinition {
    private static Position routeStartPosition = new Position(2809, 3441, 0);
    private static BotRoute taskRoute = new BotRoute(new Position[]{new Position(2808, 3437, 0), new Position(2820, 3437, 0), new Position(2833, 3439, 0)});

    public CatherbyFishingShopBotTask(int value2) {
        super(routeStartPosition, taskRoute, 1, true, 1);
        value2 = 2;
        CatherbyFishingShopBotTask catherbyFishingShopBotTask = this;
        this.interactionOption = 2;
        this.usesCustomTaskAction = true;
    }

    @Override
    public final int getShopId() {
        NpcDefinition npcDefinition = NpcDefinition.forId(576);
        return npcDefinition.getShopId();
    }

    @Override
    public final void configureTaskInteractionTargets(Player player) {
        player.botInteractionTargetIds.clear();
        player.botInteractionTargetIds.add(576);
    }

    @Override
    public final void prepareTaskInventory(Player player) {
        player.getInventoryManager().getContainer().clear();
        player.getEquipmentManager().getContainer().clear();
        player.getBankContainer().clear();
        player.botTaskItemId = 303;
        player.botShopItemAmount = 1;
        player.botShopBuyMode = GameUtil.randomInt(2);
        ItemStack[] itemStackArray = player.botShopBuyMode == 1 ? new ItemStack[]{new ItemStack(995, 10000)} : new ItemStack[]{new ItemStack(player.botTaskItemId, player.botShopItemAmount)};
        player.botTaskRequiredItems = itemStackArray;
        player.getInventoryManager().addItem(itemStackArray[0]);
        player.getInventoryManager().refresh();
        player.getEquipmentManager().refresh();
    }

    @Override
    public final void startCustomTaskAction(Player player) {
        ItemStack itemStack = new ItemStack(((Player)player).botTaskItemId, ((Player)player).botShopItemAmount);
        World.getTaskScheduler().schedule(new CatherbyFishingShopTradeTickTask(this, 2, (Player)player, itemStack));
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
}

