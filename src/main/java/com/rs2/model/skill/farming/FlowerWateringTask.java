package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FlowerWateringTask
extends CycleEvent {
    private FlowerPatchManager manager;
    private final FlowerPatch patch;

    public FlowerWateringTask(FlowerPatchManager flowerPatchManager, FlowerPatch flowerPatch) {
        this.manager = flowerPatchManager;
        this.patch = flowerPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 1;
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        FlowerPatchManager.getPlayer(this.manager).setActionLocked(false);
        FlowerPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

