package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelSession;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.TextUtil;

public final class DuelInterfaceManager {
    private Player player;
    private int ruleConfigValue = 0;
    private static int[] confirmRuleTextIds = new int[]{8242, 8243, 8244, 8245, 8246, 8247, 8248, 8249, 8251, 8252, 8253};
    private static final int[] ruleConfigMasks = new int[]{1, 2, 16, 32, 64, 128, 256, 512, 1024, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 0x200000, 0x800000, 0x1000000, 0x4000000, 0x8000000, 0x10000000};

    public DuelInterfaceManager(Player player) {
        this.player = player;
    }

    public final void openDuelSetupInterface() {
        this.resetRules();
        Player player = this.player;
        player.packetSender.showInterface(6575);
        String text = "duel";
        player = this.player;
        this.player.interfaceAction = text;
        player = this.player;
        player.packetSender.sendInterfaceText("Dueling with:  " + this.player.getDuelSession().getOpponent().getUsername() + "  Opponent's combat level: " + TextUtil.formatCombatLevel(this.player.getCombatLevel(), this.player.getDuelSession().getOpponent().getCombatLevel()), 6671);
        player = this.player;
        player.packetSender.sendItemContainer(13824, this.player.getEquipmentManager().getContainer().getRawItems());
        this.refreshStakeContainers();
        this.refreshAcceptStatus();
    }

    public final void openDuelConfirmInterface() {
        Object value;
        openDuelConfirmInterfaceControlExit1: {
            Player player = this.player;
            player.packetSender.showInterface(6412);
            value = "duel2";
            player = this.player;
            this.player.interfaceAction = (String)value;
            this.player.getDuelController().setAccepted(false);
            value = this;
            player = ((DuelInterfaceManager)value).player;
            player.packetSender.sendInterfaceText("Hitpoints will be restored.", 8250);
            player = ((DuelInterfaceManager)value).player;
            player.packetSender.sendInterfaceText("Boosted stats will be restored.", 8238);
            int value2 = 11;
            while (value2 < ((DuelInterfaceManager)value).player.getDuelSession().getEnabledRules().length) {
                if (((DuelInterfaceManager)value).player.getDuelSession().getEnabledRules()[value2]) {
                    player = ((DuelInterfaceManager)value).player;
                    player.packetSender.sendInterfaceText("Some worn items will be taken off", 8239);
                    break openDuelConfirmInterfaceControlExit1;
                }
                ++value2;
            }
            player = ((DuelInterfaceManager)value).player;
            player.packetSender.sendInterfaceText("", 8240);
            player = ((DuelInterfaceManager)value).player;
            player.packetSender.sendInterfaceText("", 8241);
            int[] integerValues = confirmRuleTextIds;
            int index = 0;
            while (index < 11) {
                value2 = integerValues[index];
                player = ((DuelInterfaceManager)value).player;
                player.packetSender.sendInterfaceText("", value2);
                ++index;
            }
        }
        value = this;
        String duelSession = ((DuelInterfaceManager)value).player.getDuelSession().getStakedItems().size() <= 0 ? "Absolutely nothing!" : "";
        ItemStack[] itemStackArray = DuelSession.toItemArray(((DuelInterfaceManager)value).player.getDuelSession().getStakedItems());
        int length = itemStackArray.length;
        int index2 = 0;
        while (index2 < length) {
            ItemStack itemStack = itemStackArray[index2];
            if (itemStack != null) {
                duelSession = itemStack.getDefinition().isStackable() || itemStack.getDefinition().isNote() ? String.valueOf(duelSession) + itemStack.getDefinition().getName() + " x @cya@" + GameUtil.formatNumber(itemStack.getAmount()) + "\\n" : String.valueOf(duelSession) + itemStack.getDefinition().getName() + "\\n";
            }
            ++index2;
        }
        Player player = ((DuelInterfaceManager)value).player;
        player.packetSender.sendInterfaceText(duelSession, 6516);
        duelSession = ((DuelInterfaceManager)value).player.getDuelSession().getOpponent().getDuelSession().getStakedItems().size() <= 0 ? "Absolutely nothing!" : "";
        itemStackArray = DuelSession.toItemArray(((DuelInterfaceManager)value).player.getDuelSession().getOpponent().getDuelSession().getStakedItems());
        length = itemStackArray.length;
        int index3 = 0;
        while (index3 < length) {
            ItemStack itemStack = itemStackArray[index3];
            if (itemStack != null) {
                duelSession = itemStack.getDefinition().isStackable() || itemStack.getDefinition().isNote() ? String.valueOf(duelSession) + itemStack.getDefinition().getName() + " x @cya@" + GameUtil.formatNumber(itemStack.getAmount()) + "\\n" : String.valueOf(duelSession) + itemStack.getDefinition().getName() + "\\n";
            }
            ++index3;
        }
        Player player2 = ((DuelInterfaceManager)value).player;
        player2.packetSender.sendInterfaceText(duelSession, 6517);
        if (this.player.getDuelSession().getRuleDescriptions().size() == 0) {
            player2 = this.player;
            player2.packetSender.sendInterfaceText("Everything will be allowed!", confirmRuleTextIds[0]);
        } else {
            int index4 = 0;
            while (index4 < this.player.getDuelSession().getRuleDescriptions().size()) {
                player2 = this.player;
                player2.packetSender.sendInterfaceText((String)this.player.getDuelSession().getRuleDescriptions().get(index4), confirmRuleTextIds[index4]);
                ++index4;
            }
        }
        this.refreshAcceptStatus();
    }

