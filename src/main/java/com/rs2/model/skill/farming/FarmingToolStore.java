package com.rs2.model.skill.farming;

import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FarmingToolDefinition;

public final class FarmingToolStore {
    private Player player;
    public int[] storedAmounts = new int[18];
    private ItemStack[] storedToolDisplayItems = new ItemStack[]{new ItemStack(5341), new ItemStack(5343), new ItemStack(952), new ItemStack(5329), new ItemStack(5331), new ItemStack(5325)};
    private ItemStack[] storedCompostDisplayItems = new ItemStack[]{new ItemStack(1925), new ItemStack(6032), new ItemStack(6034)};
    private ItemStack[] inventoryToolDisplayItems = new ItemStack[]{new ItemStack(5341), new ItemStack(5343), new ItemStack(952), new ItemStack(5329), new ItemStack(5331), new ItemStack(5325)};
    private ItemStack[] inventoryCompostDisplayItems = new ItemStack[]{new ItemStack(1925), new ItemStack(6032), new ItemStack(6034)};
    private static int[] noteableProduceItemIds = new int[]{199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 225, 239, 247, 249, 251, 253, 255, 257, 259, 261, 263, 265, 267, 269, 2485, 2998, 3000, 3049, 3051, 3261, 2481, 592, 1965, 1967, 6004, 5980, 5976, 1955, 1963, 2108, 5972, 2114, 754, 2126, 248, 1951, 240, 2367, 1942, 1957, 1965, 1982, 5986, 5504, 5982, 6006, 5994, 5996, 5931, 5998, 6000, 6002, 6016, 6055};

    public FarmingToolStore(Player player) {
        this.player = player;
    }

    public final void open() {
        Player player = this.player;
        player.packetSender.showInterface(15614);
        player = this.player;
        player.packetSender.sendItemContainer(15682, this.storedToolDisplayItems);
        player = this.player;
        player.packetSender.sendItemContainer(15683, this.storedCompostDisplayItems);
        player = this.player;
        player.packetSender.setSidebarInterface(3, 15593);
        player = this.player;
        player.packetSender.sendItemContainer(15594, this.inventoryToolDisplayItems);
        player = this.player;
        player.packetSender.sendItemContainer(15595, this.inventoryCompostDisplayItems);
        this.refreshInterface();
    }

    private void refreshInventoryToolDisplayItems() {
        int value = 5340;
        while (!this.player.getInventoryManager().getContainer().containsItem(value) && value >= 5330) {
            --value;
        }
        if (value == 5330) {
            return;
        }
        this.inventoryToolDisplayItems[4] = new ItemStack(value);
        if (this.player.getInventoryManager().getContainer().containsItem(7409)) {
            this.inventoryToolDisplayItems[3] = new ItemStack(7409);
            return;
        }
        this.inventoryToolDisplayItems[3] = new ItemStack(5329);
    }

    private void refreshStoredToolDisplayItems() {
        int index = 0;
        int index2 = 0;
        int value = 5;
        while (value <= 13) {
            FarmingToolDefinition farmingToolDefinition = FarmingToolDefinition.forStorageIndex(value);
            if (this.player.getInventoryManager().getContainer().containsItem(farmingToolDefinition.getItemId())) {
                ++index2;
            }
            if (this.storedAmounts[value] == 1) {
                ++index;
            }
            ++value;
        }
        if (index == 0) {
            this.storedToolDisplayItems[4] = new ItemStack(5331);
        }
        if (index2 == 0) {
            this.inventoryToolDisplayItems[4] = new ItemStack(5331);
        }
    }

    private void refreshInterface() {
        int index = 0;
        this.player.actionSucceeded = false;
        int index2 = 0;
        while (index2 < this.storedAmounts.length) {
            FarmingToolDefinition farmingToolDefinition = FarmingToolDefinition.forStorageIndex(index2);
            if (farmingToolDefinition == null) {
                return;
            }
            index += farmingToolDefinition.getConfigValue() * this.storedAmounts[index2];
            int value = index2;
            int inventoryManager = this.player.getInventoryManager().getItemAmount(farmingToolDefinition.getItemId());
            FarmingToolDefinition farmingToolDefinition2 = farmingToolDefinition;
            FarmingToolStore farmingToolStore = this;
            if (inventoryManager > 0) {
                if (value >= 5 && value <= 13) {
                    farmingToolStore.player.actionSucceeded = true;
                }
                Player player = farmingToolStore.player;
                player.packetSender.sendInterfaceText("@gre@" + farmingToolDefinition2.getDisplayName(), farmingToolDefinition2.getNameTextInterfaceId());
                player = farmingToolStore.player;
                player.packetSender.sendInterfaceText("@gre@" + inventoryManager, farmingToolDefinition2.getAmountTextInterfaceId());
            } else if (!(value >= 5 && value <= 13 && farmingToolStore.player.actionSucceeded || (value == 3 || value == 4) && (farmingToolStore.player.getInventoryManager().getContainer().containsItem(7409) || farmingToolStore.player.getInventoryManager().getContainer().containsItem(5329)))) {
                Player player = farmingToolStore.player;
                player.packetSender.sendInterfaceText(farmingToolDefinition2.getDisplayName(), farmingToolDefinition2.getNameTextInterfaceId());
                player = farmingToolStore.player;
                player.packetSender.sendInterfaceText(String.valueOf(inventoryManager), farmingToolDefinition2.getAmountTextInterfaceId());
            }
            if (farmingToolDefinition.getItemId() != 5332 && farmingToolDefinition.getItemId() >= 5331 && farmingToolDefinition.getItemId() <= 5340 && this.storedAmounts[index2] == 1) {
                this.storedToolDisplayItems[4] = new ItemStack(farmingToolDefinition.getItemId());
            }
            if (farmingToolDefinition.getItemId() == 7409 && this.storedAmounts[index2] == 1) {
                this.storedToolDisplayItems[3] = new ItemStack(7409);
            }
            ++index2;
        }
        this.refreshInventoryToolDisplayItems();
        this.refreshStoredToolDisplayItems();
        Player player = this.player;
        player.packetSender.sendConfig(615, index);
        player = this.player;
        player.packetSender.sendItemContainer(15682, this.storedToolDisplayItems);
        player = this.player;
        player.packetSender.sendItemContainer(15594, this.inventoryToolDisplayItems);
    }

