package com.rs2.model.skill.crafting;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.DramenStaffRecipe;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class DramenStaffCarvingTask
extends CycleEvent {
    private int remainingActions;
    private final Player player;
    private final int actionSequence;
    private final DramenStaffRecipe recipe;

    public DramenStaffCarvingTask(DramenStaffRecipe dramenStaffRecipe, int value3, Player player, int actionSequence) {
        this.recipe = dramenStaffRecipe;
        this.player = player;
        this.actionSequence = actionSequence;
        this.remainingActions = dramenStaffRecipe.getQuantity() != 0 ? dramenStaffRecipe.getQuantity() : value3;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence) || this.remainingActions == 0 || this.player.getInventoryManager().getItemAmount(this.recipe.getIngredientItemId()) < this.recipe.getIngredientAmount()) {
            ((CycleEventContainer)cycleEventContainer).stop();
            return;
        }
        this.player.getUpdateState().setAnimation(1248);
        this.player.packetSender.sendGameMessage("You carve the branch into a staff.");
        this.player.getInventoryManager().removeItem(new ItemStack(this.recipe.getIngredientItemId(), this.recipe.getIngredientAmount()));
        this.player.getInventoryManager().addItem(new ItemStack(this.recipe.getProductItemId()));
        this.player.getSkillManager().addExperience(12, this.recipe.getExperience());
        --this.remainingActions;
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

