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

    public static String formatSnapshot() {
        StringBuilder result = new StringBuilder();
        for (Object entryObject : timers.entrySet()) {
            Map.Entry entry = (Map.Entry)entryObject;
            long millis = ((ProfilerTimer)entry.getValue()).getAccumulatedMillis();
            if (millis <= 0L) continue;
            if (result.length() > 0) result.append(", ");
            result.append(entry.getKey()).append('=').append(millis).append("ms");
        }
        return result.toString();
    }

}
