package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.FlowerGrowthDefinition;
import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FlowerInspectTask
extends CycleEvent {
    private FlowerPatchManager manager;
    private final FlowerPatch patch;
    private final FlowerGrowthDefinition growthDefinition;

    public FlowerInspectTask(FlowerPatchManager flowerPatchManager, FlowerPatch flowerPatch, FlowerGrowthDefinition flowerGrowthDefinition) {
        this.manager = flowerPatchManager;
        this.patch = flowerPatch;
        this.growthDefinition = flowerGrowthDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.manager.growthStages[this.patch.getIndex()] - 4;
        FlowerPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[index]);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        FlowerPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(1332);
        FlowerPatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

