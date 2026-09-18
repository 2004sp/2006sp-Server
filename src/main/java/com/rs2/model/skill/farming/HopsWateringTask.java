package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HopsWateringTask
extends CycleEvent {
    private HopsPatchManager manager;
    private final HopsPatch patch;

    public HopsWateringTask(HopsPatchManager hopsPatchManager, HopsPatch hopsPatch) {
        this.manager = hopsPatchManager;
        this.patch = hopsPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 1;
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        HopsPatchManager.getPlayer(this.manager).setActionLocked(false);
        HopsPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

