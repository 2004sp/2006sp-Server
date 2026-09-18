package com.rs2.model.combat.requirement;

import com.rs2.model.Entity;
import com.rs2.model.combat.requirement.CombatCostRequirement;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public abstract class EquipmentItemRequirement
extends CombatCostRequirement {
    private int equipmentSlot;
    private int itemId;
    private int amount;
    private boolean consumeOnUse;

    public EquipmentItemRequirement(int equipmentSlot, int itemId, int amount, boolean consumeOnUse) {
        this.equipmentSlot = equipmentSlot;
        this.itemId = itemId;
        this.amount = amount;
        this.consumeOnUse = consumeOnUse;
    }

    @Override
    public final void consume(Entity entity) {
        if (!this.consumeOnUse || !entity.isPlayer()) {
            return;
        }
        entity = (Player)entity;
        ((Player)entity).getEquipmentManager().consumeSlotItemAmount(this.equipmentSlot, this.amount);
    }

    @Override
    final boolean isSatisfiedBy(Entity entity) {
        if (!entity.isPlayer()) {
            return true;
        }
        Player player = (Player)entity;
        ItemStack itemStack = player.getEquipmentManager().getContainer().getItems()[this.equipmentSlot];
        if (itemStack == null) {
            return false;
        }
        return itemStack.getId() == this.itemId && itemStack.getAmount() >= this.amount;
    }
}

