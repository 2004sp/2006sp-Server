package com.rs2.model.skill.farming;

import com.rs2.model.item.ItemStack;
import com.rs2.model.skill.farming.CompostBinManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class CompostBinEmptyTask
extends CycleEvent {
    private CompostBinManager manager;
    private final int actionSequence;
    private final int binIndex;
    private final int compostItemId;

    public CompostBinEmptyTask(CompostBinManager compostBinManager, int actionSequence, int binIndex, int compostItemId) {
        this.manager = compostBinManager;
        this.actionSequence = actionSequence;
        this.binIndex = binIndex;
        this.compostItemId = compostItemId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!CompostBinManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence) || !CompostBinManager.getPlayer(this.manager).getInventoryManager().getContainer().containsItem(1925) && this.manager.itemIds[this.binIndex] != 2518 || this.manager.states[this.binIndex] < 16) {
            ((CycleEventContainer)cycleEventContainer).stop();
            return;
        }
        CompostBinManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.compostItemId == 6032 ? 4.5 : 8.5);
        if (this.manager.itemIds[this.binIndex] != 2518) {
            CompostBinManager.getPlayer(this.manager).getInventoryManager().removeItem(new ItemStack(1925));
        }
        CompostBinManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(this.compostItemId));
        CompostBinManager.getPlayer(this.manager).getUpdateState().setAnimation(832, 0);
        int value = this.binIndex;
        this.manager.states[value] = this.manager.states[value] - 1;
        if (this.manager.states[this.binIndex] < 16) {
            int value2 = this.binIndex;
            this.manager.states[value2] = 0;
            this.manager.itemIds[value2] = 0;
            this.manager.lastUpdateTicks[value2] = 0L;
        }
        this.manager.refreshConfig();
    }

    @Override
    public final void onStop() {
        CompostBinManager.getPlayer(this.manager).resetAnimation();
    }
}

