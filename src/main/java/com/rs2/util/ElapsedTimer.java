package com.rs2.util;

public final class ElapsedTimer {
    private long startTimeMillis = System.currentTimeMillis();

    public final void reset() {
        this.startTimeMillis = System.currentTimeMillis();
    }

    public final long elapsedMillis() {
        return System.currentTimeMillis() - this.startTimeMillis;
    }
}

