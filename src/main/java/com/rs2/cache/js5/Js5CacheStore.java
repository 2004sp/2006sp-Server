package com.rs2.cache.js5;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/** Reader for the JS5 dat2/idx cache format used by revision 443. */
public final class Js5CacheStore implements Closeable {
    private static final int SECTOR_SIZE = 520;

    private final File directory;
    private final RandomAccessFile dataFile;
    private final Map<Integer, RandomAccessFile> indexFiles = new HashMap<Integer, RandomAccessFile>();

    public Js5CacheStore(File directory) throws IOException {
        this.directory = directory;
        File dat2 = new File(directory, "main_file_cache.dat2");
        if (!dat2.isFile()) {
            throw new FileNotFoundException("Missing JS5 data file: " + dat2.getAbsolutePath());
        }
        this.dataFile = new RandomAccessFile(dat2, "r");
    }

    public byte[] readGroup(int indexId, int groupId) throws IOException {
        RandomAccessFile indexFile = openIndex(indexId);
        long entryOffset = (long) groupId * 6L;
        if (groupId < 0 || entryOffset + 6L > indexFile.length()) {
            throw new IOException("Missing group " + indexId + ":" + groupId);
        }

        indexFile.seek(entryOffset);
        int length = readMedium(indexFile);
        int sector = readMedium(indexFile);
        if (length < 0 || sector <= 0) {
            throw new IOException("Invalid index entry for " + indexId + ":" + groupId);
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream(length);
        int chunk = 0;
        while (output.size() < length) {
            if (sector <= 0 || (long) sector * SECTOR_SIZE >= dataFile.length()) {
                throw new IOException("Invalid sector " + sector + " for " + indexId + ":" + groupId);
            }
            dataFile.seek((long) sector * SECTOR_SIZE);
            int nextSector = readSector(indexId, groupId, chunk, length - output.size(), output);
            sector = nextSector;
            chunk++;
        }
        return output.toByteArray();
    }

    private int readSector(int indexId, int groupId, int chunk, int remaining,
                           ByteArrayOutputStream output) throws IOException {
        int storedGroup;
        int storedChunk;
        int nextSector;
        int storedIndex;
        int payloadSize;

        if (groupId > 0xFFFF) {
            storedGroup = dataFile.readInt();
            storedChunk = dataFile.readUnsignedShort();
            nextSector = readMedium(dataFile);
            storedIndex = dataFile.readUnsignedByte();
            payloadSize = 510;
        } else {
            storedGroup = dataFile.readUnsignedShort();
            storedChunk = dataFile.readUnsignedShort();
            nextSector = readMedium(dataFile);
            storedIndex = dataFile.readUnsignedByte();
            payloadSize = 512;
        }

        if (storedGroup != groupId || storedChunk != chunk || storedIndex != indexId) {
            throw new IOException("JS5 sector header mismatch for " + indexId + ":" + groupId);
        }

        int bytesToRead = Math.min(payloadSize, remaining);
        byte[] block = new byte[bytesToRead];
        dataFile.readFully(block);
        output.write(block);
        return nextSector;
    }

    public byte[] readContainer(int indexId, int groupId) throws IOException {
        return Js5Container.decode(readGroup(indexId, groupId));
    }

    public Js5ReferenceTable readReferenceTable(int indexId) throws IOException {
        return Js5ReferenceTable.decode(readContainer(255, indexId));
    }

    public Map<Integer, byte[]> readFiles(int indexId, int groupId) throws IOException {
        return readFiles(indexId, groupId, null);
    }

    public Map<Integer, byte[]> readFiles(int indexId, int groupId, int[] xteaKey)
            throws IOException {
        Js5ReferenceTable table = readReferenceTable(indexId);
        int[] fileIds = table.getFileIds(groupId);
        if (fileIds == null) {
            throw new IOException("Group " + indexId + ":" + groupId + " is not in the reference table");
        }
        // Disk groups include a two-byte version trailer. XTEA covers only the
        // JS5 container, so remove the trailer before decrypting its last block.
        byte[] raw = readGroup(indexId, groupId);
        if (raw.length < 5) throw new IOException("Truncated JS5 group " + indexId + ":" + groupId);
        int compressedLength = (raw[1] & 255) << 24 | (raw[2] & 255) << 16
                | (raw[3] & 255) << 8 | raw[4] & 255;
        long containerLength = ((raw[0] & 255) == 0 ? 5L : 9L) + compressedLength;
        if (compressedLength < 0 || containerLength > raw.length) {
            throw new IOException("Invalid JS5 container " + indexId + ":" + groupId);
        }
        byte[] container = new byte[(int) containerLength];
        System.arraycopy(raw, 0, container, 0, container.length);
        if (xteaKey != null) {
            if (xteaKey.length != 4) throw new IllegalArgumentException("XTEA requires four words");
            container = decryptXtea(container, xteaKey);
        }
        byte[] group = Js5Container.decode(container);
        return Js5Group.unpack(group, fileIds);
    }

    private static byte[] decryptXtea(byte[] container, int[] key) {
        byte[] decoded = container.clone();
        for (int offset = 5; offset + 8 <= decoded.length; offset += 8) {
            int left = readInt(decoded, offset);
            int right = readInt(decoded, offset + 4);
            int sum = 0xC6EF3720;
            for (int round = 0; round < 32; round++) {
                right -= (((left << 4) ^ (left >>> 5)) + left)
                        ^ (sum + key[(sum >>> 11) & 3]);
                sum -= 0x9E3779B9;
                left -= (((right << 4) ^ (right >>> 5)) + right)
                        ^ (sum + key[sum & 3]);
            }
            writeInt(decoded, offset, left);
            writeInt(decoded, offset + 4, right);
        }
        return decoded;
    }

    private static int readInt(byte[] data, int offset) {
        return (data[offset] & 255) << 24 | (data[offset + 1] & 255) << 16
                | (data[offset + 2] & 255) << 8 | data[offset + 3] & 255;
    }

    private static void writeInt(byte[] data, int offset, int value) {
        data[offset] = (byte) (value >>> 24);
        data[offset + 1] = (byte) (value >>> 16);
        data[offset + 2] = (byte) (value >>> 8);
        data[offset + 3] = (byte) value;
    }

    public byte[] readFile(int indexId, String groupName, String fileName)
            throws IOException {
        Js5ReferenceTable table = readReferenceTable(indexId);
        int groupId = table.getGroupId(groupName);
        if (groupId < 0) {
            throw new IOException("Missing JS5 group " + indexId + ":" + groupName);
        }
        int fileId = table.getFileId(groupId, fileName);
        byte[] file = fileId < 0 ? null : readFiles(indexId, groupId).get(fileId);
        if (file == null) {
            throw new IOException("Missing JS5 file " + indexId + ":" + groupName
                    + ":" + fileName);
        }
        return file;
    }

    private RandomAccessFile openIndex(int indexId) throws IOException {
        RandomAccessFile existing = indexFiles.get(indexId);
        if (existing != null) {
            return existing;
        }
        File file = new File(directory, "main_file_cache.idx" + indexId);
        if (!file.isFile()) {
            throw new FileNotFoundException("Missing JS5 index: " + file.getAbsolutePath());
        }
        RandomAccessFile opened = new RandomAccessFile(file, "r");
        indexFiles.put(indexId, opened);
        return opened;
    }

    static int readMedium(RandomAccessFile file) throws IOException {
        return (file.readUnsignedByte() << 16)
                | (file.readUnsignedByte() << 8)
                | file.readUnsignedByte();
    }

    @Override
    public void close() throws IOException {
        dataFile.close();
        for (RandomAccessFile file : indexFiles.values()) {
            file.close();
        }
        indexFiles.clear();
    }
}
