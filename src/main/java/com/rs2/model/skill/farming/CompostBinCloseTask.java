package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.CompostBinManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class CompostBinCloseTask
extends CycleEvent {
    private CompostBinManager manager;

    public CompostBinCloseTask(CompostBinManager compostBinManager) {
        this.manager = compostBinManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = CompostBinManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You close the compost bin, and its content start to rot.");
        this.manager.refreshConfig();
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        CompostBinManager.getPlayer(this.manager).setActionLocked(false);
    }
}

