package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.SpecialCropGrowthDefinition;
import com.rs2.model.skill.farming.SpecialCropPatch;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialCropInspectTask
extends CycleEvent {
    private SpecialCropPatchManager manager;
    private final SpecialCropPatch patch;
    private final SpecialCropGrowthDefinition growthDefinition;

    public SpecialCropInspectTask(SpecialCropPatchManager specialCropPatchManager, SpecialCropPatch specialCropPatch, SpecialCropGrowthDefinition specialCropGrowthDefinition) {
        this.manager = specialCropPatchManager;
        this.patch = specialCropPatch;
        this.growthDefinition = specialCropGrowthDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.manager.growthStages[this.patch.getIndex()] - 4;
        if (this.growthDefinition.getGrowthMessages().length > index) {
            SpecialCropPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[index]);
        } else {
            SpecialCropPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[this.growthDefinition.getGrowthMessages().length - 1]);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        SpecialCropPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(1332);
        SpecialCropPatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