    public final void depositItem(int itemId, int value4) {
        int value2;
        FarmingToolDefinition farmingToolDefinition;
        depositItemControlExit1: {
            depositItemControlExit2: {
                farmingToolDefinition = FarmingToolDefinition.forItemId(itemId);
                if (farmingToolDefinition == null) {
                    return;
                }
                int storageIndex = this.storedAmounts[farmingToolDefinition.getStorageIndex()];
                value2 = value4;
                if (!this.player.getInventoryManager().getContainer().containsItem(itemId)) {
                    return;
                }
                if (farmingToolDefinition.getMaxStoredAmount() == storageIndex || (itemId == 7409 || itemId == 5329) && (this.storedAmounts[3] == 1 || this.storedAmounts[4] == 1)) break depositItemControlExit2;
                FarmingToolStore farmingToolStore = this;
                int index = 0;
                int value3 = 5;
                while (value3 <= 13) {
                    if (farmingToolStore.storedAmounts[value3] == 1) {
                        ++index;
                    }
                    ++value3;
                }
                if (!(index != 0) || farmingToolDefinition.getItemId() == 5332 || itemId > 5340 || itemId < 5331) break depositItemControlExit1;
            }
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't store any more of those.");
            return;
        }
        if (this.player.getInventoryManager().getContainer().getItemAmount(itemId) <= 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You aren't carrying any of those.");
            return;
        }
        if (this.player.getInventoryManager().getContainer().getItemAmount(itemId) < value4) {
            value2 = this.player.getInventoryManager().getContainer().getItemAmount(itemId);
        }
        value4 = value2;
        if (this.storedAmounts[farmingToolDefinition.getStorageIndex()] + value4 > farmingToolDefinition.getMaxStoredAmount()) {
            value2 -= this.storedAmounts[farmingToolDefinition.getStorageIndex()] + value4 - farmingToolDefinition.getMaxStoredAmount();
        }
        this.player.getInventoryManager().removeItem(new ItemStack(itemId, value2));
        int storageIndex2 = farmingToolDefinition.getStorageIndex();
        this.storedAmounts[storageIndex2] = this.storedAmounts[storageIndex2] + value2;
        this.refreshInterface();
    }

    public final void withdrawItem(int itemId, int value2) {
        FarmingToolDefinition farmingToolDefinition = FarmingToolDefinition.forItemId(itemId);
        if (farmingToolDefinition == null) {
            return;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            return;
        }
        if (this.storedAmounts[farmingToolDefinition.getStorageIndex()] <= 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You haven't got any of those stored in here.");
            return;
        }
        if (value2 > this.storedAmounts[farmingToolDefinition.getStorageIndex()]) {
            value2 = this.storedAmounts[farmingToolDefinition.getStorageIndex()];
        }
        if (value2 > this.player.getInventoryManager().getContainer().getFreeSlots()) {
            value2 = this.player.getInventoryManager().getContainer().getFreeSlots();
        }
        int storageIndex = farmingToolDefinition.getStorageIndex();
        this.storedAmounts[storageIndex] = this.storedAmounts[storageIndex] - value2;
        this.player.getInventoryManager().addItem(new ItemStack(itemId, value2));
        this.refreshInterface();
    }

    public final boolean noteProduce(Npc npc, int value3) {
        this.player.getDialogueManager().setDialogueNpcId(3021);
        if (new ItemStack(value3).getDefinition().isNote()) {
            this.player.getDialogueManager().showNpcOneLineDialogue("That IS a banknote!", 600);
            this.player.getDialogueManager().finishDialogue();
            return true;
        }
        int[] integerValues = noteableProduceItemIds;
        int index = 0;
        while (index < 65) {
            int value2 = integerValues[index];
            if (value3 == value2 && (npc.getPosition().getX() < 3044 || npc.getPosition().getX() > 3064 || npc.getPosition().getY() < 3300 || npc.getPosition().getY() > 3313 || value3 != 1965)) {
                int inventoryManager = this.player.getInventoryManager().getItemAmount(value3);
                this.player.getInventoryManager().removeItem(new ItemStack(value3, inventoryManager));
                this.player.getInventoryManager().addItem(new ItemStack(value3 + 1, inventoryManager));
                this.player.getDialogueManager().showOneLineStatement("The tool leprechaun notes those items for you.");
                this.player.getDialogueManager().finishDialogue();
                return true;
            }
            ++index;
        }
        this.player.getDialogueManager().showNpcOneLineDialogue("Nay, I've got no banknotes to exchange for that item.", 588);
        this.player.getDialogueManager().finishDialogue();
        return true;
    }
}

