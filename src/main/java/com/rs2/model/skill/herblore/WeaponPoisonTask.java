package com.rs2.model.skill.herblore;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.herblore.PoisonedWeaponDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class WeaponPoisonTask
extends CycleEvent {
    private int outputItemId = 0;
    private final int poisonTier;
    private final PoisonedWeaponDefinition weaponDefinition;
    private final Player player;
    private final int weaponItemId;

    public WeaponPoisonTask(int poisonTier, PoisonedWeaponDefinition poisonedWeaponDefinition, Player player, int weaponItemId) {
        this.poisonTier = poisonTier;
        this.weaponDefinition = poisonedWeaponDefinition;
        this.player = player;
        this.weaponItemId = weaponItemId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        int value;
        switch (this.poisonTier) {
            case 1: {
                this.outputItemId = this.weaponDefinition.getPoisonedItemId();
                this.player.getInventoryManager().removeItem(new ItemStack(187));
                this.player.getInventoryManager().addItem(new ItemStack(229));
                break;
            }
            case 2: {
                this.outputItemId = this.weaponDefinition.getPoisonPlusItemId();
                this.player.getInventoryManager().removeItem(new ItemStack(5937));
                this.player.getInventoryManager().addItem(new ItemStack(229));
                break;
            }
            case 3: {
                this.outputItemId = this.weaponDefinition.getPoisonPlusPlusItemId();
                this.player.getInventoryManager().removeItem(new ItemStack(5940));
                this.player.getInventoryManager().addItem(new ItemStack(229));
            }
        }
        Object value2 = new ItemStack(this.weaponItemId);
        int inventoryManager = value = this.player.getInventoryManager().getItemAmount(this.weaponItemId) < 15 ? this.player.getInventoryManager().getItemAmount(this.weaponItemId) : 15;
        if (!((ItemStack)value2).getDefinition().isStackable()) {
            value = 1;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(this.weaponItemId, value));
        this.player.getInventoryManager().addItem(new ItemStack(this.outputItemId, value));
        value2 = this.player;
        ((Player)value2).packetSender.sendGameMessage("You imbue the " + new ItemStack(this.weaponItemId).getDefinition().getName().toLowerCase() + (this.player.getInventoryManager().getItemAmount(this.weaponItemId) > 1 && new ItemStack(this.weaponItemId).getDefinition().isStackable() ? "s" : "") + " with poison");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

