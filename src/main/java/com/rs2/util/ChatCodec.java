package com.rs2.util;

import com.rs2.cache.js5.Js5CacheStore;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/** Revision 443 wordpack: unsigned smart character count and JS5 Huffman bits. */
public final class ChatCodec {
    private static final Charset CHARSET = Charset.forName("windows-1252");
    private static volatile ChatCodec instance;
    private final int[] codes = new int[256];
    private final int[] lengths = new int[256];
    private final int[] left = new int[8192];
    private final int[] right = new int[8192];
    private final int[] symbol = new int[8192];
    private int nodes = 1;

    private ChatCodec(byte[] table) {
        if (table.length != 256) throw new IllegalArgumentException("Invalid 443 Huffman table");
        int[] counts = new int[33];
        for (int i = 0; i < 256; i++) {
            lengths[i] = table[i] & 255;
            if (lengths[i] > 32) throw new IllegalArgumentException("Invalid Huffman code length");
            if (lengths[i] != 0) counts[lengths[i]]++;
        }
        int[] next = new int[33];
        int code = 0;
        for (int bits = 1; bits <= 32; bits++) {
            code = (code + counts[bits - 1]) << 1;
            next[bits] = code;
        }
        Arrays.fill(symbol, -1);
        for (int i = 0; i < 256; i++) {
            int bits = lengths[i];
            if (bits == 0) continue;
            codes[i] = next[bits]++;
            int node = 0;
            for (int bit = bits - 1; bit >= 0; bit--) {
                boolean one = ((codes[i] >>> bit) & 1) != 0;
                int child = one ? right[node] : left[node];
                if (child == 0) child = ++nodes;
                if (child >= symbol.length) throw new IllegalArgumentException("Invalid Huffman tree");
                if (one) right[node] = child; else left[node] = child;
                node = child;
            }
            symbol[node] = i;
        }
    }

    public static ChatCodec get() {
        ChatCodec result = instance;
        if (result != null) return result;
        synchronized (ChatCodec.class) {
            if (instance == null) {
                try (Js5CacheStore store = new Js5CacheStore(new File("cache"))) {
                    instance = new ChatCodec(store.readFile(10, "huffman", ""));
                } catch (IOException e) {
                    throw new IllegalStateException("Cannot load revision 443 wordpack", e);
                }
            }
            return instance;
        }
    }

    public String decode(byte[] payload) {
        if (payload.length == 0) throw new IllegalArgumentException("Missing chat length");
        int count = payload[0] & 255;
        int offset = 1;
        if (count >= 128) {
            if (payload.length < 2) throw new IllegalArgumentException("Truncated chat length");
            count = ((count << 8) | (payload[1] & 255)) - 32768;
            offset = 2;
        }
        if (count > 32767) throw new IllegalArgumentException("Invalid chat length");
        byte[] output = new byte[count];
        int node = 0, written = 0;
        for (int i = offset; i < payload.length && written < count; i++) {
            for (int bit = 7; bit >= 0 && written < count; bit--) {
                node = ((payload[i] >>> bit) & 1) == 0 ? left[node] : right[node];
                if (node == 0) throw new IllegalArgumentException("Invalid chat codeword");
                if (symbol[node] >= 0) {
                    output[written++] = (byte) symbol[node];
                    node = 0;
                }
            }
        }
        if (written != count) throw new IllegalArgumentException("Truncated chat payload");
        return new String(output, CHARSET);
    }

    public byte[] encode(String text) {
        byte[] input = text.getBytes(CHARSET);
        if (input.length > 32767) throw new IllegalArgumentException("Chat too long");
        byte[] output = new byte[2 + input.length * 4];
        int offset = input.length < 128 ? 1 : 2;
        if (offset == 1) output[0] = (byte) input.length;
        else {
            output[0] = (byte) ((input.length + 32768) >>> 8);
            output[1] = (byte) (input.length + 32768);
        }
        int position = offset * 8;
        for (byte value : input) {
            int index = value & 255;
            int bits = lengths[index];
            if (bits == 0) throw new IllegalArgumentException("No Huffman code for " + index);
            for (int bit = bits - 1; bit >= 0; bit--) {
                if (((codes[index] >>> bit) & 1) != 0)
                    output[position >>> 3] |= 1 << (7 - (position & 7));
                position++;
            }
        }
        return Arrays.copyOf(output, (position + 7) >>> 3);
    }
}
