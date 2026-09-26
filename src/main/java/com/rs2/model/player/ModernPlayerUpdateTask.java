package com.rs2.model.player;

import com.rs2.model.Entity;
import com.rs2.model.EntityUpdateState;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.net.packet.AccessMode;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketWriter;
import com.rs2.net.packet.VarpPacket;
import com.rs2.util.GameUtil;
import java.util.Iterator;

/** Minimal revision 443 player update encoder. */
public final class ModernPlayerUpdateTask {
    private static final int PLAYER_UPDATE_OPCODE = 29;
    private static final int APPEARANCE_MASK = 0x40;

    private ModernPlayerUpdateTask() {
    }

    public static void updatePlayer(Player player) {
        VarpPacket.flush(player);
        int selfMask = getUpdateMask(player, false);
        PacketWriter packet = PacketBuffer.allocateWriter(4096);
        PacketWriter masks = PacketBuffer.allocateWriter(2048);
        packet.startVariableShortPacket(player.getOutboundCipher(), PLAYER_UPDATE_OPCODE);
        packet.setAccessMode(AccessMode.BIT_ACCESS);
        writeLocalMovement(player, packet, selfMask != 0);
        if (selfMask != 0) {
            writeUpdateMask(player, player, masks, selfMask);
        }

        packet.writeBits(8, player.getLocalPlayers().size());
        Iterator iterator = player.getLocalPlayers().iterator();
        while (iterator.hasNext()) {
            Player other = (Player) iterator.next();
            if (!isWithinViewport(player, other) || !other.isVisibleToOtherPlayers()
                    || other.getConnectionState() == PlayerConnectionState.DISCONNECTED
                    || other.isTeleporting()) {
                packet.writeBoolean(true);
                packet.writeBits(2, 3);
                iterator.remove();
                continue;
            }
            int updateMask = getUpdateMask(other, false);
            int walk = other.getWalkDirection();
            int run = other.getRunDirection();
            if (walk != -1) {
                packet.writeBoolean(true);
                if (run != -1) {
                    packet.writeBits(2, 2);
                    packet.writeBits(3, walk);
                    packet.writeBits(3, run);
                } else {
                    packet.writeBits(2, 1);
                    packet.writeBits(3, walk);
                }
                packet.writeBoolean(updateMask != 0);
            } else if (updateMask != 0) {
                packet.writeBoolean(true);
                packet.writeBits(2, 0);
            } else {
                packet.writeBoolean(false);
            }
            if (updateMask != 0) {
                writeUpdateMask(player, other, masks, updateMask);
            }
        }

        int added = 0;
        Player[] players = World.getPlayers();
        for (int index = 1; index < players.length && added < 16
                && player.getLocalPlayers().size() < 255; index++) {
            Player other = players[index];
            if (other == null || other == player
                    || other.getConnectionState() == PlayerConnectionState.DISCONNECTED
                    || player.getLocalPlayers().contains(other)
                    || !isWithinViewport(player, other) || !other.isVisibleToOtherPlayers()) {
                continue;
            }
            player.getLocalPlayers().add(other);
            Position delta = GameUtil.getDelta(player.getPosition(), other.getPosition());
            packet.writeBits(11, other.getIndex());
            packet.writeBoolean(true);
            packet.writeBits(5, delta.getY());
            packet.writeBoolean(true);
            packet.writeBits(3, 0);
            packet.writeBits(5, delta.getX());
            writeUpdateMask(player, other, masks, getUpdateMask(other, true));
            added++;
        }
        packet.writeBits(11, 2047);
        packet.setAccessMode(AccessMode.BYTE_ACCESS);
        packet.writeBuffer(masks.getBuffer());
        packet.finishVariableShortPacket();
        player.writePacketBuffer(packet.getBuffer());
        player.publicChatUpdatePending = false;
    }
    private static boolean isWithinViewport(Player viewer, Player other) {
        return other.getPosition().isWithinViewport(viewer.getPosition());
    }
    private static void writeLocalMovement(Player player, PacketWriter packet,
                                           boolean appearance) {
        if (player.isTeleporting()) {
            packet.writeBoolean(true);
            packet.writeBits(2, 3);
            packet.writeBits(7, Position.updateLocalX(player));
            packet.writeBoolean(appearance);
            packet.writeBits(2, player.getPosition().getPlane());
            packet.writeBits(7, Position.updateLocalY(player));
            packet.writeBoolean(player.isTeleportPlacementUpdateRequired());
            return;
        }
        int walk = player.getWalkDirection();
        int run = player.getRunDirection();
        if (walk != -1) {
            packet.writeBoolean(true);
            if (run != -1) {
                packet.writeBits(2, 2);
                packet.writeBits(3, walk);
                packet.writeBits(3, run);
            } else {
                packet.writeBits(2, 1);
                packet.writeBits(3, walk);
            }
            packet.writeBoolean(appearance);
        } else if (appearance) {
            packet.writeBoolean(true);
            packet.writeBits(2, 0);
        } else {
            packet.writeBoolean(false);
        }
    }

