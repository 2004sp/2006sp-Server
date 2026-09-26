package com.rs2.net.packet;

import com.rs2.model.player.Player;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

/** Writes and queues revision 443 server-to-client varp updates. */
public final class VarpPacket {
    public static final int SMALL_OPCODE = 62;
    public static final int LARGE_OPCODE = 74;

    private static final Map<Player, LinkedHashMap<Integer, Integer>> PENDING =
            new WeakHashMap<Player, LinkedHashMap<Integer, Integer>>();

    private VarpPacket() {
    }

    public static synchronized void queue(Player player, int id, int value) {
        LinkedHashMap<Integer, Integer> updates = PENDING.get(player);
        if (updates == null) {
            updates = new LinkedHashMap<Integer, Integer>();
            PENDING.put(player, updates);
        }
        updates.put(Integer.valueOf(id), Integer.valueOf(value));
    }

    public static void flush(Player player) {
        LinkedHashMap<Integer, Integer> updates;
        synchronized (VarpPacket.class) {
            updates = PENDING.remove(player);
        }
        if (updates == null) return;
        for (Map.Entry<Integer, Integer> update : updates.entrySet()) {
            send(player, update.getKey().intValue(), update.getValue().intValue());
        }
    }

    public static synchronized void clear(Player player) {
        PENDING.remove(player);
    }

    public static void send(Player player, int id, int value) {
        if (value >= -128 && value < 128) {
            PacketWriter packetWriter = PacketBuffer.allocateWriter(4);
            packetWriter.writeOpcode(player.getOutboundCipher(), SMALL_OPCODE);
            packetWriter.writeByte(value, ByteTransform.NEGATE);
            packetWriter.writeShort(id);
            player.writePacketBuffer(packetWriter.getBuffer());
            return;
        }

        PacketWriter packetWriter = PacketBuffer.allocateWriter(7);
        packetWriter.writeOpcode(player.getOutboundCipher(), LARGE_OPCODE);
        packetWriter.writeInt(value, ByteOrder.INVERSE_MIDDLE);
        packetWriter.writeShort(id);
        player.writePacketBuffer(packetWriter.getBuffer());
    }
}
