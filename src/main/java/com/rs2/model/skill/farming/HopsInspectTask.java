package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.HopsGrowthDefinition;
import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HopsInspectTask
extends CycleEvent {
    private HopsPatchManager manager;
    private final HopsPatch patch;
    private final HopsGrowthDefinition growthDefinition;

    public HopsInspectTask(HopsPatchManager hopsPatchManager, HopsPatch hopsPatch, HopsGrowthDefinition hopsGrowthDefinition) {
        this.manager = hopsPatchManager;
        this.patch = hopsPatch;
        this.growthDefinition = hopsGrowthDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.manager.growthStages[this.patch.getIndex()] - 4;
        if (this.growthDefinition.getGrowthMessages().length > index) {
            HopsPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[index]);
        } else {
            HopsPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[this.growthDefinition.getGrowthMessages().length - 1]);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        HopsPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(1332);
        HopsPatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

