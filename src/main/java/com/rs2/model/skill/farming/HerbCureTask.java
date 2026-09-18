package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.HerbPatch;
import com.rs2.model.skill.farming.HerbPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HerbCureTask
extends CycleEvent {
    private HerbPatchManager manager;
    private final HerbPatch patch;

    public HerbCureTask(HerbPatchManager herbPatchManager, HerbPatch herbPatch) {
        this.manager = herbPatchManager;
        this.patch = herbPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = HerbPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You cure the plant with a plant cure.");
        this.manager.patchStates[this.patch.getIndex()] = 0;
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        HerbPatchManager.getPlayer(this.manager).setActionLocked(false);
        HerbPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

