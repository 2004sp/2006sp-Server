package com.rs2.util;

public final class ProfilerTimer {
    private long startTimeNanos;
    private long accumulatedNanos;

    public final void start() {
        this.startTimeNanos = System.nanoTime();
    }

    public final void stop() {
        this.accumulatedNanos += System.nanoTime() - this.startTimeNanos;
    }

    public final void reset() {
        this.accumulatedNanos = 0L;
    }

    public final long getAccumulatedMillis() {
        return this.accumulatedNanos / 1000000L;
    }
}

