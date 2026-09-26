package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class CameraPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        int reader;
        int reader2;
        if (ServerSettings.clientBuild == 443) {
            reader = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
            reader2 = incomingPacket.getReader().readSignedShort();
        } else {
            reader = incomingPacket.getReader().readSignedShort();
            reader2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        }
        if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
            System.out.println("a = " + reader + "---- b = " + reader2);
        }
    }
}

