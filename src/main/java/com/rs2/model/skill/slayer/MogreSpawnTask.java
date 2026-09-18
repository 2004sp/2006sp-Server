package com.rs2.model.skill.slayer;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.skill.slayer.SlayerManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class MogreSpawnTask
extends CycleEvent {
    private SlayerManager slayerManager;

    public MogreSpawnTask(SlayerManager slayerManager) {
        this.slayerManager = slayerManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        GameplayHelper.spawnNpcTargetingEntityAtPosition(SlayerManager.getPlayer(this.slayerManager), SlayerManager.MOGRE_SPAWN, new Position(SlayerManager.getPlayer(this.slayerManager).getPosition().getX(), SlayerManager.getPlayer(this.slayerManager).getPosition().getY()), false, null);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        SlayerManager.getPlayer(this.slayerManager).setActionLocked(false);
    }
}

