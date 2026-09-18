package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.task.DelayTimer;

public final class CombatTargetDelayTimer
extends DelayTimer {
    private Entity target;

    public CombatTargetDelayTimer(Entity entity, int value2) {
        super(0);
    }

    public final void setTargetDelay(Entity entity, int delayTicks) {
        this.target = entity;
        this.setDelayTicks(delayTicks);
        this.reset();
    }

    public final Entity getTarget() {
        return this.target;
    }
}

