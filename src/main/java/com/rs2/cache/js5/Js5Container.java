package com.rs2.cache.js5;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;

final class Js5Container {
    private Js5Container() {
    }

    static byte[] decode(byte[] container) throws IOException {
        if (container == null || container.length < 5) {
            throw new EOFException("Truncated JS5 container");
        }
        ByteBuffer header = ByteBuffer.wrap(container);
        int compression = header.get() & 0xFF;
        int compressedLength = header.getInt();
        if (compressedLength < 0) {
            throw new IOException("Negative JS5 compressed length");
        }

        if (compression == 0) {
            if (5 + compressedLength > container.length) {
                throw new EOFException("Truncated uncompressed JS5 container");
            }
            byte[] output = new byte[compressedLength];
            System.arraycopy(container, 5, output, 0, compressedLength);
            return output;
        }

        if (container.length < 9 || 9 + compressedLength > container.length) {
            throw new EOFException("Truncated compressed JS5 container");
        }
        int uncompressedLength = header.getInt();
        byte[] compressed = new byte[compressedLength];
        System.arraycopy(container, 9, compressed, 0, compressedLength);

        InputStream input;
        if (compression == 1) {
            input = new BZip2CompressorInputStream(new ByteArrayInputStream(addBzipHeader(compressed)));
        } else if (compression == 2) {
            input = new GZIPInputStream(new ByteArrayInputStream(compressed));
        } else {
            throw new IOException("Unsupported JS5 compression type: " + compression);
        }

        byte[] output = readFully(input, uncompressedLength);
        if (output.length != uncompressedLength) {
            throw new IOException("JS5 length mismatch: expected " + uncompressedLength
                    + ", got " + output.length);
        }
        return output;
    }

    private static byte[] addBzipHeader(byte[] compressed) {
        if (compressed.length >= 3
                && compressed[0] == 'B' && compressed[1] == 'Z' && compressed[2] == 'h') {
            return compressed;
        }
        byte[] withHeader = new byte[compressed.length + 4];
        withHeader[0] = 'B';
        withHeader[1] = 'Z';
        withHeader[2] = 'h';
        withHeader[3] = '1';
        System.arraycopy(compressed, 0, withHeader, 4, compressed.length);
        return withHeader;
    }

    private static byte[] readFully(InputStream input, int expectedLength) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(expectedLength);
        byte[] buffer = new byte[8192];
        int read;
        try {
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
        } finally {
            input.close();
        }
        return output.toByteArray();
    }
}
