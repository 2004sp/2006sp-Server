package com.rs2.cache.js5;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/** Bounds-checked reader for NUL-terminated revision 443 config files. */
public final class ConfigReader {
    private final byte[] data;
    private int position;

    public ConfigReader(byte[] data) {
        this.data = data;
    }

    public int position() {
        return position;
    }

    public int length() {
        return data.length;
    }

    public int readUnsignedByte() throws IOException {
        require(1);
        return data[position++] & 255;
    }

    public int readByte() throws IOException {
        require(1);
        return data[position++];
    }

    public int readUnsignedShort() throws IOException {
        require(2);
        int value = (data[position] & 255) << 8 | data[position + 1] & 255;
        position += 2;
        return value;
    }

    public int readInt() throws IOException {
        require(4);
        int value = (data[position] & 255) << 24 | (data[position + 1] & 255) << 16
                | (data[position + 2] & 255) << 8 | data[position + 3] & 255;
        position += 4;
        return value;
    }

    public String readString() throws IOException {
        int start = position;
        while (position < data.length && data[position] != 0) position++;
        if (position == data.length) throw new EOFException("Unterminated 443 config string");
        String value = new String(data, start, position - start, StandardCharsets.ISO_8859_1);
        position++;
        return value;
    }

    public void skip(int count) throws IOException {
        require(count);
        position += count;
    }

    private void require(int count) throws IOException {
        if (count < 0 || position + count > data.length) {
            throw new EOFException("Truncated 443 config at byte " + position);
        }
    }
}
