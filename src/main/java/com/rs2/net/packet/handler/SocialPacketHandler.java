package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.GameplayTrace;
import com.rs2.util.ChatTextCodec;
import com.rs2.util.ChatCodec;

public final class SocialPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket packet) {
        switch (packet.getOpcode()) {
            case 188:
            case 90: {
                long reader = packet.getReader().readLong();
                player.getSocialManager().addFriend(reader);
                return;
            }
            case 215:
            case 159: {
                long reader2 = packet.getReader().readLong();
                player.getSocialManager().removeFromList(player.getFriendsList(), reader2);
                return;
            }
            case 133: {
                long reader3 = packet.getReader().readLong();
                player.getSocialManager().addIgnore(reader3);
                return;
            }
            case 198: {
                long reader3 = packet.getReader().readLong();
                player.getSocialManager().addIgnore(reader3);
                return;
            }
            case 74:
            case 250: {
                long reader4 = packet.getReader().readLong();
                player.getSocialManager().removeFromList(player.getIgnoreList(), reader4);
                return;
            }
            case 50: {
                long recipient = packet.getReader().readLong();
                int compressedLength = packet.getLength() - 8;
                if (compressedLength <= 0) return;
                byte[] compressed = packet.getReader().readBytes(compressedLength);
                String message;
                try {
                    message = ChatCodec.get().decode(compressed);
                } catch (IllegalArgumentException exception) {
                    return;
                }
                if (player.isMuted()) {
                    player.packetSender.sendGameMessage("You are muted and cannot talk. Mute expires in: " + (GameplayHelper.getHoursBetween(System.currentTimeMillis(), player.getMuteExpires()) + 1) + " hours.");
                    return;
                }
                byte[] legacy = new byte[256];
                int length = ChatTextCodec.encode(message, legacy);
                player.getSocialManager().sendPrivateMessage(player, recipient, legacy, length);
                return;
            }
            case 126: {
                long reader5 = packet.getReader().readLong();
                int length = packet.getLength() - 8;
                if (length < 0) {
                    return;
                }
                byte[] messageBytes = packet.getReader().readBytes(length);
                if (player.isMuted()) {
                    player.packetSender.sendGameMessage("You are muted and cannot talk. Mute expires in: " + (GameplayHelper.getHoursBetween(System.currentTimeMillis(), player.getMuteExpires()) + 1) + " hours.");
                    return;
                }
                player.getSocialManager().sendPrivateMessage(player, reader5, messageBytes, length);
            }
        }
    }

}

