package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.SpecialTreeGrowthDefinition;
import com.rs2.model.skill.farming.SpecialTreePatch;
import com.rs2.model.skill.farming.SpecialTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialTreeInspectTask
extends CycleEvent {
    private SpecialTreePatchManager manager;
    private final SpecialTreePatch patch;
    private final SpecialTreeGrowthDefinition growthDefinition;

    public SpecialTreeInspectTask(SpecialTreePatchManager specialTreePatchManager, SpecialTreePatch specialTreePatch, SpecialTreeGrowthDefinition specialTreeGrowthDefinition) {
        this.manager = specialTreePatchManager;
        this.patch = specialTreePatch;
        this.growthDefinition = specialTreeGrowthDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        SpecialTreeDefinition definition = SpecialTreeDefinition.forSaplingId(this.manager.treeIds[this.patch.getIndex()]);
        int completedCycles = this.manager.growthStages[this.patch.getIndex()] - 4;
        int index = definition == null ? completedCycles : definition.getGrowthMessageStage(completedCycles);
        if (this.growthDefinition.getGrowthMessages().length > index) {
            SpecialTreePatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[index]);
        } else {
            SpecialTreePatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[this.growthDefinition.getGrowthMessages().length - 1]);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        SpecialTreePatchManager.getPlayer(this.manager).getUpdateState().setAnimation(1332);
        SpecialTreePatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

