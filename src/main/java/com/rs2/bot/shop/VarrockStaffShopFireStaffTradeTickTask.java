package com.rs2.bot.shop;

import com.rs2.bot.shop.VarrockStaffShopFireStaffBotTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class VarrockStaffShopFireStaffTradeTickTask
extends TickTask {
    private final Player player;
    private final ItemStack shopItem;

    public VarrockStaffShopFireStaffTradeTickTask(VarrockStaffShopFireStaffBotTask varrockStaffShopFireStaffBotTask, int value2, Player player, ItemStack itemStack) {
        super(2);
        this.player = player;
        this.shopItem = itemStack;
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.isRegistered() || !this.player.currentBotTask.usesCustomTaskAction || !this.player.botTaskState.equals("do task")) {
            this.stop();
            return;
        }
        if (this.player.getOpenInterfaceId() == 3824) {
            if (this.player.temporaryActionValue <= 0) {
                if (this.player.botShopBuyMode == 1) {
                    ShopManager.buyItemStack(this.player, this.shopItem);
                    return;
                }
                ShopManager.sellItemStack(this.player, this.shopItem);
                return;
            }
            --this.player.temporaryActionValue;
            return;
        }
        this.player.temporaryActionValue = 3 + GameUtil.randomInt(3);
        this.player.botInteractionOption = 2;
        this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
    }
}

