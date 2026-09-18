package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class KalphiteQueenRespawnCombatEvent
extends CycleEvent {
    private final Entity killer;

    public KalphiteQueenRespawnCombatEvent(Entity entity) {
        this.killer = entity;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        CombatManager.finishDeath(CombatManager.kalphiteQueenFirstForm, this.killer, false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

