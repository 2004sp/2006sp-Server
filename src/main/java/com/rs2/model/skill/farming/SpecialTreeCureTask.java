package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialTreeCureTask
extends CycleEvent {
    private SpecialTreePatchManager manager;

    public SpecialTreeCureTask(SpecialTreePatchManager specialTreePatchManager) {
        this.manager = specialTreePatchManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = SpecialTreePatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You cure the plant with a plant cure.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        SpecialTreePatchManager.getPlayer(this.manager).setActionLocked(false);
        SpecialTreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

