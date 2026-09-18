package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import org.joda.time.DateTime;
import org.joda.time.base.BaseDateTime;

public final class ReportAbusePacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        long reportedNameHash = incomingPacket.getReader().readLong();
        int reportType = incomingPacket.getReader().readUnsignedByte(false);
        boolean mutePlayer = incomingPacket.getReader().readUnsignedByte(false) == 1;
        Player reportedPlayer = null;
        Player[] playerArray = World.getPlayers();
        int count = playerArray.length;
        int index = 0;
        while (index < count) {
            Player candidate = playerArray[index];
            if (candidate != null && candidate.getNameHash() == reportedNameHash) {
                reportedPlayer = candidate;
                break;
            }
            ++index;
        }
        if (reportedPlayer == null) {
            player.packetSender.sendGameMessage("You cannot report offline players.");
            return;
        }
        if (player.getNameHash() == reportedNameHash) {
            player.packetSender.sendGameMessage("You cannot report yourself!");
            return;
        }
        if (System.currentTimeMillis() - player.lastAbuseReportMillis < 60000L) {
            player.packetSender.sendGameMessage("You can only report a player once every 60 seconds.");
            return;
        }
        player.packetSender.sendGameMessage("Thank-you, your abuse report has been received.");
        if (mutePlayer && player.getPlayerRights() > 0 && reportedPlayer.getPlayerRights() == 0) {
            if (reportedPlayer.isMuted()) {
                return;
            }
            DateTime muteExpires = new DateTime(System.currentTimeMillis()).plusHours(48);
            reportedPlayer.setMuteExpires(muteExpires.getMillis());
        }
        player.lastAbuseReportMillis = System.currentTimeMillis();
        GameplayHelper.appendLogLine(String.valueOf(System.currentTimeMillis()) + "\u00a7" + player.getUsername() + "\u00a7" + player.getPosition().getX() + "\u00a7" + player.getPosition().getY() + "\u00a7" + player.getPosition().getPlane() + "\u00a7" + reportedPlayer.getUsername() + "\u00a7" + reportedPlayer.getPosition().getX() + "\u00a7" + reportedPlayer.getPosition().getY() + "\u00a7" + reportedPlayer.getPosition().getPlane() + "\u00a7" + reportType + "\u00a7" + (mutePlayer ? 1 : 0), "reports");
    }
}

