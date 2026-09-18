package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.FarmedTreeGrowthDefinition;
import com.rs2.model.skill.farming.TreePatch;
import com.rs2.model.skill.farming.TreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class TreeInspectTask
extends CycleEvent {
    private TreePatchManager manager;
    private final TreePatch patch;
    private final FarmedTreeGrowthDefinition growthDefinition;

    public TreeInspectTask(TreePatchManager treePatchManager, TreePatch treePatch, FarmedTreeGrowthDefinition farmedTreeGrowthDefinition) {
        this.manager = treePatchManager;
        this.patch = treePatch;
        this.growthDefinition = farmedTreeGrowthDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.manager.growthStages[this.patch.getIndex()] - 4;
        TreePatchManager.getPlayer(this.manager).getDialogueManager().showStatement(this.growthDefinition.getGrowthMessages()[index]);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        TreePatchManager.getPlayer(this.manager).getUpdateState().setAnimation(1332);
        TreePatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

