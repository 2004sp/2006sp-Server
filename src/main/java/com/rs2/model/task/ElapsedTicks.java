package com.rs2.model.task;

import com.rs2.model.World;

public final class ElapsedTicks {
    private int lastResetTick;

    public ElapsedTicks() {
        ElapsedTicks elapsedTicks = this;
        this.lastResetTick = World.tickCount;
    }

    public final int elapsed() {
        return World.tickCount - this.lastResetTick;
    }

    public final void reset() {
        this.lastResetTick = World.tickCount;
    }
}

