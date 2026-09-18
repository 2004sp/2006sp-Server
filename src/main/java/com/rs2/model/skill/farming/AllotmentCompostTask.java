package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.AllotmentPatch;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AllotmentCompostTask
extends CycleEvent {
    private AllotmentPatchManager manager;
    private final AllotmentPatch patch;
    private final int compostItemId;

    public AllotmentCompostTask(AllotmentPatchManager allotmentPatchManager, AllotmentPatch allotmentPatch, int compostItemId) {
        this.manager = allotmentPatchManager;
        this.patch = allotmentPatch;
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
        AllotmentPatchManager.getPlayer(this.manager).setActionLocked(false);
        AllotmentPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

