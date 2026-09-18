package com.rs2.model.player;

import com.rs2.model.combat.CombatManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemStack;

public final class InventoryManager {
    private Player player;
    private ItemContainer container = new ItemContainer(ItemContainerType.a, 28);

    public InventoryManager(Player player) {
        this.player = player;
    }

    public final void refresh() {
        ItemStack[] itemStackArray = this.container.getRawItems();
        this.player.packetSender.sendItemContainer(3214, itemStackArray);
    }

    public final void sendToInterface(int interfaceId) {
        ItemStack[] itemStackArray = this.container.getRawItems();
        this.player.packetSender.sendItemContainer(interfaceId, itemStackArray);
    }

    public final int addItemPartial(ItemStack itemStack) {
        if (!this.hasSpaceFor(itemStack)) {
            this.sendNoSpaceMessage();
            return 0;
        }
        int amount = itemStack.getAmount();
        if (!itemStack.getDefinition().isStackable() && amount > this.container.getFreeSlots()) {
            amount = this.container.getFreeSlots();
        }
        this.container.add(new ItemStack(itemStack.getId(), amount, itemStack.getMetadata()), -1);
        this.refresh();
        this.player.getEquipmentManager().refreshCarriedValue();
        return amount;
    }

    public final void addOrDropItem(ItemStack itemStack) {
        if (!this.addItem(itemStack)) {
            if (!itemStack.getDefinition().isStackable()) {
                int index = 0;
                while (index < itemStack.getAmount()) {
                    GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(itemStack.getId(), 1), this.player));
                    ++index;
                }
                return;
            }
            GroundItemManager.getInstance().spawn(new GroundItem(itemStack, this.player));
        }
    }

    public final boolean addItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.isValid()) {
            return false;
        }
        if (!this.hasSpaceFor(itemStack)) {
            this.sendNoSpaceMessage();
            if (this.player.botEnabled) {
                if (this.player.botCombatState != null && this.player.botCombatState.equals("loot arrows")) {
                    this.player.botCombatState = null;
                    if (this.player.botLootResumeTarget != null && !this.player.botLootResumeTarget.isDead()) {
                        CombatManager.startCombat(this.player, this.player.botLootResumeTarget);
                    } else if (this.player.currentBotTask != null) {
                        this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
                    }
                } else if (this.player.botCombatState != null && this.player.botCombatState.equals("loot items")) {
                    this.player.botCombatState = null;
                    this.player.botLootGroundItems.clear();
                    this.player.botLootPickupTargets.clear();
                }
            }
            return false;
        }
        int amount = itemStack.getAmount();
        if (!itemStack.getDefinition().isStackable() && amount > this.container.getFreeSlots()) {
            amount = this.container.getFreeSlots();
            int amount2 = itemStack.getAmount() - amount;
            int index = 0;
            while (index < amount2) {
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(itemStack.getId(), 1, itemStack.getMetadata()), this.player));
                ++index;
            }
        }
        this.container.add(new ItemStack(itemStack.getId(), amount, itemStack.getMetadata()), -1);
        this.refresh();
        this.player.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final boolean addItemUpToFreeSlots(ItemStack itemStack) {
        if (!itemStack.isValid()) {
            return false;
        }
        if (!this.hasSpaceFor(itemStack)) {
            this.sendNoSpaceMessage();
            return false;
        }
        int amount = itemStack.getAmount();
        if (amount >= this.container.getFreeSlots()) {
            amount = this.container.getFreeSlots();
        }
        this.container.add(new ItemStack(itemStack.getId(), amount, itemStack.getMetadata()), -1);
        this.refresh();
        this.player.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final boolean canAddItem(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (!this.hasSpaceFor(itemStack)) {
            this.sendNoSpaceMessage();
            return false;
        }
        return true;
    }

    public final boolean hasSpaceFor(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        boolean enabled = true;
        if (itemStack.getDefinition().isStackable() && this.container.containsItem(itemStack.getId())) {
            enabled = false;
        }
        return !(enabled ? this.container.getFreeSlots() <= 0 && !this.container.canAdd(itemStack) : !this.container.canAdd(itemStack));
    }

    public final boolean containsItemInInventoryOrBank(int itemId) {
        ItemStack itemStack;
        ItemStack[] itemStackArray = this.container.getItems();
        int length = itemStackArray.length;
        int index = 0;
        while (index < length) {
            itemStack = itemStackArray[index];
            if (itemStack != null && itemStack.getId() == itemId) {
                return true;
            }
            ++index;
        }
        itemStackArray = this.player.getBankContainer().getItems();
        length = itemStackArray.length;
        index = 0;
        while (index < length) {
            itemStack = itemStackArray[index];
            if (itemStack != null && itemStack.getId() == itemId) {
                return true;
            }
            ++index;
        }
        return false;
    }

    public final void setItemInSlot(ItemStack itemStack, int itemId) {
        if (itemStack == null || !itemStack.isValid()) {
            return;
        }
        if (itemStack.getDefinition().isStackable() && this.container.indexOfItem(itemStack.getId()) >= 0) {
            itemId = this.container.indexOfItem(itemStack.getId());
            ItemStack itemStack2 = this.container.getItemAt(itemId);
            this.container.setItem(itemId, new ItemStack(itemStack.getId(), itemStack.getAmount() + itemStack2.getAmount(), itemStack.getMetadata()));
            this.refresh();
            return;
        }
        if (this.container.getFirstFreeSlot() == -1) {
            this.sendNoSpaceMessage();
            return;
        }
        this.container.setItem(itemId, itemStack);
        this.refresh();
    }

    public final boolean removeItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.isValid()) {
            return false;
        }
        if (!this.containsItemStack(itemStack)) {
            return false;
        }
        this.container.remove(itemStack);
        this.refresh();
        this.player.getEquipmentManager().refreshCarriedValue();
        return true;
    }

    public final void replaceItem(ItemStack itemStack, ItemStack itemStack2) {
        this.removeItem(itemStack);
        this.addItem(itemStack2);
    }

    public final boolean removeItemAtSlot(int itemId) {
        if (itemId == -1) {
            return false;
        }
        if (this.container.getItemAt(itemId) == null) {
            return false;
        }
        this.container.removeFromSlot(this.container.getItemAt(itemId), itemId);
        return true;
    }

    public final boolean removeItemFromSlot(ItemStack itemStack, int itemId) {
        if (itemStack == null || itemStack.getId() == -1) {
            return false;
        }
        if (itemId == -1) {
            return false;
        }
        if (this.container.getItemAt(itemId) == null) {
            return false;
        }
        if (!this.container.containsItem(itemStack.getId())) {
            return false;
        }
        int value = this.container.removeFromSlot(itemStack, itemId);
        this.refresh();
        return value > 0;
    }

    public final void swapSlots(int slot, int value2) {
        this.container.swapSlots(slot, value2);
        this.refresh();
    }

    public final ItemContainer getContainer() {
        return this.container;
    }

    public final boolean containsItemStack(ItemStack itemStack) {
        return this.containsItemAmount(itemStack.getId(), itemStack.getAmount());
    }

    public final boolean containsItem(int itemId) {
        return this.container.indexOfItem(itemId) >= 0;
    }

    public final boolean containsItemAmount(int itemId, int value2) {
        if (!this.containsItem(itemId)) {
            return false;
        }
        return this.container.getItemAmount(itemId) >= value2;
    }

    public final int getItemAmount(int itemId) {
        return this.player.getInventoryManager().container.getItemAmount(itemId);
    }

    private void sendNoSpaceMessage() {
        this.player.packetSender.sendGameMessage("Not enough space in your inventory.");
        this.player.packetSender.sendSoundEffect(1878, 1, 0);
    }
}
