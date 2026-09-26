package com.rs2.net.packet;

import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.player.Player;

/** Exact revision 443 local-zone event packet writers. */
public final class ZonePacket {
    public static final int GROUND_AMOUNT_OPCODE = 79;
    public static final int GROUND_REMOVE_OPCODE = 84;
    public static final int GROUND_ADD_EXCEPT_OPCODE = 94;
    public static final int PROJECTILE_OPCODE = 101;
    public static final int AREA_SOUND_OPCODE = 109;
    public static final int SPOT_ANIMATION_OPCODE = 115;
    public static final int PLAYER_OBJECT_OPCODE = 129;
    public static final int GROUND_ADD_OPCODE = 207;

    private ZonePacket() {
    }

    public static void sendGroundItemCreate(Player player, GroundItem groundItem) {
        Position position = groundItem.getPosition();
        if (!ObjectPacket.sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(6);
        writer.writeOpcode(player.getOutboundCipher(), GROUND_ADD_OPCODE);
        writer.writeShort(clampUnsignedShort(groundItem.getItem().getAmount()),
                ByteTransform.ADD, ByteOrder.BIG);
        writer.writeShort(groundItem.getItem().getId(), ByteOrder.LITTLE);
        writer.writeByte(0);
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendGroundItemRemove(Player player, GroundItem groundItem) {
        Position position = groundItem.getPosition();
        if (!ObjectPacket.sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(4);
        writer.writeOpcode(player.getOutboundCipher(), GROUND_REMOVE_OPCODE);
        writer.writeShort(groundItem.getItem().getId(), ByteOrder.LITTLE);
        writer.writeByte(0);
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendGroundItemAmount(Player player, Position position,
                                            int itemId, int oldAmount, int newAmount) {
        if (!ObjectPacket.sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(8);
        writer.writeOpcode(player.getOutboundCipher(), GROUND_AMOUNT_OPCODE);
        writer.writeByte(0);
        writer.writeShort(itemId);
        writer.writeShort(clampUnsignedShort(oldAmount));
        writer.writeShort(clampUnsignedShort(newAmount));
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendGroundItemCreateExcept(Player player, Position position,
                                                  int sourcePlayerIndex,
                                                  int itemId, int amount) {
        if (!ObjectPacket.sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(8);
        writer.writeOpcode(player.getOutboundCipher(), GROUND_ADD_EXCEPT_OPCODE);
        writer.writeShort(sourcePlayerIndex, ByteTransform.ADD, ByteOrder.BIG);
        writer.writeShort(clampUnsignedShort(amount), ByteTransform.ADD, ByteOrder.BIG);
        writer.writeByte(0, ByteTransform.NEGATE);
        writer.writeShort(itemId, ByteTransform.ADD, ByteOrder.BIG);
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendProjectile(Player player, Position position,
                                      int projectileId, byte deltaX, byte deltaY,
                                      int targetIndex, int startDelay, int endDelay,
                                      int startHeight, int endHeight,
                                      int slope, int startDistance) {
        if (!ObjectPacket.sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(16);
        writer.writeOpcode(player.getOutboundCipher(), PROJECTILE_OPCODE);
        writer.writeByte(0);
        writer.writeByte(deltaX);
        writer.writeByte(deltaY);
        writer.writeShort(targetIndex);
        writer.writeShort(projectileId);
        writer.writeByte(startHeight);
        writer.writeByte(endHeight);
        writer.writeShort(startDelay);
        writer.writeShort(endDelay);
        writer.writeByte(slope);
        writer.writeByte(startDistance);
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendSpotAnimation(Player player, Position position,
                                         int graphicId, int heightOffset, int delay) {
        if (!ObjectPacket.sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(7);
        writer.writeOpcode(player.getOutboundCipher(), SPOT_ANIMATION_OPCODE);
        writer.writeByte(0);
        writer.writeShort(graphicId);
        writer.writeByte(heightOffset);
        writer.writeShort(delay);
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendPlayerObjectAttachment(Player player, Position position,
                                                  int playerIndex, int objectId,
                                                  int type, int orientation,
                                                  int startDelay, int endDelay,
                                                  int minXOffset, int minYOffset,
                                                  int maxXOffset, int maxYOffset) {
        if (!ObjectPacket.sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(15);
        writer.writeOpcode(player.getOutboundCipher(), PLAYER_OBJECT_OPCODE);
        writer.writeByte(maxYOffset, ByteTransform.NEGATE);
        writer.writeByte(minXOffset, ByteTransform.SUBTRACT);
        writer.writeShort(objectId, ByteTransform.ADD, ByteOrder.BIG);
        writer.writeByte(maxXOffset, ByteTransform.SUBTRACT);
        writer.writeByte(minYOffset, ByteTransform.ADD);
        writer.writeShort(startDelay, ByteTransform.ADD, ByteOrder.BIG);
        writer.writeByte((type << 2) | (orientation & 3), ByteTransform.NEGATE);
        writer.writeByte(0, ByteTransform.SUBTRACT);
        writer.writeShort(endDelay);
        writer.writeShort(playerIndex, ByteTransform.ADD, ByteOrder.LITTLE);
        player.writePacketBuffer(writer.getBuffer());
    }

    public static void sendAreaSound(Player player, Position position, int soundId,
                                     int radius, int loops, int delay) {
        if (!ObjectPacket.sendZoneBase(player, position)) return;
        PacketWriter writer = PacketBuffer.allocateWriter(6);
        writer.writeOpcode(player.getOutboundCipher(), AREA_SOUND_OPCODE);
        writer.writeByte(0);
        writer.writeShort(soundId);
        writer.writeByte((radius & 15) << 4 | loops & 7);
        writer.writeByte(delay);
        player.writePacketBuffer(writer.getBuffer());
    }

    private static int clampUnsignedShort(int value) {
        if (value < 0) return 0;
        return value > 65535 ? 65535 : value;
    }
}
