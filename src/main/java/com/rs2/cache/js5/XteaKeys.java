package com.rs2.cache.js5;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Validated location-map keys for the bundled revision 443 cache. */
public final class XteaKeys {
    private static final Pattern OBJECT = Pattern.compile("\\{([^{}]*)\\}");
    private static final Pattern MAP_SQUARE = Pattern.compile("\"mapsquare\"\\s*:\\s*(\\d+)");
    private static final Pattern KEY = Pattern.compile("\"key\"\\s*:\\s*\\[\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\]");
    private static final Map<Integer, int[]> KEYS = load(new File("cache/xteas.json"));

    private XteaKeys() {
    }

    public static int[] forMapSquare(int x, int y) {
        int[] key = KEYS.get((x << 8) | y);
        return key == null ? new int[4] : key.clone();
    }

    public static int count() {
        return KEYS.size();
    }

    private static Map<Integer, int[]> load(File file) {
        Map<Integer, int[]> keys = new HashMap<Integer, int[]>();
        try {
            String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8).trim();
            if (!json.startsWith("[") || !json.endsWith("]")) {
                throw new IOException("Invalid XTEA JSON array");
            }
            Matcher objects = OBJECT.matcher(json);
            while (objects.find()) {
                String object = objects.group(1);
                Matcher square = MAP_SQUARE.matcher(object);
                Matcher keyValues = KEY.matcher(object);
                if (!square.find() || !keyValues.find()) {
                    throw new IOException("Invalid XTEA JSON entry: " + object);
                }
                int mapSquare = Integer.parseInt(square.group(1));
                if (mapSquare < 0 || mapSquare > 65535) {
                    throw new IOException("Invalid XTEA map square " + mapSquare);
                }
                int[] key = new int[4];
                for (int i = 0; i < key.length; i++) {
                    key[i] = Integer.parseInt(keyValues.group(i + 1));
                }
                if (keys.put(mapSquare, key) != null) {
                    throw new IOException("Duplicate XTEA map square " + mapSquare);
                }
            }
            if (keys.isEmpty()) {
                throw new IOException("No XTEA keys in JSON array");
            }
        } catch (IOException | NumberFormatException exception) {
            throw new IllegalStateException("Unable to load revision 443 XTEA keys from "
                    + file.getPath(), exception);
        }
        return keys;
    }
}
