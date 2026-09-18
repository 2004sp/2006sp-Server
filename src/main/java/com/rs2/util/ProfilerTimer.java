package com.rs2.util;

public final class ProfilerTimer {
    private long startTimeMillis;
    private long accumulatedMillis = 0L;

    public final void start() {
        this.startTimeMillis = System.currentTimeMillis();
    }

    public final void stop() {
        this.accumulatedMillis += System.currentTimeMillis() - this.startTimeMillis;
    }

    public final void reset() {
        this.accumulatedMillis = 0L;
    }

    public final long getAccumulatedMillis() {
        return this.accumulatedMillis;
    }
}

