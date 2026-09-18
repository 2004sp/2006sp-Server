package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FruitTreePatch;
import com.rs2.model.skill.farming.FruitTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FruitTreeCuttingTask
extends CycleEvent {
    private FruitTreePatchManager manager;
    private final int actionSequence;
    private final FruitTreePatch patch;

    public FruitTreeCuttingTask(FruitTreePatchManager fruitTreePatchManager, int actionSequence, FruitTreePatch fruitTreePatch) {
        this.manager = fruitTreePatchManager;
        this.actionSequence = actionSequence;
        this.patch = fruitTreePatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!FruitTreePatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        Player player = FruitTreePatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You cut down the tree.");
        this.manager.patchStates[this.patch.getIndex()] = 6;
        this.manager.refreshConfig();
        cycleEventContainer.stop();
        FruitTreePatchManager.getPlayer(this.manager).getUpdateState().setAnimation(-1, 0);
    }

    @Override
    public final void onStop() {
    }
}

