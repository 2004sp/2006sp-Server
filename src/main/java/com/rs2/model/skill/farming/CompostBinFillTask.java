package com.rs2.model.skill.farming;

import com.rs2.model.item.ItemStack;
import com.rs2.model.skill.farming.CompostBinManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class CompostBinFillTask
extends CycleEvent {
    private CompostBinManager manager;
    private final int actionSequence;
    private final int itemId;
    private final int binIndex;
    private final int fillAmount;

    public CompostBinFillTask(CompostBinManager compostBinManager, int actionSequence, int itemId, int binIndex, int fillAmount) {
        this.manager = compostBinManager;
        this.actionSequence = actionSequence;
        this.itemId = itemId;
        this.binIndex = binIndex;
        this.fillAmount = fillAmount;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!CompostBinManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence) || !CompostBinManager.getPlayer(this.manager).getInventoryManager().getContainer().containsItem(this.itemId) || this.manager.states[this.binIndex] == 15) {
            cycleEventContainer.stop();
            return;
        }
        CompostBinManager.getPlayer(this.manager).getUpdateState().setAnimation(832, 0);
        CompostBinManager.getPlayer(this.manager).getInventoryManager().removeItem(new ItemStack(this.itemId));
        int value = this.binIndex;
        this.manager.states[value] = this.manager.states[value] + this.fillAmount;
        this.manager.refreshConfig();
    }

    @Override
    public final void onStop() {
        CompostBinManager.getPlayer(this.manager).resetAnimation();
    }
}

