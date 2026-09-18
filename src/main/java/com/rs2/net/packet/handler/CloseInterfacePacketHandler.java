package com.rs2.net.packet.handler;

import com.rs2.model.GameplayHelper;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class CloseInterfacePacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        GameplayHelper.declineTrade(player);
        PartyRoomManager.returnStagedChestItems(player);
        if (player.interfaceAction == "flour") {
            GameplayHelper.handleFlourDoughButton(player, 53204);
        }
        if ((player.interfaceAction == "duel" || player.interfaceAction == "duel2") && !player.isInDuelArena()) {
            if (player.getDuelSession().getOpponent() != null && player.getDuelSession().getOpponent().isRegistered()) {
                player.getDuelSession().getOpponent().getDuelController().resetDuel(true);
                player.getDuelSession().getOpponent().packetSender.sendGameMessage("Other played declined the duel.");
            }
            player.getDuelController().resetDuel(true);
        }
        player.getAttributes().put("isBanking", Boolean.FALSE);
        player.getAttributes().put("isShopping", Boolean.FALSE);
        player.interfaceAction = "";
        if (player.getQuestState(0) != 1) {
            player.packetSender.closeInterfaces();
        }
        if (player.getQuestState(0) == 1) {
            player.packetSender.setSidebarInterface(3, 3213);
        }
        player.setOpenInterfaceId(0);
        if (player.queuedLevelUpSkillIds.size() > 0) {
            player.getSkillManager().showLevelUpInterface((Integer)player.queuedLevelUpSkillIds.get(0));
            player.queuedLevelUpSkillIds.remove(0);
        }
    }
}
