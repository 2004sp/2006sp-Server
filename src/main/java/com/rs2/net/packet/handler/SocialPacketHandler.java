package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class SocialPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket packet) {
        switch (packet.getOpcode()) {
            case 188: {
                long reader = packet.getReader().readLong();
                player.getSocialManager().addFriend(reader);
                return;
            }
            case 215: {
                long reader2 = packet.getReader().readLong();
                player.getSocialManager().removeFromList(player.getFriendsList(), reader2);
                return;
            }
            case 133: {
                long reader3 = packet.getReader().readLong();
                player.getSocialManager().addIgnore(reader3);
                return;
            }
            case 74: {
                long reader4 = packet.getReader().readLong();
                player.getSocialManager().removeFromList(player.getIgnoreList(), reader4);
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

