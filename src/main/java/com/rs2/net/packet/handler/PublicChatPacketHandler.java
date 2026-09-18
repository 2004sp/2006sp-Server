package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.ChatTextCodec;

public final class PublicChatPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        int effects = incomingPacket.getReader().readByte(false, ByteTransform.SUBTRACT);
        int color = incomingPacket.getReader().readByte(false, ByteTransform.SUBTRACT);
        int payloadLength = incomingPacket.getLength() - 2;
        byte[] payload = incomingPacket.getReader().readBytesReverse(payloadLength, ByteTransform.ADD);
        String message = ChatTextCodec.decode(payload, payloadLength);
        if (player.isMuted()) {
            player.packetSender.sendGameMessage("You are muted and cannot talk. Mute expires in: " + (GameplayHelper.getHoursBetween(System.currentTimeMillis(), player.getMuteExpires()) + 1) + " hours.");
            return;
        }
        if (player.getQuestState(0) != 1) {
            return;
        }
        player.setPublicChatEffects(effects);
        player.setPublicChatColor(color);
        player.setPublicChatPayload(payload);
        player.flagAppearanceUpdate(true);
        if (!player.isBot) {
            String trimmedMessage = message.replaceAll("\\s+$", "");
            player.pendingPublicChatText = trimmedMessage;
            if (player.getMovementTarget() != null) {
                if (!player.getMovementTarget().isPlayer()) {
                    return;
                }
                Player player2 = (Player)player.getMovementTarget();
                if (!player2.isBot) {
                    return;
                }
                player2.botPvpChatMessage = trimmedMessage;
                player2.botPvpChatSource = player;
                return;
            }
            if (player.botPvpTeamRequesters.size() > 0) {
                for (Object requesterObject : player.botPvpTeamRequesters) {
                    Player player3 = (Player)requesterObject;
                    player3.botPvpChatMessage = trimmedMessage;
                    player3.botPvpChatSource = player;
                }
            }
        }
    }
}

