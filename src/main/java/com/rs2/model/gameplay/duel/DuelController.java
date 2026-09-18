package com.rs2.model.gameplay.duel;

import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.TextUtil;

public final class DuelController {
    private Player player;
    private boolean accepted = false;

    public DuelController(Player player) {
        this.player = player;
    }

    public final void handleDuelRequest(Player player) {
        if (this.player.gameMode != 0) {
            Player player2 = this.player;
            player2.packetSender.sendGameMessage("You are not playing on normal gamemode on cannot duel.");
            return;
        }
        if (player.gameMode != 0) {
            Player player3 = this.player;
            player3.packetSender.sendGameMessage(String.valueOf(player.getUsername()) + " is not playing on normal gamemode on cannot duel.");
            return;
        }
        if (player.getDuelRequestTarget() != this.player) {
            Player player4 = this.player;
            player4.packetSender.sendGameMessage("Sending duel request...");
            player4 = player;
            player4.packetSender.sendGameMessage(String.valueOf(TextUtil.formatDisplayName(this.player.getUsername())) + ":duelreq:");
            this.player.setDuelRequestTarget(player);
            return;
        }
        this.player.getDuelSession().setOpponent(player);
        player.getDuelSession().setOpponent(this.player);
        player.getDuelInterfaceManager().openDuelSetupInterface();
        this.player.getDuelInterfaceManager().openDuelSetupInterface();
        this.player.setDuelRequestTarget(null);
        player.setDuelRequestTarget(null);
    }

    public final void resetDuel(boolean returnStakedItems) {
        if (returnStakedItems) {
            int index = 0;
            while (index < this.player.getDuelSession().getStakedItems().size()) {
                this.player.getInventoryManager().addItem((ItemStack)this.player.getDuelSession().getStakedItems().get(index));
                ++index;
            }
        }
        boolean enabled = false;
        Object value = this;
        this.accepted = enabled;
        this.player.getDuelSession().clearDuelState();
        this.player.getDuelSession().setStarted(false);
        this.player.getDuelSession().setOpponent(null);
        this.player.setDuelRequestTarget(null);
        value = this.player;
        ((Player)value).packetSender.closeInterfaces();
        this.player.getDuelInterfaceManager().resetRules();
        GameplayHelper.refreshPlayerAreaOverlay(this.player);
    }

    public final void acceptCurrentDuelScreen() {
        if (this.player.getDuelSession().getOpponent() == null || !this.player.getDuelSession().getOpponent().isRegistered() || this.player.getDuelSession().getOpponent().getDuelController() == null) {
            this.resetDuel(true);
            return;
        }
        boolean enabled = true;
        Object value = this;
        this.accepted = enabled;
        value = this.player;
        if (((Player)value).interfaceAction == "duel") {
            if (!this.player.getDuelInterfaceManager().hasInventorySpaceForDuel()) {
                enabled = false;
                value = this.player.getDuelSession().getOpponent().getDuelController();
                this.player.getDuelSession().getOpponent().getDuelController().accepted = enabled;
                enabled = false;
                value = this.player.getDuelController();
                this.player.getDuelController().accepted = enabled;
                return;
            }
            value = this.player.getDuelSession().getOpponent().getDuelController();
            if (((DuelController)value).accepted) {
                this.player.getDuelInterfaceManager().openDuelConfirmInterface();
                this.player.getDuelSession().getOpponent().getDuelInterfaceManager().openDuelConfirmInterface();
            }
        } else {
            value = this.player;
            if (((Player)value).interfaceAction == "duel2") {
                value = this.player.getDuelSession().getOpponent().getDuelController();
                if (((DuelController)value).accepted) {
                    this.player.getDuelSession().startDuel();
                }
            }
        }
        this.player.getDuelSession().getOpponent().getDuelInterfaceManager().refreshAcceptStatus();
        this.player.getDuelInterfaceManager().refreshAcceptStatus();
    }

    public final boolean isAccepted() {
        return this.accepted;
    }

    public final void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}

