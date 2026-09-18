package com.rs2.model.item;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.skill.farming.FarmingPatchUtils;

public class ItemStack {
    private int id;
    private int amount;
    private int metadata;

    public ItemStack(int value2) {
        this(value2, 1);
    }

    public final void setAmount(int amount) {
        this.amount = amount;
    }

    public final void setMetadata(int metadata) {
        this.metadata = metadata;
    }

    public ItemStack(int id, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }
        this.id = id;
        this.amount = amount;
        this.metadata = -1;
        int[] integerValues = FarmingPatchUtils.wateredSeedlingItemIds;
        int index = 0;
        while (index < 14) {
            amount = integerValues[index];
            if (amount == id) {
                this.metadata = 5;
            }
            ++index;
        }
        if (id == 1995) {
            this.metadata = 1;
        }
        if (id >= 5510 && id <= 5515 || id == 5519) {
            this.metadata = 0;
        }
    }

    public ItemStack(int id, int amount, int metadata) {
        if (amount < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }
        this.id = id;
        this.amount = amount;
        this.metadata = metadata;
    }

    public final int getId() {
        return this.id;
    }

    public final int getAmount() {
        return this.amount;
    }

    public final int getMetadata() {
        return this.metadata;
    }

    public final ItemDefinition getDefinition() {
        return ItemDefinition.forId(this.id);
    }

    public String toString() {
        return String.valueOf(ItemStack.class.getName()) + " [id=" + this.id + ", count=" + this.amount + "]";
    }

    public final boolean isValid() {
        ItemStack itemStack = this;
        if (itemStack.id >= 0) {
            itemStack = this;
            if (itemStack.id <= 11883) {
                itemStack = this;
                if (itemStack.amount > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isEquippable() {
        if (this.isValid()) {
            ItemStack itemStack = this;
            if (ItemDefinition.forId(itemStack.id).getEquipmentSlot() != -1) {
                return true;
            }
        }
        return false;
    }
}

