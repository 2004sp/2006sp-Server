package com.rs2.model.skill.herblore;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class UnfinishedPotionTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final ItemStack herbItem;
    private final int baseItemId;
    private final double unfinishedPotionItemId;

    public UnfinishedPotionTask(Player player, int actionSequence, ItemStack itemStack, int baseItemId, double unfinishedPotionItemId) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.herbItem = itemStack;
        this.baseItemId = baseItemId;
        this.unfinishedPotionItemId = unfinishedPotionItemId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        if (this.player.getInventoryManager().containsItemStack(this.herbItem) && this.player.getInventoryManager().containsItem(this.baseItemId)) {
            this.player.getInventoryManager().removeItem(this.herbItem);
            this.player.getInventoryManager().removeItem(new ItemStack(this.baseItemId));
            this.player.getInventoryManager().addItem(new ItemStack((int)this.unfinishedPotionItemId));
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

