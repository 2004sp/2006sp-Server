package com.rs2.net;

import com.rs2.cache.js5.Js5CacheStore;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketWriter;

import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.zip.CRC32;

/** JS5 update protocol used by the revision 443 client. */
final class Js5Protocol {
    private static final int ARCHIVE_COUNT = 14;
    private static final Map<Player, Byte> XOR_KEYS = new WeakHashMap<Player, Byte>();
    private static Js5CacheStore cacheStore;
    private static byte[] masterIndex;

    private Js5Protocol() {
    }

    static boolean process(Player player, ByteBuffer input) {
        while (((Buffer) input).remaining() >= 4) {
            int opcode = input.get() & 0xFF;
            int key = ((input.get() & 0xFF) << 16)
                    | ((input.get() & 0xFF) << 8)
                    | (input.get() & 0xFF);

            try {
                switch (opcode) {
                    case 0:
                    case 1:
                        sendGroup(player, key >> 16 & 0xFF, key & 0xFFFF);
                        break;
                    case 2:
                    case 3:
                        // Logged-in/logged-out state hint. It does not alter cache data.
                        break;
                    case 4:
                        XOR_KEYS.put(player, (byte) (key >> 16));
                        break;
                    default:
                        System.err.println("Unknown 443 JS5 opcode: " + opcode);
                        player.disconnect();
                        return false;
                }
            } catch (IOException exception) {
                System.err.println("443 JS5 request failed: " + exception.getMessage());
                player.disconnect();
                return false;
            }
        }

        if (((Buffer) input).hasRemaining()) {
            input.compact();
            return true;
        }
        return false;
    }

    private static void sendGroup(Player player, int archive, int group) throws IOException {
        byte[] raw = archive == 255 && group == 255
                ? getMasterIndex()
                : getCacheStore().readGroup(archive, group);
        if (raw.length < 5) {
            throw new IOException("Truncated cache group " + archive + ":" + group);
        }

        int compression = raw[0] & 0xFF;
        int compressedLength = readInt(raw, 1);
        int containerLength = (compression == 0 ? 5 : 9) + compressedLength;
        if (compressedLength < 0 || containerLength > raw.length) {
            throw new IOException("Invalid cache container " + archive + ":" + group);
        }

        int bodyLength = containerLength - 5;
        byte[] response = frameResponse(archive, group, compression,
                compressedLength, raw, 5, bodyLength);
        Byte xorKey = XOR_KEYS.get(player);
        if (xorKey != null && xorKey.byteValue() != 0) {
            byte key = xorKey.byteValue();
            for (int i = 0; i < response.length; i++) {
                response[i] ^= key;
            }
        }

        PacketWriter writer = PacketBuffer.allocateWriter(response.length);
        writer.writeBytes(response, response.length);
        player.writePacketBuffer(writer.getBuffer());
    }

    private static byte[] frameResponse(int archive, int group, int compression,
                                        int compressedLength, byte[] bodySource,
                                        int bodyOffset, int bodyLength) {
        int remainingAfterFirstBlock = Math.max(0, bodyLength - 504);
        int continuationMarkers = (remainingAfterFirstBlock + 510) / 511;
        ByteBuffer output = ByteBuffer.allocate(8 + bodyLength + continuationMarkers);
        output.put((byte) archive);
        output.putShort((short) group);
        output.put((byte) compression);
        output.putInt(compressedLength);

        int copied = 0;
        int blockPosition = 8;
        while (copied < bodyLength) {
            int count = Math.min(512 - blockPosition, bodyLength - copied);
            output.put(bodySource, bodyOffset + copied, count);
            copied += count;
            blockPosition += count;
            if (blockPosition == 512 && copied < bodyLength) {
                output.put((byte) 0xFF);
                blockPosition = 1;
            }
        }
        return output.array();
    }

    private static int readInt(byte[] data, int offset) {
        return (data[offset] & 0xFF) << 24
                | (data[offset + 1] & 0xFF) << 16
                | (data[offset + 2] & 0xFF) << 8
                | data[offset + 3] & 0xFF;
    }

    private static synchronized Js5CacheStore getCacheStore() throws IOException {
        if (cacheStore == null) {
            cacheStore = new Js5CacheStore(new File("cache"));
        }
        return cacheStore;
    }

    private static synchronized byte[] getMasterIndex() throws IOException {
        if (masterIndex != null) {
            return masterIndex;
        }
        ByteBuffer payload = ByteBuffer.allocate(ARCHIVE_COUNT * 4);
        Js5CacheStore store = getCacheStore();
        for (int archive = 0; archive < ARCHIVE_COUNT; archive++) {
            byte[] referenceTable = store.readGroup(255, archive);
            CRC32 crc = new CRC32();
            // idx255 entries in this cache are exact containers, with no
            // trailing group-version bytes.
            crc.update(referenceTable);
            payload.putInt((int) crc.getValue());
        }

        byte[] payloadBytes = payload.array();
        ByteBuffer container = ByteBuffer.allocate(5 + payloadBytes.length);
        container.put((byte) 0);
        container.putInt(payloadBytes.length);
        container.put(payloadBytes);
        masterIndex = container.array();
        return masterIndex;
    }

    static int getArchiveCrc(int archive) throws IOException {
        if (archive < 0 || archive >= ARCHIVE_COUNT) {
            throw new IllegalArgumentException("Invalid cache archive " + archive);
        }
        return readInt(getMasterIndex(), 5 + archive * 4);
    }
}
