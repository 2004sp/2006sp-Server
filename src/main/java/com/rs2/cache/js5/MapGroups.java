package com.rs2.cache.js5;

import java.io.IOException;
import java.util.Map;

/** Named terrain and XTEA-encrypted location groups in JS5 archive 5. */
public final class MapGroups {
    private MapGroups() {
    }

    public static byte[] readTerrain(Js5CacheStore store, Js5ReferenceTable table,
                                     int x, int y) throws IOException {
        return read(store, table, "m" + x + "_" + y, null);
    }

    public static byte[] readLocations(Js5CacheStore store, Js5ReferenceTable table,
                                       int x, int y) throws IOException {
        int[] key = XteaKeys.forMapSquare(x, y);
        if (key[0] == 0 && key[1] == 0 && key[2] == 0 && key[3] == 0) {
            key = null;
        }
        return read(store, table, "l" + x + "_" + y, key);
    }

    private static byte[] read(Js5CacheStore store, Js5ReferenceTable table,
                               String name, int[] key) throws IOException {
        int groupId = table.getGroupId(name);
        if (groupId < 0) return null;
        try {
            Map<Integer, byte[]> files = store.readFiles(5, groupId, key);
            if (files.size() != 1) {
                throw new IOException("443 map group " + name + " has " + files.size() + " files");
            }
            return files.values().iterator().next();
        } catch (IOException exception) {
            throw new IOException("Unable to decode 443 map group " + name, exception);
        }
    }
}
