package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.BushGrowthDefinition;
import com.rs2.model.skill.farming.BushPatch;
import com.rs2.model.skill.farming.BushPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class BushInspectTask
extends CycleEvent {
    private BushPatchManager manager;
    private final BushPatch patch;
    private final BushGrowthDefinition growthDefinition;

    public BushInspectTask(BushPatchManager bushPatchManager, BushPatch bushPatch, BushGrowthDefinition bushGrowthDefinition) {
        this.manager = bushPatchManager;
        this.patch = bushPatch;
        this.growthDefinition = bushGrowthDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.manager.growthStages[this.patch.getIndex()] - 4;
        if (this.growthDefinition.getGrowthMessages().length > index) {
            BushPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[index]);
        } else {
            BushPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[this.growthDefinition.getGrowthMessages().length - 1]);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        BushPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(1332);
        BushPatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

