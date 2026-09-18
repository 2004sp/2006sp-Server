package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FlowerCureTask
extends CycleEvent {
    private FlowerPatchManager manager;

    public FlowerCureTask(FlowerPatchManager flowerPatchManager) {
        this.manager = flowerPatchManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = FlowerPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You cure the plant with a plant cure.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        FlowerPatchManager.getPlayer(this.manager).setActionLocked(false);
        FlowerPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

