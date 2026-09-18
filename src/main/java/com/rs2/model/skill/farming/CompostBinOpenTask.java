package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.CompostBinManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class CompostBinOpenTask
extends CycleEvent {
    private CompostBinManager manager;

    public CompostBinOpenTask(CompostBinManager compostBinManager) {
        this.manager = compostBinManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.refreshConfig();
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        CompostBinManager.getPlayer(this.manager).setActionLocked(false);
    }
}

