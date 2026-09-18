package com.rs2.model.combat.requirement;

import com.rs2.model.Entity;
import com.rs2.model.combat.requirement.CombatCostRequirement;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import java.util.ArrayList;

public abstract class InventoryItemRequirement
extends CombatCostRequirement {
    private int itemId = 1;
    private int amount = 1;
    private ArrayList requiredItems = new ArrayList();

    public InventoryItemRequirement(int value3, int value22) {
    }

    public final void setRequiredItems(ArrayList arrayList) {
        this.requiredItems = arrayList;
    }

    @Override
    public void consume(Entity entity) {
        if (!entity.isPlayer()) {
            return;
        }
        entity = (Player)entity;
        if (this.requiredItems.size() == 0) {
            ((Player)entity).getInventoryManager().removeItem(new ItemStack(this.itemId, this.amount));
            return;
        }
        for (Object itemObject : this.requiredItems) {
            ItemStack itemStack = (ItemStack)itemObject;
            ((Player)entity).getInventoryManager().removeItem(itemStack);
        }
    }

    @Override
    boolean isSatisfiedBy(Entity entity) {
        if (!entity.isPlayer()) {
            return true;
        }
        entity = (Player)entity;
        if (this.requiredItems.size() == 0) {
            return ((Player)entity).getInventoryManager().getItemAmount(this.itemId) >= this.amount;
        }
        for (Object itemObject : this.requiredItems) {
            ItemStack itemStack = (ItemStack)itemObject;
            if (((Player)entity).getInventoryManager().getItemAmount(itemStack.getId()) >= itemStack.getAmount()) continue;
            return false;
        }
        return true;
    }
}

