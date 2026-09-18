package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FlowerCompostTask
extends CycleEvent {
    private FlowerPatchManager manager;
    private final FlowerPatch patch;
    private final int compostItemId;

    public FlowerCompostTask(FlowerPatchManager flowerPatchManager, FlowerPatch flowerPatch, int compostItemId) {
        this.manager = flowerPatchManager;
        this.patch = flowerPatch;
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
        FlowerPatchManager.getPlayer(this.manager).setActionLocked(false);
        FlowerPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

