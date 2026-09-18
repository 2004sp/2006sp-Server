package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.SpecialCropPatch;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialCropCompostTask
extends CycleEvent {
    private SpecialCropPatchManager manager;
    private final SpecialCropPatch patch;
    private final int compostItemId;

    public SpecialCropCompostTask(SpecialCropPatchManager specialCropPatchManager, SpecialCropPatch specialCropPatch, int compostItemId) {
        this.manager = specialCropPatchManager;
        this.patch = specialCropPatch;
        this.compostItemId = compostItemId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.patch.getIndex();
        this.manager.diseaseChanceMultipliers[index] = this.manager.diseaseChanceMultipliers[index] * (this.compostItemId == 6032 ? 0.35 : 0.1);
        this.manager.patchStates[this.patch.getIndex()] = 5;
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        SpecialCropPatchManager.getPlayer(this.manager).setActionLocked(false);
        SpecialCropPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

