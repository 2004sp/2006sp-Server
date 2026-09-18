package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.BushPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class BushCureTask
extends CycleEvent {
    private BushPatchManager manager;

    public BushCureTask(BushPatchManager bushPatchManager) {
        this.manager = bushPatchManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = BushPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You cure the plant with a plant cure.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        BushPatchManager.getPlayer(this.manager).setActionLocked(false);
        BushPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