    public final void refreshAcceptStatus() {
        if (this.player.getDuelSession().getOpponent() == null || !this.player.getDuelSession().getOpponent().isRegistered() || this.player.getDuelSession().getOpponent().getDuelController() == null) {
            this.player.getDuelController().resetDuel(true);
            return;
        }
        Player player = this.player;
        if (player.interfaceAction == "duel") {
            if (this.player.getDuelSession().getOpponent().getDuelController().isAccepted()) {
                player = this.player;
                player.packetSender.sendInterfaceText("Other player accepted.", 6684);
                return;
            }
            if (this.player.getDuelController().isAccepted()) {
                player = this.player;
                player.packetSender.sendInterfaceText("Waiting for other player...", 6684);
                return;
            }
            player = this.player;
            player.packetSender.sendInterfaceText("", 6684);
            return;
        }
        player = this.player;
        if (player.interfaceAction == "duel2") {
            if (this.player.getDuelSession().getOpponent().getDuelController().isAccepted()) {
                player = this.player;
                player.packetSender.sendInterfaceText("Other player accepted.", 6571);
                return;
            }
            if (this.player.getDuelController().isAccepted()) {
                player = this.player;
                player.packetSender.sendInterfaceText("Waiting for other player...", 6571);
                return;
            }
            player = this.player;
            player.packetSender.sendInterfaceText("", 6571);
        }
    }

    private int countEquipmentItemsToRemove() {
        int index = 0;
        for (Object itemObject : this.player.getDuelSession().getEquipmentToRemove()) {
            ItemStack itemStack = (ItemStack)itemObject;
            if (itemStack.getId() <= 0) continue;
            ++index;
        }
        return index;
    }

    public final boolean hasInventorySpaceForDuel() {
        Player player = this.player.getDuelSession().getOpponent();
        int duelSession = this.player.getDuelSession().getStakedItems().size() + player.getDuelSession().getStakedItems().size() + this.player.getDuelInterfaceManager().countEquipmentItemsToRemove();
        if (duelSession > this.player.getInventoryManager().getContainer().getFreeSlots()) {
            player = this.player;
            player.packetSender.sendGameMessage("You or your opponent doesn't have enough spaces for that.");
            return false;
        }
        duelSession = player.getDuelSession().getStakedItems().size() + this.player.getDuelSession().getStakedItems().size() + player.getDuelInterfaceManager().countEquipmentItemsToRemove();
        if (duelSession > player.getInventoryManager().getContainer().getFreeSlots()) {
            player = this.player;
            player.packetSender.sendGameMessage("You or your opponent doesn't have enough spaces for that.");
            return false;
        }
        return true;
    }

    public final void refreshStakeContainers() {
        if (this.player.getDuelSession().getOpponent() == null || !this.player.getDuelSession().getOpponent().isRegistered()) {
            this.player.getDuelController().resetDuel(true);
            return;
        }
        Player player = this.player;
        player.packetSender.showInterfaceWithInventory(6575, 3321);
        player = this.player;
        player.packetSender.sendItemContainer(3322, this.player.getInventoryManager().getContainer().getRawItems());
        player = this.player;
        player.packetSender.sendItemContainer(6669, DuelSession.toItemArray(this.player.getDuelSession().getStakedItems()));
        player = this.player;
        player.packetSender.sendItemContainer(6670, DuelSession.toItemArray(this.player.getDuelSession().getOpponent().getDuelSession().getStakedItems()));
    }

    public final void toggleRule(int value2, String text2) {
        this.player.getDuelController().setAccepted(false);
        if (this.player.getDuelSession().getEnabledRules()[value2]) {
            this.ruleConfigValue -= ruleConfigMasks[value2];
            this.player.getDuelSession().getEnabledRules()[value2] = false;
            if (text2 != null) {
                this.player.getDuelSession().getRuleDescriptions().remove(text2);
            }
        } else {
            this.ruleConfigValue += ruleConfigMasks[value2];
            this.player.getDuelSession().getEnabledRules()[value2] = true;
            if (text2 != null) {
                this.player.getDuelSession().getRuleDescriptions().add(text2);
            }
        }
        Player player = this.player;
        player.packetSender.sendConfig(286, this.ruleConfigValue);
        this.refreshAcceptStatus();
    }

    public final void resetRules() {
        int index = 0;
        while (index < this.player.getDuelSession().getEnabledRules().length) {
            if (this.player.getDuelSession().getEnabledRules()[index]) {
                this.ruleConfigValue -= ruleConfigMasks[index];
                this.player.getDuelSession().getEnabledRules()[index] = false;
            }
            ++index;
        }
        Player player = this.player;
        player.packetSender.sendConfig(286, this.ruleConfigValue);
        int index2 = 0;
        while (index2 < this.player.getDuelSession().getEnabledRules().length) {
            this.player.getDuelSession().getEnabledRules()[index2] = false;
            ++index2;
        }
    }
}

