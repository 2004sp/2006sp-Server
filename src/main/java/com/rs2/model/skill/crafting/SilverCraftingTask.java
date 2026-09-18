package com.rs2.model.skill.crafting;

import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.SilverCraftingRecipe;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SilverCraftingTask
extends CycleEvent {
    private int remainingActions;
    private final Player player;
    private final int actionSequence;
    private final SilverCraftingRecipe recipe;
    private final ItemStack productItem;

    public SilverCraftingTask(SilverCraftingRecipe silverCraftingRecipe, int value3, Player player, int actionSequence, ItemStack itemStack) {
        this.recipe = silverCraftingRecipe;
        this.player = player;
        this.actionSequence = actionSequence;
        this.productItem = itemStack;
        this.remainingActions = silverCraftingRecipe.getQuantity() != 0 ? silverCraftingRecipe.getQuantity() : value3;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence) || this.remainingActions == 0 || !this.player.getInventoryManager().getContainer().containsItem(2355)) {
            ((CycleEventContainer)cycleEventContainer).stop();
            return;
        }
        ((CycleEventContainer)cycleEventContainer).setTickDelay(3);
        this.player.getUpdateState().setAnimation(899);
        this.player.packetSender.sendSoundEffect(469, 1, 0);
        this.player.packetSender.sendGameMessage("You make the silver bar into " + GameplayHelper.getIndefiniteArticle(new ItemStack(this.recipe.getProductItemId()).getDefinition().getName().toLowerCase()) + " " + new ItemStack(this.recipe.getProductItemId()).getDefinition().getName().toLowerCase() + ".");
        this.player.getInventoryManager().removeItem(new ItemStack(2355));
        this.player.getInventoryManager().addItem(this.productItem);
        this.player.getSkillManager().addExperience(12, this.recipe.getExperience());
        --this.remainingActions;
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

