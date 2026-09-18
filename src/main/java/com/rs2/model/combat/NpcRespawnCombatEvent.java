package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class NpcRespawnCombatEvent
extends CycleEvent {
    private final Npc respawningNpc;
    private final Entity killer;

    public NpcRespawnCombatEvent(Npc npc, Entity entity) {
        this.respawningNpc = npc;
        this.killer = entity;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        CombatManager.finishDeath(this.respawningNpc, this.killer, false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

