package com.rs2.cache.js5;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/** Access to the split configuration groups in the bundled revision 443 cache. */
public final class Definitions {
    private Definitions() {
    }

    public static Map<Integer, byte[]> readGroup(int group) throws IOException {
        try (Js5CacheStore store = new Js5CacheStore(new File("cache"))) {
            return store.readFiles(2, group);
        }
    }
}
