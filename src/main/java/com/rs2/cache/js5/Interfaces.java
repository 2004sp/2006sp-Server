package com.rs2.cache.js5;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Revision 443 interface groups from JS5 index 3. Identities are group:child pairs. */
public final class Interfaces {
    private static Map<Integer, Map<Integer, Component>> groups = Collections.emptyMap();

    private Interfaces() {
    }

    public static synchronized int load() throws IOException {
        try (Js5CacheStore store = new Js5CacheStore(new File("cache"))) {
            return load(store);
        }
    }

    static synchronized int load(Js5CacheStore store) throws IOException {
        Map<Integer, Map<Integer, Component>> loaded =
                new LinkedHashMap<Integer, Map<Integer, Component>>();
        int count = 0;
        Js5ReferenceTable table = store.readReferenceTable(3);
        for (int groupId : table.getGroupIds()) {
            Map<Integer, Component> components = new LinkedHashMap<Integer, Component>();
            for (Map.Entry<Integer, byte[]> file : store.readFiles(3, groupId).entrySet()) {
                Component component = decode(groupId, file.getKey(), file.getValue());
                components.put(file.getKey(), component);
                count++;
            }
            loaded.put(groupId, Collections.unmodifiableMap(components));
        }
        groups = Collections.unmodifiableMap(loaded);
        return count;
    }

    public static Component forId(int groupId, int childId) {
        Map<Integer, Component> group = groups.get(groupId);
        return group == null ? null : group.get(childId);
    }

    public static Component forPackedId(int packedId) {
        return forId(packedId >>> 16, packedId & 0xffff);
    }

    public static Map<Integer, Component> group(int groupId) {
        Map<Integer, Component> group = groups.get(groupId);
        return group == null ? Collections.<Integer, Component>emptyMap() : group;
    }

    public static int groupCount() {
        return groups.size();
    }

    private static Component decode(int groupId, int childId, byte[] data) throws IOException {
        ConfigReader reader = new ConfigReader(data);
        int marker = reader.readUnsignedByte();
        boolean modern = marker == 255;
        int type = modern ? reader.readUnsignedByte() : marker;
        int actionType = modern ? -1 : reader.readUnsignedByte();
        int contentType = reader.readUnsignedShort();
        int x = reader.readUnsignedShort();
        int y = reader.readUnsignedShort();
        if (x > 32767) x -= 65536;
        if (y > 32767) y -= 65536;
        int width = reader.readUnsignedShort();
        int height = reader.readUnsignedShort();
        if (modern) reader.skip(4); // width/height and x/y alignment modes
        else reader.skip(1); // opacity
        int parent = reader.readUnsignedShort();
        int parentId = parent == 65535 ? -1 : groupId << 16 | parent;
        if (modern) reader.skip(1); // hidden flag
        else reader.skip(2); // hover target
        return new Component(groupId, childId, modern, type, actionType,
                contentType, x, y, width, height, parentId, data);
    }

    public static final class Component {
        public final int groupId;
        public final int childId;
        public final int packedId;
        public final boolean modern;
        public final int type;
        public final int actionType;
        public final int contentType;
        public final int x;
        public final int y;
        public final int width;
        public final int height;
        public final int parentId;
        private final byte[] data;

        private Component(int groupId, int childId, boolean modern, int type, int actionType,
                          int contentType, int x, int y, int width, int height,
                          int parentId, byte[] data) {
            this.groupId = groupId;
            this.childId = childId;
            this.packedId = groupId << 16 | childId;
            this.modern = modern;
            this.type = type;
            this.actionType = actionType;
            this.contentType = contentType;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.parentId = parentId;
            this.data = data.clone();
        }

        public byte[] getData() {
            return data.clone();
        }
    }
}
