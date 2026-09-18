package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.GameUtil;

public final class ItemSpawnPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        if (player.isActionLocked()) {
            return;
        }
        switch (incomingPacket.getOpcode()) {
            case 19: {
                Object value;
                int reader = incomingPacket.getReader().readSignedShort();
                if (reader < 0 || reader > 11883) {
                    return;
                }
                if (reader == 995 || player.getOpenInterfaceId() != 18890 || ((ItemStack)(value = new ItemStack(reader, 1))).getDefinition().isUntradeable()) break;
                if (((ItemStack)value).getDefinition().isMembersOnly() && reader != 7999 && reader != 8000) {
                    if (player.isMember()) {
                        if (ServerSettings.freeToPlayWorld) {
                            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                            return;
                        }
                    } else {
                        player.packetSender.sendGameMessage("You need a members account to access members content.");
                        return;
                    }
                }
                player.selectedGrandExchangeItemId = reader;
                player.selectedGrandExchangeQuantity = 1;
                player.selectedGrandExchangeUnitPrice = GrandExchangeManager.getGuidePrice(reader);
                value = player;
                ((Player)value).packetSender.sendInterfaceItemModel(18938, player.selectedGrandExchangeItemId);
                value = player;
                ((Player)value).packetSender.sendInterfaceText(GameUtil.formatNumber(GrandExchangeManager.getGuidePrice(reader)), 18919);
                value = player;
                ((Player)value).packetSender.sendInterfaceText("" + player.selectedGrandExchangeQuantity, 18920);
                value = player;
                ((Player)value).packetSender.sendInterfaceText(String.valueOf(GameUtil.formatNumber(player.selectedGrandExchangeUnitPrice)) + " coins", 18921);
                value = player;
                ((Player)value).packetSender.sendInterfaceText(String.valueOf(GameUtil.formatNumber(player.selectedGrandExchangeUnitPrice * player.selectedGrandExchangeQuantity)) + " coins", 18922);
            }
        }
    }
}

