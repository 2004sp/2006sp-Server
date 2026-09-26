package com.rs2.cache.js5;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/** Loads revision 443 terrain and location groups for server collision maps. */
public final class WorldMaps {
    private static Square[] squares;

    private WorldMaps() {
    }

    public static synchronized Square[] load() throws IOException {
        if (squares != null) return squares;
        Map<Integer, Square> loaded = new LinkedHashMap<Integer, Square>();
        try (Js5CacheStore store = new Js5CacheStore(new File("cache"))) {
            Js5ReferenceTable table = store.readReferenceTable(5);
            for (int x = 0; x < 256; x++) {
                for (int y = 0; y < 256; y++) {
                    if (table.getGroupId("m" + x + "_" + y) < 0) continue;
                    byte[] terrain = MapGroups.readTerrain(store, table, x, y);
                    byte[] locations = MapGroups.readLocations(store, table, x, y);
                    if (terrain == null || locations == null) {
                        throw new IOException("Missing revision 443 map square " + x + "_" + y);
                    }
                    int region = x << 8 | y;
                    loaded.put(region, new Square(region, terrain, locations,
                            true, true, null));
                }
            }
        }
        squares = loaded.values().toArray(new Square[loaded.size()]);
        return squares;
    }

    public static final class Square {
        public final int regionId;
        public final byte[] terrain;
        public final byte[] locations;
        public final boolean terrainFrom443;
        public final boolean locationsFrom443;
        public final String locationIssue;

        private Square(int regionId, byte[] terrain, byte[] locations,
                       boolean terrainFrom443, boolean locationsFrom443,
                       String locationIssue) {
            this.regionId = regionId;
            this.terrain = terrain;
            this.locations = locations;
            this.terrainFrom443 = terrainFrom443;
            this.locationsFrom443 = locationsFrom443;
            this.locationIssue = locationIssue;
        }
    }
}
