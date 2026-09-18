package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.AllotmentGrowthDefinition;
import com.rs2.model.skill.farming.AllotmentPatch;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AllotmentInspectTask
extends CycleEvent {
    private AllotmentPatchManager manager;
    private final AllotmentPatch patch;
    private final AllotmentGrowthDefinition growthDefinition;

    public AllotmentInspectTask(AllotmentPatchManager allotmentPatchManager, AllotmentPatch allotmentPatch, AllotmentGrowthDefinition allotmentGrowthDefinition) {
        this.manager = allotmentPatchManager;
        this.patch = allotmentPatch;
        this.growthDefinition = allotmentGrowthDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.manager.growthStages[this.patch.getIndex()] - 4;
        AllotmentPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[index]);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        AllotmentPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(1332);
        AllotmentPatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

