package com.rs2.model.skill.crafting.armor;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.CraftedArmorTask;
import com.rs2.model.task.CycleEventHandler;

public abstract class CraftedArmorAction {
    protected Player player;
    protected int materialItemId;
    protected int materialAmount;
    protected int productItemId;
    protected int recipeQuantity;
    protected int requestedQuantity;
    private int requiredLevel;
    protected double experience;

    protected CraftedArmorAction(Player player, int materialItemId, int materialAmount, int productItemId, int recipeQuantity, int requestedQuantity, int requiredLevel, double experience) {
        this.player = player;
        this.materialItemId = materialItemId;
        this.materialAmount = materialAmount;
        this.productItemId = productItemId;
        this.requestedQuantity = requestedQuantity;
        this.recipeQuantity = recipeQuantity;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final boolean startCrafting() {
        Object value = this.player;
        ((Player)value).packetSender.closeInterfaces();
        if (!ServerSettings.craftingEnabled) {
            value = this.player;
            ((Player)value).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(1733)) {
            this.player.getDialogueManager().showOneLineStatement("You need a needle to do this.");
            return true;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(1734)) {
            this.player.getDialogueManager().showOneLineStatement("You need thread to do this.");
            return true;
        }
        if (!this.player.getInventoryManager().containsItemStack(new ItemStack(this.materialItemId, this.materialAmount))) {
            this.player.getDialogueManager().showOneLineStatement("You need " + this.materialAmount + " " + new ItemStack(this.materialItemId).getDefinition().getName().toLowerCase() + " to do this.");
            return true;
        }
        if (this.player.getSkillManager().getCurrentLevels()[12] < this.requiredLevel) {
            this.player.getDialogueManager().showOneLineStatement("You need a crafting level of " + this.requiredLevel + " to make this.");
            return true;
        }
        value = new ItemStack(this.productItemId, 1);
        if (((ItemStack)value).getDefinition().isMembersOnly()) {
            if (!this.player.isMember()) {
                this.player.packetSender.sendGameMessage("You need a members account to access members content.");
                return true;
            }
            if (ServerSettings.freeToPlayWorld) {
                this.player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                return true;
            }
        }
        int value2 = this.player.nextActionSequence();
        this.player.setActiveCycleEvent(new CraftedArmorTask(this, value2));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 1);
        return true;
    }
}

