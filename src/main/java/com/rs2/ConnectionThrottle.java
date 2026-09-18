package com.rs2;

import com.rs2.ConnectionThrottleSettings;
import java.util.concurrent.ConcurrentHashMap;

public final class ConnectionThrottle {
    private static ConcurrentHashMap connectionCountsByHost = new ConcurrentHashMap();

    public static boolean tryAcquireConnectionSlot(String slot) {
        if (!ConnectionThrottleSettings.connectionsEnabled) {
            return false;
        }
        Integer integer = (Integer)connectionCountsByHost.putIfAbsent(slot, 1);
        if (integer != null && integer == 3) {
            return false;
        }
        integer = integer == null ? 0 : integer;
        connectionCountsByHost.replace(slot, integer + 1);
        return true;
    }

    public static void releaseConnectionSlot(String slot) {
        Integer integer = (Integer)connectionCountsByHost.get(slot);
        if (integer == null) {
            return;
        }
        if (integer == 1) {
            connectionCountsByHost.remove(slot);
            return;
        }
        if (integer != null) {
            connectionCountsByHost.replace(slot, integer - 1);
        }
    }
}

