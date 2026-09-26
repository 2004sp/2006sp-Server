package com.rs2.model.npc;

import com.rs2.model.EntityUpdateState;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.net.packet.AccessMode;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketWriter;
import com.rs2.util.GameUtil;
import java.util.Iterator;
import java.util.List;

/** NPC update layout decoded by the revision 443 client (packet 238). */
public final class NpcUpdateTask {
    private NpcUpdateTask() { }

    public static void updatePlayer(Player player) {
        PacketWriter packet = PacketBuffer.allocateWriter(8192);
        PacketWriter masks = PacketBuffer.allocateWriter(4096);
        packet.startVariableShortPacket(player.getOutboundCipher(), 238);
        packet.setAccessMode(AccessMode.BIT_ACCESS);
        List<Npc> local = player.getLocalNpcs();
        synchronized (local) {
            packet.writeBits(8, local.size());
            Iterator<Npc> iterator = local.iterator();
            while (iterator.hasNext()) {
                Npc npc = iterator.next();
                if (!npc.isActive() || npc.teleportUpdateRequired
                        || !npc.getPosition().isWithinViewport(player.getPosition())) {
                    if (npc.teleportUpdateRequired) npc.teleportUpdateRequired = false;
                    packet.writeBoolean(true);
                    packet.writeBits(2, 3);
                    iterator.remove();
                    continue;
                }
                int mask = mask(npc);
                int walk = npc.getWalkDirection();
                int run = npc.getRunDirection();
                if (walk != -1) {
                    packet.writeBoolean(true);
                    packet.writeBits(2, run == -1 ? 1 : 2);
                    packet.writeBits(3, walk);
                    if (run != -1) packet.writeBits(3, run);
                    packet.writeBoolean(mask != 0);
                } else if (mask != 0) {
                    packet.writeBoolean(true);
                    packet.writeBits(2, 0);
                } else {
                    packet.writeBoolean(false);
                }
                if (mask != 0) writeMask(masks, npc, mask);
            }
            int added = 0;
            for (Npc npc : World.getNpcs()) {
                if (added == 16 || local.size() >= 255) break;
                if (npc == null || !npc.isActive() || local.contains(npc)
                        || !npc.getPosition().isWithinViewport(player.getPosition())) continue;
                Position delta = GameUtil.getDelta(player.getPosition(), npc.getPosition());
                packet.writeBits(15, npc.getIndex());
                packet.writeBoolean(true); // update block follows, including face/transform when needed
                packet.writeBits(13, npc.getNpcId());
                packet.writeBits(5, delta.getX());
                packet.writeBits(3, 0);
                packet.writeBits(5, delta.getY());
                packet.writeBoolean(false);
                local.add(npc);
                writeMask(masks, npc, mask(npc) | 0x10);
                added++;
            }
        }
        packet.writeBits(15, 32767);
        packet.setAccessMode(AccessMode.BYTE_ACCESS);
        packet.writeBuffer(masks.getBuffer());
        packet.finishVariableShortPacket();
        player.writePacketBuffer(packet.getBuffer());
    }

    private static int mask(Npc npc) {
        EntityUpdateState state = npc.getUpdateState();
        int mask = 0;
        if (state.isFacePositionUpdateRequired()) mask |= 0x2;
        if (state.isAnimationUpdateRequired()) mask |= 0x20;
        if (state.isPrimaryHitUpdateRequired()) mask |= 0x40;
        if (state.isGraphicUpdateRequired()) mask |= 0x4;
        if (state.isFaceEntityUpdateRequired()) mask |= 0x10;
        if (state.isSecondaryHitUpdateRequired()) mask |= 0x80;
        if (npc.isTransformed() && npc.getTransformedNpcId() != -1) mask |= 0x1;
        if (state.isForcedTextUpdateRequired()) mask |= 0x8;
        return mask;
    }

    private static void writeMask(PacketWriter packet, Npc npc, int mask) {
        EntityUpdateState state = npc.getUpdateState();
        packet.writeByte(mask);
        if ((mask & 0x2) != 0) {
            Position face = state.getFacePosition();
            packet.writeShort(face == null ? 0 : (face.getX() << 1) + 1);
            packet.writeShort(face == null ? 0 : (face.getY() << 1) + 1, ByteOrder.LITTLE);
        }
        if ((mask & 0x20) != 0) {
            packet.writeShort(state.getAnimationId(), ByteTransform.ADD, ByteOrder.LITTLE);
            packet.writeByte(state.getAnimationDelay(), ByteTransform.ADD);
        }
        if ((mask & 0x40) != 0) {
            packet.writeByte(state.getPrimaryHitDamage(), ByteTransform.ADD);
            packet.writeByte(state.getPrimaryHitType(), ByteTransform.ADD);
            packet.writeByte(GameplayHelper.calculatePercent(npc.getCurrentHitpoints(), npc.getMaxHitpoints(), 100));
            packet.writeByte(100, ByteTransform.NEGATE);
        }
        if ((mask & 0x4) != 0) {
            packet.writeShort(state.getGraphicId(), ByteTransform.ADD, ByteOrder.LITTLE);
            packet.writeInt(state.getGraphicDelay(), ByteOrder.INVERSE_MIDDLE);
        }
        if ((mask & 0x10) != 0) packet.writeShort(state.getFaceEntityId(), ByteOrder.LITTLE);
        if ((mask & 0x80) != 0) {
            packet.writeByte(state.getSecondaryHitDamage(), ByteTransform.SUBTRACT);
            packet.writeByte(state.getSecondaryHitType(), ByteTransform.SUBTRACT);
            packet.writeByte(GameplayHelper.calculatePercent(npc.getCurrentHitpoints(), npc.getMaxHitpoints(), 100));
            packet.writeByte(100, ByteTransform.SUBTRACT);
        }
        if ((mask & 0x1) != 0) packet.writeShort(npc.getTransformedNpcId(), ByteOrder.LITTLE);
        if ((mask & 0x8) != 0) packet.writeString(state.getForcedText());
    }
}
