package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AllotmentCureTask
extends CycleEvent {
    private AllotmentPatchManager manager;

    public AllotmentCureTask(AllotmentPatchManager allotmentPatchManager) {
        this.manager = allotmentPatchManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = AllotmentPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You cure the plant with a plant cure.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        AllotmentPatchManager.getPlayer(this.manager).setActionLocked(false);
        AllotmentPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

