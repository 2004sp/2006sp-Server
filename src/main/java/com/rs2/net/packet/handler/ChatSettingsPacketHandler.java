package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class ChatSettingsPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        byte reader = (byte)incomingPacket.getReader().readUnsignedByte(false);
        byte reader2 = (byte)incomingPacket.getReader().readUnsignedByte(false);
        byte reader3 = (byte)incomingPacket.getReader().readUnsignedByte(false);
        player.setPrivateChatMode(reader2);
        player.setPublicChatMode(reader);
        player.setTradeMode(reader3);
        player.getSocialManager().refreshFriendStatuses(false);
    }
}

