package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialCropCureTask
extends CycleEvent {
    private SpecialCropPatchManager manager;

    public SpecialCropCureTask(SpecialCropPatchManager specialCropPatchManager) {
        this.manager = specialCropPatchManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = SpecialCropPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You cure the plant with a plant cure.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        SpecialCropPatchManager.getPlayer(this.manager).setActionLocked(false);
        SpecialCropPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

