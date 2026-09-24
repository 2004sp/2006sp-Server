package com.rs2.model.task;

import com.rs2.model.Entity;
import com.rs2.model.task.CycleEvent;

public final class CycleEventContainer {
    private Entity entity;
    private boolean active;
    private int tickDelay;
    private CycleEvent event;
    private int elapsedTicks;

    public CycleEventContainer(Entity entity, CycleEvent cycleEvent, int tickDelay) {
        this.entity = entity;
        this.event = cycleEvent;
        this.active = true;
        this.elapsedTicks = 0;
        this.tickDelay = tickDelay;
    }

    public final void execute() {
        this.event.execute(this);
    }

    public final void stop() {
        this.active = false;
        this.event.onStop();
    }

    public final boolean tick() {
        if (++this.elapsedTicks >= this.tickDelay) {
            this.elapsedTicks = 0;
            return true;
        }
        return false;
    }

    public final Entity getEntity() {
        return this.entity;
    }

    public final boolean isActive() {
        return this.active;
    }

    public final void setTickDelay(int delayTicks) {
        this.tickDelay = delayTicks;
    }

    public final void resetElapsedTicks() {
        this.elapsedTicks = 0;
    }

    public final CycleEvent getEvent() {
        return this.event;
    }
}

