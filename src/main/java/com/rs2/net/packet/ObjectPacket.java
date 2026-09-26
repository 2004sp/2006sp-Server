package com.rs2.net.packet;

import com.rs2.model.Position;
import com.rs2.model.player.Player;

/** Exact revision 443 zone-base and runtime location packet writers. */
public final class ObjectPacket {
    public static final int REMOVE_OPCODE = 69;
    public static final int ZONE_BASE_OPCODE = 82;
    public static final int ADD_OPCODE = 122;
    public static final int ANIMATE_OPCODE = 170;

    private ObjectPacket() {
    }

    public static void sendCreate(Player player, int objectId, Position position,
                                  int orientation, int type) {
        if (!sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(5);
        writer.writeOpcode(player.getOutboundCipher(), ADD_OPCODE);
        writer.writeByte(0, ByteTransform.NEGATE);
        writer.writeShort(objectId);
        writer.writeByte((type << 2) | (orientation & 3), ByteTransform.ADD);
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendRemove(Player player, Position position,
                                  int orientation, int type) {
        if (!sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(3);
        writer.writeOpcode(player.getOutboundCipher(), REMOVE_OPCODE);
        writer.writeByte((type << 2) | (orientation & 3));
        writer.writeByte(0, ByteTransform.ADD);
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendAnimation(Player player, Position position,
                                     int orientation, int type, int sequenceId) {
        if (!sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(5);
        writer.writeOpcode(player.getOutboundCipher(), ANIMATE_OPCODE);
        writer.writeShort(sequenceId, ByteTransform.ADD, ByteOrder.LITTLE);
        writer.writeByte((type << 2) | (orientation & 3), ByteTransform.ADD);
        writer.writeByte(0, ByteTransform.SUBTRACT);
        player.writePacketBuffer(writer.getBuffer());
    }

    static boolean sendZoneBase(Player player, Position position) {
        int baseX = player.getLastKnownRegionPosition().getRegionX() << 3;
        int baseY = player.getLastKnownRegionPosition().getRegionY() << 3;
        int localX = position.getX() - baseX;
        int localY = position.getY() - baseY;
        if (localX < 0 || localY < 0 || localX >= 104 || localY >= 104) {
            return false;
        }
        PacketWriter writer = PacketBuffer.allocateWriter(3);
        writer.writeOpcode(player.getOutboundCipher(), ZONE_BASE_OPCODE);
        writer.writeByte(localX, ByteTransform.SUBTRACT);
        writer.writeByte(localY, ByteTransform.SUBTRACT);
        player.writePacketBuffer(writer.getBuffer());
        return true;
    }
}
