package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HopsCompostTask
extends CycleEvent {
    private HopsPatchManager manager;
    private final HopsPatch patch;
    private final int compostItemId;

    public HopsCompostTask(HopsPatchManager hopsPatchManager, HopsPatch hopsPatch, int compostItemId) {
        this.manager = hopsPatchManager;
        this.patch = hopsPatch;
        this.compostItemId = compostItemId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.patch.getIndex();
        this.manager.diseaseChanceMultipliers[index] = this.manager.diseaseChanceMultipliers[index] * (this.compostItemId == 6032 ? 0.35 : 0.1);
        this.manager.patchStates[this.patch.getIndex()] = 5;
        int index2 = this.patch.getIndex();
        this.manager.harvestAmounts[index2] = this.manager.harvestAmounts[index2] + (this.compostItemId == 6032 ? 1 : 2);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        HopsPatchManager.getPlayer(this.manager).setActionLocked(false);
        HopsPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

