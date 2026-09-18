package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.EntityReference;
import com.rs2.model.task.DelayTimer;

public final class PvpCombatReference
extends EntityReference {
    private DelayTimer timer;

    public PvpCombatReference(Entity entity, int value2) {
        super(entity);
        this.timer = new DelayTimer(value2);
    }

    public final boolean hasExpired() {
        return this.timer.hasElapsed();
    }

    public final void resetTimer() {
        this.timer.reset();
    }

    public final int getRemainingTicks() {
        return this.timer.getRemainingTicks();
    }
}

