package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.TreePatch;
import com.rs2.model.skill.farming.TreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class TreeCompostTask
extends CycleEvent {
    private TreePatchManager manager;
    private final TreePatch patch;
    private final int compostItemId;

    public TreeCompostTask(TreePatchManager treePatchManager, TreePatch treePatch, int compostItemId) {
        this.manager = treePatchManager;
        this.patch = treePatch;
        this.compostItemId = compostItemId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.patch.getIndex();
        this.manager.diseaseChanceMultipliers[index] = this.manager.diseaseChanceMultipliers[index] * (this.compostItemId == 6032 ? 0.35 : 0.1);
        this.manager.patchStates[this.patch.getIndex()] = 5;
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        TreePatchManager.getPlayer(this.manager).setActionLocked(false);
        TreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

