package com.rs2.model.task;

public abstract class TickTask {
    private int remainingTicks;
    private int intervalTicks;
    private boolean active = true;
    private boolean executeImmediately = true;

    public TickTask(int value2) {
        this(value2, false);
    }

    public TickTask(int remainingTicks, boolean executeImmediately) {
        this.remainingTicks = remainingTicks;
        this.intervalTicks = remainingTicks;
        this.executeImmediately = executeImmediately;
    }

    public final int getIntervalTicks() {
        return this.intervalTicks;
    }

    public final int getRemainingTicks() {
        return this.remainingTicks;
    }

    public final void setIntervalTicks(int ticks) {
        if (ticks < 0) {
            throw new IllegalArgumentException("Tick amount must be positive.");
        }
        this.intervalTicks = ticks;
    }

    public final void setRemainingTicks(int ticks) {
        if (ticks < 0) {
            throw new IllegalArgumentException("Tick amount must be positive.");
        }
        this.remainingTicks = ticks;
    }

    public final boolean isActive() {
        return this.active;
    }

    public void stop() {
        this.active = false;
    }

    public abstract void execute();

    public final void tick() {
        TickTask tickTask = this;
        if (tickTask.executeImmediately) {
            this.execute();
            this.executeImmediately = false;
            return;
        }
        if (this.remainingTicks-- <= 1) {
            tickTask = this;
            if (tickTask.active) {
                this.execute();
            }
            this.remainingTicks = this.intervalTicks;
        }
    }
}

