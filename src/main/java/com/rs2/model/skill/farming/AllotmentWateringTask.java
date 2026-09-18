package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.AllotmentPatch;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AllotmentWateringTask
extends CycleEvent {
    private AllotmentPatchManager manager;
    private final AllotmentPatch patch;

    public AllotmentWateringTask(AllotmentPatchManager allotmentPatchManager, AllotmentPatch allotmentPatch) {
        this.manager = allotmentPatchManager;
        this.patch = allotmentPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 1;
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        AllotmentPatchManager.getPlayer(this.manager).setActionLocked(false);
        AllotmentPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

