package com.rs2.util;

import com.rs2.ConnectionThrottleSettings;
import com.rs2.Server;

public final class DelayedShutdownTask
implements Runnable {
    private final long delayMillis;

    public DelayedShutdownTask(int delayMillis) {
        this.delayMillis = delayMillis * 1000;
    }

    @Override
    public final void run() {
        try {
            Thread.sleep(this.delayMillis);
        }
        catch (InterruptedException interruptedException) {
            InterruptedException interruptedException2 = interruptedException;
            interruptedException.printStackTrace();
        }
        ConnectionThrottleSettings.connectionsEnabled = false;
        if (Boolean.getBoolean("prs.traceGameplay")) {
            new Exception("[exit-trace] DelayedShutdownTask calling System.exit").printStackTrace();
        }
        System.exit(0);
        Server.shutdownRequested = true;
    }
}
