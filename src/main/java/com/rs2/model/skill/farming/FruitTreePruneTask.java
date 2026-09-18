package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FruitTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FruitTreePruneTask
extends CycleEvent {
    private FruitTreePatchManager manager;

    public FruitTreePruneTask(FruitTreePatchManager fruitTreePatchManager) {
        this.manager = fruitTreePatchManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = FruitTreePatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You prune the area with your secateurs.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        FruitTreePatchManager.getPlayer(this.manager).setActionLocked(false);
        FruitTreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

