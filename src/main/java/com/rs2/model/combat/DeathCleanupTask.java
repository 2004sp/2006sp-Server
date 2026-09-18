package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.task.TickTask;

public final class DeathCleanupTask
extends TickTask {
    private final Entity defeatedEntity;
    private final Entity killer;

    public DeathCleanupTask(int value2, Entity entity, Entity entity2) {
        super(value2);
        this.defeatedEntity = entity;
        this.killer = entity2;
    }

    @Override
    public final void execute() {
        CombatManager.finishDeath(this.defeatedEntity, this.killer, true);
        this.stop();
    }
}

