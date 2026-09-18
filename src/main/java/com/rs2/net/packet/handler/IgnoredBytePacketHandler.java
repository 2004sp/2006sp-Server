package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class IgnoredBytePacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        incomingPacket.getReader().readUnsignedByte(false);
    }
}

