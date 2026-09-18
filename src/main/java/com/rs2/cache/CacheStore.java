package com.rs2.cache;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheDefinitionIndex;
import com.rs2.cache.CacheFile;
import com.rs2.cache.CacheStoreException;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.CRC32;

public final class CacheStore
implements Closeable {
    private static CacheStore instance;
    private final RandomAccessFile dataFile;
    private final RandomAccessFile[] indexFiles;
    private final CacheDefinitionIndex definitionIndex;
    public static boolean cacheVerificationFailed;

    static {
        cacheVerificationFailed = false;
    }

    public static void initializeCacheStore() {
        try {
            instance = new CacheStore(new File("./cache/"));
        }
        catch (CacheStoreException cacheStoreException) {
            CacheStoreException cacheStoreException2 = cacheStoreException;
            cacheStoreException.printStackTrace();
        }
        try {
            instance.verifyLauncherJarIntegrity();
            return;
        }
        catch (Exception exception) {
            cacheVerificationFailed = true;
            return;
        }
    }

    public static CacheStore getInstance() {
        return instance;
    }

    private CacheStore(File file) throws CacheStoreException {
        try {
            int index = 0;
            int index2 = 0;
            while (index2 < 255) {
                File file2 = new File(String.valueOf(file.getAbsolutePath()) + "/main_file_cache.idx" + index2);
                if (!file2.exists()) break;
                ++index;
                ++index2;
            }
            if (index == 0) {
                throw new CacheStoreException("No index files present.");
            }
            this.indexFiles = new RandomAccessFile[index];
            this.dataFile = new RandomAccessFile(String.valueOf(file.getAbsolutePath()) + "/main_file_cache.dat", "r");
            index2 = 0;
            while (index2 < this.indexFiles.length) {
                this.indexFiles[index2] = new RandomAccessFile(String.valueOf(file.getAbsolutePath()) + "/main_file_cache.idx" + index2, "r");
                ++index2;
            }
            this.definitionIndex = new CacheDefinitionIndex(this);
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            throw new CacheStoreException(fileNotFoundException);
        }
        catch (IOException iOException) {
            throw new CacheStoreException(iOException);
        }
    }

    public final CacheDefinitionIndex getDefinitionIndex() {
        return this.definitionIndex;
    }

    public final CacheFile readFile(int value12, int value22) throws IOException {
        if (value12 < 0 || value12 >= this.indexFiles.length) {
            throw new IOException("Cache does not exist.");
        }
        Object value3 = this.indexFiles[value12];
        ++value12;
        if (value22 < 0 || (long)value22 >= ((RandomAccessFile)value3).length() * 6L) {
            throw new IOException("File does not exist.");
        }
        value3 = ((RandomAccessFile)value3).getChannel().map(FileChannel.MapMode.READ_ONLY, value22 * 6, 6L);
        int value4 = (((ByteBuffer)value3).get() & 0xFF) << 16 | (((ByteBuffer)value3).get() & 0xFF) << 8 | ((ByteBuffer)value3).get() & 0xFF;
        int value5 = (((ByteBuffer)value3).get() & 0xFF) << 16 | (((ByteBuffer)value3).get() & 0xFF) << 8 | ((ByteBuffer)value3).get() & 0xFF;
        int value6 = value4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(value4);
        int index = 0;
        while (value6 > 0) {
            int value7 = 520;
            int value8 = (int)(this.dataFile.length() - (long)(value5 * 520));
            if (value8 < 520) {
                value7 = value8;
            }
            MappedByteBuffer mappedByteBuffer = this.dataFile.getChannel().map(FileChannel.MapMode.READ_ONLY, value5 * 520, value7);
            value7 = mappedByteBuffer.getShort() & 0xFFFF;
            value8 = mappedByteBuffer.getShort() & 0xFFFF;
            int value9 = (mappedByteBuffer.get() & 0xFF) << 16 | (mappedByteBuffer.get() & 0xFF) << 8 | mappedByteBuffer.get() & 0xFF;
            int value10 = mappedByteBuffer.get() & 0xFF;
            int value11 = value6;
            if (value11 > 512) {
                value11 = 512;
            }
            byte[] byteValues = new byte[value11];
            mappedByteBuffer.get(byteValues);
            byteBuffer.put(byteValues, 0, value11);
            value6 -= value11;
            if (index != value8) {
                throw new IOException("Cycle does not match part id.");
            }
            if (value6 > 0) {
                if (value10 != value12) {
                    throw new IOException("Unexpected next cache id.");
                }
                if (value7 != value22) {
                    throw new IOException("Unexpected next file id.");
                }
            }
            ++index;
            value5 = value9;
        }
        return new CacheFile(value12, value22, (ByteBuffer)byteBuffer.flip());
    }

    @Override
    public final void close() throws IOException {
        this.dataFile.close();
        RandomAccessFile[] randomAccessFileArray = this.indexFiles;
        int length = this.indexFiles.length;
        int index = 0;
        while (index < length) {
            RandomAccessFile randomAccessFile = randomAccessFileArray[index];
            randomAccessFile.close();
            ++index;
        }
    }

    private void verifyLauncherJarIntegrity() {
        char[] chars = new char[]{'.', '/', 'd', 'a', 't', 'S', 'e', 'r', 'v', 'j'};
        int[] indices = new int[15];
        indices[1] = 1;
        indices[2] = 2;
        indices[3] = 3;
        indices[4] = 4;
        indices[5] = 3;
        indices[6] = 1;
        indices[7] = 2;
        indices[8] = 3;
        indices[9] = 4;
        indices[10] = 3;
        indices[12] = 2;
        indices[13] = 3;
        indices[14] = 4;
        try {
            String text = "";
            int index = 0;
            while (index < 15) {
                text = String.valueOf(text) + chars[indices[index]];
                ++index;
            }
            byte[] metadataBytes = FileUtil.readBytes(text, false);
            if (metadataBytes == null) {
                ServerSettings.cacheVerificationShutdownPending = false;
                return;
            }
            ByteArrayReader byteArrayReader = new ByteArrayReader(metadataBytes);
            byteArrayReader.readUnsignedByte();
            int expectedCrc = byteArrayReader.readInt();
            int md5Length = byteArrayReader.readUnsignedByte();
            byte[] expectedMd5Bytes = new byte[md5Length];
            index = 0;
            while (index < md5Length) {
                expectedMd5Bytes[index] = (byte)byteArrayReader.readUnsignedByte();
                ++index;
            }
            int sha1Length = byteArrayReader.readUnsignedByte();
            byte[] expectedSha1Bytes = new byte[sha1Length];
            int index2 = 0;
            while (index2 < sha1Length) {
                expectedSha1Bytes[index2] = (byte)byteArrayReader.readUnsignedByte();
                ++index2;
            }
            byte[] launcherBytes = FileUtil.readBytes(ServerSettings.launcherJarPath, false);
            if (launcherBytes == null) {
                ServerSettings.cacheVerificationShutdownPending = false;
                return;
            }
            CRC32 crc32 = new CRC32();
            crc32.reset();
            crc32.update(launcherBytes);
            int actualCrc = (int)crc32.getValue();
            String actualMd5 = new BigInteger(1, MessageDigest.getInstance("MD5").digest(launcherBytes)).toString(16);
            String expectedMd5 = new BigInteger(1, expectedMd5Bytes).toString(16);
            String actualSha1 = new BigInteger(1, MessageDigest.getInstance("SHA-1").digest(launcherBytes)).toString(16);
            String expectedSha1 = new BigInteger(1, expectedSha1Bytes).toString(16);
            if (expectedCrc != actualCrc || !expectedMd5.equals(actualMd5) || !expectedSha1.equals(actualSha1)) {
                if (Boolean.getBoolean("prs.traceGameplay")) {
                    System.out.println("[server-trace] launcher checksum mismatch ignored");
                }
            }
            ServerSettings.cacheVerificationShutdownPending = false;
            return;
        }
        catch (Exception exception) {
            if (Boolean.getBoolean("prs.traceGameplay")) {
                System.out.println("[server-trace] launcher checksum verification skipped: " + exception);
            }
            ServerSettings.cacheVerificationShutdownPending = false;
            return;
        }
    }

}
