package com.rs2.model.task;

import com.rs2.model.World;

public class DelayTimer {
    private int startTick;
    private int delayTicks;

    public DelayTimer(int value3) {
        int value2 = value3;
        DelayTimer delayTimer = this;
        this.delayTicks = value2;
        delayTimer = this;
        this.startTick = World.tickCount;
    }

    public final int getDelayTicks() {
        return this.delayTicks;
    }

    public final void setDelayTicks(int delayTicks) {
        this.delayTicks = delayTicks;
    }

    public final void reset() {
        this.startTick = World.tickCount;
    }

    public final boolean hasElapsed() {
        return World.tickCount - this.startTick >= this.delayTicks;
    }

    public final int getRemainingTicks() {
        return this.delayTicks - (World.tickCount - this.startTick);
    }
}

