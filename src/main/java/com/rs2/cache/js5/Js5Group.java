package com.rs2.cache.js5;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

final class Js5Group {
    private Js5Group() {
    }

    static Map<Integer, byte[]> unpack(byte[] group, int[] fileIds) throws IOException {
        Map<Integer, byte[]> files = new LinkedHashMap<Integer, byte[]>();
        if (fileIds.length == 0) {
            return files;
        }
        if (fileIds.length == 1) {
            files.put(fileIds[0], group);
            return files;
        }
        if (group.length == 0) {
            throw new IOException("Empty multi-file JS5 group");
        }

        int chunks = group[group.length - 1] & 0xFF;
        long tableLengthLong = (long) chunks * fileIds.length * 4;
        long tableOffsetLong = group.length - 1L - tableLengthLong;
        if (chunks == 0 || tableOffsetLong < 0) {
            throw new IOException("Invalid JS5 group chunk table");
        }
        int tableLength = (int) tableLengthLong;
        int tableOffset = (int) tableOffsetLong;

        int[] sizes = new int[fileIds.length];
        ByteBuffer table = ByteBuffer.wrap(group, tableOffset, tableLength).slice();
        for (int chunk = 0; chunk < chunks; chunk++) {
            int cumulative = 0;
            for (int file = 0; file < fileIds.length; file++) {
                long next = (long) cumulative + table.getInt();
                if (next < 0 || next > tableOffset
                        || (long) sizes[file] + next > tableOffset) {
                    throw new IOException("Invalid JS5 file size in group");
                }
                cumulative = (int) next;
                sizes[file] += cumulative;
            }
        }

        byte[][] data = new byte[fileIds.length][];
        int[] offsets = new int[fileIds.length];
        for (int file = 0; file < fileIds.length; file++) {
            if (sizes[file] < 0 || sizes[file] > tableOffset) {
                throw new IOException("Invalid JS5 file size in group");
            }
            data[file] = new byte[sizes[file]];
        }

        table.position(0);
        int sourceOffset = 0;
        for (int chunk = 0; chunk < chunks; chunk++) {
            int cumulative = 0;
            for (int file = 0; file < fileIds.length; file++) {
                long next = (long) cumulative + table.getInt();
                if (next < 0 || (long) sourceOffset + next > tableOffset
                        || (long) offsets[file] + next > data[file].length) {
                    throw new IOException("Invalid JS5 group chunk size");
                }
                cumulative = (int) next;
                System.arraycopy(group, sourceOffset, data[file], offsets[file], cumulative);
                sourceOffset += cumulative;
                offsets[file] += cumulative;
            }
        }

        if (sourceOffset != tableOffset) {
            throw new IOException("JS5 group data/table boundary mismatch");
        }
        for (int file = 0; file < fileIds.length; file++) {
            files.put(fileIds[file], data[file]);
        }
        return files;
    }
}
