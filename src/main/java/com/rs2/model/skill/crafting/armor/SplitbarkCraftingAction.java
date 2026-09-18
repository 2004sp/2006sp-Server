package com.rs2.model.skill.crafting.armor;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public abstract class SplitbarkCraftingAction {
    private Player player;
    private int productItemId;
    private int recipeQuantity;
    private int fineClothAmount;
    private int barkAmount;
    private int coinAmount;
    private int requestedQuantity;

    protected SplitbarkCraftingAction(Player player, int productItemId, int recipeQuantity, int requestedQuantity, int fineClothAmount, int barkAmount, int coinAmount) {
        this.player = player;
        this.productItemId = productItemId;
        this.requestedQuantity = requestedQuantity;
        this.recipeQuantity = recipeQuantity;
        this.fineClothAmount = fineClothAmount;
        this.barkAmount = barkAmount;
        this.coinAmount = coinAmount;
    }

    public final boolean startCrafting() {
        int value;
        int value2 = value = this.recipeQuantity != 0 ? this.recipeQuantity : this.requestedQuantity;
        if (!this.player.getInventoryManager().containsItemStack(new ItemStack(3470, value * this.fineClothAmount))) {
            this.player.getDialogueManager().showOneLineStatement("You need " + value * this.fineClothAmount + " " + new ItemStack(3470).getDefinition().getName().toLowerCase() + " to do this.");
            return true;
        }
        if (!this.player.getInventoryManager().containsItemStack(new ItemStack(3239, value * this.barkAmount))) {
            this.player.getDialogueManager().showOneLineStatement("You need " + value * this.barkAmount + " " + new ItemStack(3239).getDefinition().getName().toLowerCase() + " to do this.");
            return true;
        }
        if (!this.player.getInventoryManager().containsItemStack(new ItemStack(995, value * this.coinAmount))) {
            this.player.getDialogueManager().showOneLineStatement("You need " + value * this.coinAmount + " " + new ItemStack(995).getDefinition().getName().toLowerCase() + " to do this.");
            return true;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(3470, value * this.fineClothAmount));
        this.player.getInventoryManager().removeItem(new ItemStack(3239, value * this.barkAmount));
        this.player.getInventoryManager().removeItem(new ItemStack(995, value * this.coinAmount));
        this.player.getInventoryManager().addItem(new ItemStack(this.productItemId, value));
        DialogueManager.continueDialogue(this.player, 1263, 6, 0);
        Player player = this.player;
        player.packetSender.sendInterfaceText("What would you like to make?", 8966);
        return true;
    }
}

