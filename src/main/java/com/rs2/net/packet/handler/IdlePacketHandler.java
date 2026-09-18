package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class IdlePacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        player.setIdlePacketCount(player.getIdlePacketCount() + 1);
        if (!ServerSettings.idleLogoutEnabled || player.botEnabled) {
            return;
        }
        if (player.getIdlePacketCount() > 20 && (player.getPlayerRights() < 2 && player.getSingleCombatTimer().hasElapsed() || player.logoutPacketSent)) {
            player.packetSender.sendLogout();
            player.disconnect();
        }
    }
}