    private static int getUpdateMask(Player player, boolean forceAppearance) {
        EntityUpdateState state = player.getUpdateState();
        int mask = 0;
        if (state.isForcedMovementUpdateRequired()) mask |= 0x100;
        if (state.isGraphicUpdateRequired()) mask |= 0x200;
        if (state.isAnimationUpdateRequired()) mask |= 0x8;
        if (state.isForcedTextUpdateRequired()) mask |= 0x1;
        if (state.isFaceEntityUpdateRequired()) mask |= 0x10;
        if (state.isFacePositionUpdateRequired()) mask |= 0x2;
        if (state.isAppearanceUpdateRequired() && player.getPublicChatPayload() != null) mask |= 0x4;
        if (state.isPrimaryHitUpdateRequired()) mask |= 0x400;
        if (state.isSecondaryHitUpdateRequired()) mask |= 0x20;
        if (player.isAppearanceUpdateRequired() || forceAppearance) mask |= APPEARANCE_MASK;
        return mask;
    }

    private static void writeUpdateMask(Player viewer, Player player,
                                        PacketWriter masks, int mask) {
        if (mask >= 256) {
            masks.writeByte((mask & 0xFF) | 0x80);
            masks.writeByte(mask >> 8);
        } else {
            masks.writeByte(mask);
        }
        EntityUpdateState state = player.getUpdateState();
        if ((mask & APPEARANCE_MASK) != 0) writeAppearanceBlock(player, masks);
        if ((mask & 0x100) != 0) writeForcedMovement(viewer, player, masks);
        if ((mask & 0x200) != 0) {
            masks.writeShort(state.getGraphicId());
            masks.writeInt(state.getGraphicDelay(), ByteOrder.INVERSE_MIDDLE);
        }
        if ((mask & 0x1) != 0) masks.writeString(state.getForcedText());
        if ((mask & 0x10) != 0) {
            masks.writeShort(state.getFaceEntityId(), ByteTransform.ADD, ByteOrder.LITTLE);
        }
        if ((mask & 0x2) != 0) {
            masks.writeShort((state.getFacePosition().getX() << 1) + 1,
                    ByteTransform.ADD, ByteOrder.LITTLE);
            masks.writeShort((state.getFacePosition().getY() << 1) + 1, ByteOrder.LITTLE);
        }
        if ((mask & 0x8) != 0) {
            masks.writeShort(state.getAnimationId(), ByteTransform.ADD, ByteOrder.LITTLE);
            masks.writeByte(state.getAnimationDelay(), ByteTransform.NEGATE);
        }
        if ((mask & 0x4) != 0) writePublicChat(player, masks);
        if ((mask & 0x400) != 0) {
            masks.writeByte(state.getPrimaryHitDamage());
            masks.writeByte(state.getPrimaryHitType(), ByteTransform.SUBTRACT);
            masks.writeByte(player.getSkillManager().getCurrentLevels()[3], ByteTransform.ADD);
            masks.writeByte(player.getSkillManager().getBaseLevel(3));
        }
        if ((mask & 0x20) != 0) {
            masks.writeByte(state.getSecondaryHitDamage(), ByteTransform.NEGATE);
            masks.writeByte(state.getSecondaryHitType());
            masks.writeByte(player.getSkillManager().getCurrentLevels()[3], ByteTransform.NEGATE);
            masks.writeByte(player.getSkillManager().getBaseLevel(3), ByteTransform.NEGATE);
        }
    }

