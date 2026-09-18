package com.rs2.model.skill.crafting;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.GlassblowingRecipe;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class GlassblowingTask
extends CycleEvent {
    private int remainingActions;
    private final Player player;
    private final int actionSequence;
    private final GlassblowingRecipe recipe;

    public GlassblowingTask(GlassblowingRecipe glassblowingRecipe, int value3, Player player, int actionSequence) {
        this.recipe = glassblowingRecipe;
        this.player = player;
        this.actionSequence = actionSequence;
        this.remainingActions = glassblowingRecipe.getQuantity() != 0 ? glassblowingRecipe.getQuantity() : value3;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence) || this.remainingActions == 0 || !this.player.getInventoryManager().getContainer().containsItem(1775)) {
            ((CycleEventContainer)cycleEventContainer).stop();
            return;
        }
        ((CycleEventContainer)cycleEventContainer).setTickDelay(3);
        this.player.getUpdateState().setAnimation(884);
        this.player.packetSender.sendGameMessage("You make the molten glass into a " + new ItemStack(this.recipe.getProductItemId()).getDefinition().getName() + ".");
        this.player.getInventoryManager().removeItem(new ItemStack(1775));
        this.player.getInventoryManager().addItem(new ItemStack(this.recipe.getProductItemId()));
        this.player.getSkillManager().addExperience(12, this.recipe.getExperience());
        --this.remainingActions;
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

