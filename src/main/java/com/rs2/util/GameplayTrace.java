package com.rs2.util;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class GameplayTrace {
    private static final boolean ENABLED = Boolean.getBoolean("prs.traceGameplay");
    private static final String FILTER = System.getProperty("prs.traceFilter", "");
    private static final Object LOCK = new Object();

    private GameplayTrace() {
    }

    public static boolean enabled() {
        return ENABLED;
    }

    public static void log(String message) {
        if (!ENABLED) {
            return;
        }
        if (!FILTER.isEmpty() && !message.contains(FILTER)) {
            return;
        }
        synchronized (LOCK) {
            try {
                File directory = new File("qa-output");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                PrintWriter writer = new PrintWriter(new FileWriter(new File(directory, "gameplay-trace.log"), true));
                writer.println(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + " " + message);
                writer.close();
            }
            catch (Exception exception) {
                System.err.println("[gameplay-trace] " + message);
                exception.printStackTrace();
            }
        }
    }

    public static void logException(String context, Throwable throwable) {
        if (!ENABLED) {
            return;
        }
        log(context + " exception=" + throwable);
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int limit = Math.min(stackTrace.length, 12);
        for (int index = 0; index < limit; ++index) {
            log("  at " + stackTrace[index]);
        }
    }

    public static String describe(Entity entity) {
        if (entity == null) {
            return "null";
        }
        String type = entity.isPlayer() ? "player" : (entity.isNpc() ? "npc" : "entity");
        String name = "";
        try {
            if (entity.isPlayer()) {
                name = ((Player)entity).getUsername();
            } else if (entity.isNpc()) {
                Npc npc = (Npc)entity;
                name = npc.getNpcId() + ":" + npc.getDefinition().getName();
            }
        }
        catch (Exception exception) {
            name = "?";
        }
        return type + "[" + name + " index=" + entity.getIndex() + " pos=" + position(entity.getPosition()) + " hp=" + entity.getCurrentHitpoints() + "/" + entity.getMaxHitpoints() + "]";
    }

    public static String position(Position position) {
        if (position == null) {
            return "null";
        }
        return position.getX() + "," + position.getY() + "," + position.getPlane();
    }
}