    private static void writeForcedMovement(Player viewer, Player player, PacketWriter masks) {
        EntityUpdateState state = player.getUpdateState();
        int startX = EntityUpdateState.getLocalXForUpdate(player);
        int startY = EntityUpdateState.getLocalYForUpdate(player);
        if (viewer != player) {
            int deltaX = player.getPosition().getX() - viewer.getPosition().getX();
            int deltaY = player.getPosition().getY() - viewer.getPosition().getY();
            startX = EntityUpdateState.getLocalXForUpdate(viewer) + deltaX;
            startY = EntityUpdateState.getLocalYForUpdate(viewer) + deltaY;
        }
        masks.writeByte(startX, ByteTransform.SUBTRACT);
        masks.writeByte(startY, ByteTransform.NEGATE);
        masks.writeByte(startX + state.getForcedMovementEndXOffset(), ByteTransform.SUBTRACT);
        masks.writeByte(startY + state.getForcedMovementEndYOffset(), ByteTransform.SUBTRACT);
        masks.writeShort(state.getForcedMovementStartDelay(), ByteTransform.ADD, ByteOrder.LITTLE);
        masks.writeShort(state.getForcedMovementEndDelay(), ByteTransform.ADD, ByteOrder.LITTLE);
        masks.writeByte(state.getForcedMovementDirection(), ByteTransform.NEGATE);
    }

    private static void writePublicChat(Player player, PacketWriter masks) {
        int colorEffects = ((player.getPublicChatColor() & 0xFF) << 8)
                | player.getPublicChatEffects() & 0xFF;
        byte[] payload = player.getPublicChatPayload();
        masks.writeShort(colorEffects);
        masks.writeByte(player.getPlayerRights());
        masks.writeByte(payload.length, ByteTransform.ADD);
        for (int i = payload.length - 1; i >= 0; i--) masks.writeByte(payload[i]);
    }

    private static void writeAppearanceBlock(Player player, PacketWriter masks) {
        PacketWriter appearance = buildAppearance(player);
        int length = appearance.getBuffer().position();
        masks.writeByte(length, ByteTransform.ADD);
        appearance.getBuffer().flip();
        while (appearance.getBuffer().hasRemaining()) {
            masks.writeByte(appearance.getBuffer().get() & 0xFF, ByteTransform.ADD);
        }
    }
    private static PacketWriter buildAppearance(Player player) {
        PacketWriter appearance = PacketBuffer.allocateWriter(96);
        appearance.writeByte(player.getGender());
        appearance.writeByte(player.getPrayerHeadIcon());
        appearance.writeByte(player.getSkullIcon());
        appearance.writeByte(0); // head equipment
        appearance.writeByte(0); // cape
        appearance.writeByte(0); // amulet
        if (player.getEquipmentManager().getContainer().hasItemAtSlot(3)
        && !player.shouldHideHeldItemsInAppearance()
        && player.getEquipmentManager().getContainer().getItemAt(3).isEquippable()) {
    appearance.writeShort(512
            + player.getEquipmentManager().getContainer().getItemAt(3).getId());
} else {
    appearance.writeByte(0);
}
        appearance.writeShort(256 + player.getAppearanceParts()[0]);
        if (player.getEquipmentManager().getContainer().hasItemAtSlot(5)
        && !player.shouldHideHeldItemsInAppearance()
        && player.getEquipmentManager().getContainer().getItemAt(5).isEquippable()) {
    appearance.writeShort(512
            + player.getEquipmentManager().getContainer().getItemAt(5).getId());
} else {
    appearance.writeByte(0);
}
        appearance.writeShort(256 + player.getAppearanceParts()[1]);
        appearance.writeShort(256 + player.getAppearanceParts()[2]);
        appearance.writeShort(256 + player.getAppearanceParts()[3]);
        appearance.writeShort(256 + player.getAppearanceParts()[4]);
        appearance.writeShort(256 + player.getAppearanceParts()[5]);
        if (player.getGender() == 0) {
            appearance.writeShort(256 + player.getAppearanceParts()[6]);
        } else {
            appearance.writeByte(0);
        }
        for (int color : player.getAppearanceColors()) {
            appearance.writeByte(color);
        }
        appearance.writeShort(player.getStandAnimation());
        appearance.writeShort(player.getWalkAnimation());
        appearance.writeShort(player.getWalkAnimation());
        appearance.writeShort(player.getWalkAnimation());
        appearance.writeShort(player.getWalkAnimation());
        appearance.writeShort(player.getWalkAnimation());
        appearance.writeShort(player.getRunAnimation());
        appearance.writeLong(player.getNameHash());
        appearance.writeByte(player.getCombatLevel());
        appearance.writeShort(0); // 0 makes the client display combat level instead of Skill
        return appearance;
    }
}
