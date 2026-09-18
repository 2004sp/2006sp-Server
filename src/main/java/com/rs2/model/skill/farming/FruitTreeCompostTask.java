package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.FruitTreePatch;
import com.rs2.model.skill.farming.FruitTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FruitTreeCompostTask
extends CycleEvent {
    private FruitTreePatchManager manager;
    private final FruitTreePatch patch;
    private final int compostItemId;

    public FruitTreeCompostTask(FruitTreePatchManager fruitTreePatchManager, FruitTreePatch fruitTreePatch, int compostItemId) {
        this.manager = fruitTreePatchManager;
        this.patch = fruitTreePatch;
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
        FruitTreePatchManager.getPlayer(this.manager).setActionLocked(false);
        FruitTreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

