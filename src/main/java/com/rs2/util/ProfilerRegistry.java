package com.rs2.util;

import com.rs2.util.ProfilerTimer;
import java.util.HashMap;
import java.util.Map;

public final class ProfilerRegistry {
    private static Map timers = new HashMap();

    public static ProfilerTimer getTimer(String text2) {
        ProfilerTimer profilerTimer = (ProfilerTimer)timers.get(text2);
        if (profilerTimer == null) {
            profilerTimer = new ProfilerTimer();
            timers.put(text2, profilerTimer);
        }
        return profilerTimer;
    }

    public static void resetAll() {
        for (Object entryObject : timers.entrySet()) {
            Map.Entry entry = (Map.Entry)entryObject;
            ((ProfilerTimer)entry.getValue()).reset();
        }
    }

}
