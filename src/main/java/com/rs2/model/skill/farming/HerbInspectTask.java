package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.HerbGrowthDefinition;
import com.rs2.model.skill.farming.HerbPatch;
import com.rs2.model.skill.farming.HerbPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HerbInspectTask
extends CycleEvent {
    private HerbPatchManager manager;
    private final HerbPatch patch;
    private final HerbGrowthDefinition growthDefinition;

    public HerbInspectTask(HerbPatchManager herbPatchManager, HerbPatch herbPatch, HerbGrowthDefinition herbGrowthDefinition) {
        this.manager = herbPatchManager;
        this.patch = herbPatch;
        this.growthDefinition = herbGrowthDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.manager.growthStages[this.patch.getIndex()] - 4;
        HerbPatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[index]);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        HerbPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(1332);
        HerbPatchManager.getPlayer(this.manager).setActionLocked(false);
        HerbPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

