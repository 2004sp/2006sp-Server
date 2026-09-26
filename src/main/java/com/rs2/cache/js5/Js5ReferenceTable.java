package com.rs2.cache.js5;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

/** Minimal protocol-5/6 JS5 reference table parser for the 443 cache. */
public final class Js5ReferenceTable {
    private final int protocol;
    private final int version;
    private final int[] groupIds;
    private final Map<Integer, int[]> fileIds;
    private final Map<Integer, Integer> namedGroups;
    private final Map<Integer, Map<Integer, Integer>> namedFiles;

    private Js5ReferenceTable(int protocol, int version, int[] groupIds,
                              Map<Integer, int[]> fileIds,
                              Map<Integer, Integer> namedGroups,
                              Map<Integer, Map<Integer, Integer>> namedFiles) {
        this.protocol = protocol;
        this.version = version;
        this.groupIds = groupIds;
        this.fileIds = fileIds;
        this.namedGroups = namedGroups;
        this.namedFiles = namedFiles;
    }

    public static Js5ReferenceTable decode(byte[] data) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        if (!buffer.hasRemaining()) {
            throw new IOException("Empty JS5 reference table");
        }
        int protocol = buffer.get() & 0xFF;
        if (protocol != 5 && protocol != 6) {
            throw new IOException("Unsupported JS5 reference table protocol: " + protocol);
        }
        int version = protocol >= 6 ? buffer.getInt() : 0;
        int flags = buffer.get() & 0xFF;
        boolean named = (flags & 1) != 0;

        int groupCount = buffer.getShort() & 0xFFFF;
        int[] groupIds = new int[groupCount];
        Map<Integer, Integer> namedGroups = new LinkedHashMap<Integer, Integer>();
        Map<Integer, Map<Integer, Integer>> namedFiles =
                new LinkedHashMap<Integer, Map<Integer, Integer>>();
        int groupId = 0;
        for (int i = 0; i < groupCount; i++) {
            groupId += buffer.getShort() & 0xFFFF;
            groupIds[i] = groupId;
        }

        if (named) {
            for (int i = 0; i < groupCount; i++) {
                namedGroups.put(buffer.getInt(), groupIds[i]);
            }
        }
        skipInts(buffer, groupCount); // group CRCs
        skipInts(buffer, groupCount); // group versions

        int[] fileCounts = new int[groupCount];
        for (int i = 0; i < groupCount; i++) {
            fileCounts[i] = buffer.getShort() & 0xFFFF;
        }

        Map<Integer, int[]> fileIds = new LinkedHashMap<Integer, int[]>();
        for (int i = 0; i < groupCount; i++) {
            int[] ids = new int[fileCounts[i]];
            int fileId = 0;
            for (int j = 0; j < ids.length; j++) {
                fileId += buffer.getShort() & 0xFFFF;
                ids[j] = fileId;
            }
            fileIds.put(groupIds[i], ids);
        }

        if (named) {
            for (int i = 0; i < groupCount; i++) {
                int[] ids = fileIds.get(groupIds[i]);
                Map<Integer, Integer> names = new LinkedHashMap<Integer, Integer>();
                for (int j = 0; j < ids.length; j++) {
                    names.put(buffer.getInt(), ids[j]);
                }
                namedFiles.put(groupIds[i], names);
            }
        }
        return new Js5ReferenceTable(protocol, version, groupIds, fileIds,
                namedGroups, namedFiles);
    }

    private static void skipInts(ByteBuffer buffer, int count) throws IOException {
        if (count < 0 || buffer.remaining() < count * 4) {
            throw new IOException("Truncated JS5 reference table");
        }
        buffer.position(buffer.position() + count * 4);
    }

    public int getProtocol() {
        return protocol;
    }

    public int getVersion() {
        return version;
    }

    public int[] getGroupIds() {
        return groupIds.clone();
    }

    public int getGroupCount() {
        return groupIds.length;
    }

    public int[] getFileIds(int groupId) {
        int[] ids = fileIds.get(groupId);
        return ids == null ? null : ids.clone();
    }

    public int getFileCount(int groupId) {
        int[] ids = fileIds.get(groupId);
        return ids == null ? 0 : ids.length;
    }

    public int getGroupId(String name) {
        Integer id = namedGroups.get(nameHash(name));
        return id == null ? -1 : id;
    }

    public int getFileId(int groupId, String name) {
        Map<Integer, Integer> names = namedFiles.get(groupId);
        Integer id = names == null ? null : names.get(nameHash(name));
        return id == null ? -1 : id;
    }

    private static int nameHash(String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            int value = name.charAt(i);
            if (value > 255) {
                throw new IllegalArgumentException("JS5 names must be Latin-1");
            }
            if (value >= 'A' && value <= 'Z'
                    || value >= 192 && value <= 222 && value != 215) {
                value += 32;
            }
            hash = hash * 31 + value;
        }
        return hash;
    }
}
