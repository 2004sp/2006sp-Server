package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.HerbPatch;
import com.rs2.model.skill.farming.HerbPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HerbCompostTask
extends CycleEvent {
    private HerbPatchManager manager;
    private final HerbPatch patch;
    private final int compostItemId;

    public HerbCompostTask(HerbPatchManager herbPatchManager, HerbPatch herbPatch, int compostItemId) {
        this.manager = herbPatchManager;
        this.patch = herbPatch;
        this.compostItemId = compostItemId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int index = this.patch.getIndex();
        this.manager.diseaseChanceMultipliers[index] = this.manager.diseaseChanceMultipliers[index] * (this.compostItemId == 6032 ? 0.52 : 0.22);
        this.manager.patchStates[this.patch.getIndex()] = 4;
        int index2 = this.patch.getIndex();
        this.manager.harvestAmounts[index2] = this.manager.harvestAmounts[index2] + (this.compostItemId == 6032 ? 1 : 2);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        HerbPatchManager.getPlayer(this.manager).setActionLocked(false);
        HerbPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

