package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HopsCureTask
extends CycleEvent {
    private HopsPatchManager manager;

    public HopsCureTask(HopsPatchManager hopsPatchManager) {
        this.manager = hopsPatchManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = HopsPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You cure the plant with a plant cure.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        HopsPatchManager.getPlayer(this.manager).setActionLocked(false);
        HopsPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

