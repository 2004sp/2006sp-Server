package com.rs2.model.skill.slayer;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.npc.Npc;
import com.rs2.model.skill.slayer.SlayerManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class ZygomiteSpawnTask
extends CycleEvent {
    private SlayerManager slayerManager;
    private final Npc sourceNpc;
    private final int spawnX;
    private final int spawnY;

    public ZygomiteSpawnTask(SlayerManager slayerManager, Npc npc, int spawnX, int spawnY) {
        this.slayerManager = slayerManager;
        this.sourceNpc = npc;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.sourceNpc.setActive(false);
        GameplayHelper.spawnNpcTargetingEntityAtPosition(SlayerManager.getPlayer(this.slayerManager), this.sourceNpc.getDefinition().getId() == 3344 ? SlayerManager.ZYGOMITE_SPAWN_A : SlayerManager.ZYGOMITE_SPAWN_B, new Position(this.spawnX, this.spawnY), false, null);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

