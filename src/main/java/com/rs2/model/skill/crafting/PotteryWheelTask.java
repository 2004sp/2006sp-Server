package com.rs2.model.skill.crafting;

import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.PotteryRecipe;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class PotteryWheelTask
extends CycleEvent {
    private int remainingActions;
    private final Player player;
    private final int actionSequence;
    private final PotteryRecipe recipe;
    private final ItemStack productItem;

    public PotteryWheelTask(PotteryRecipe potteryRecipe, int value3, Player player, int actionSequence, ItemStack itemStack) {
        this.recipe = potteryRecipe;
        this.player = player;
        this.actionSequence = actionSequence;
        this.productItem = itemStack;
        this.remainingActions = potteryRecipe.getQuantity() != 0 ? potteryRecipe.getQuantity() : value3;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence) || this.remainingActions == 0 || !this.player.getInventoryManager().getContainer().containsItem(1761)) {
            ((CycleEventContainer)cycleEventContainer).stop();
            return;
        }
        ((CycleEventContainer)cycleEventContainer).setTickDelay(3);
        this.player.getUpdateState().setAnimation(894);
        this.player.packetSender.sendGameMessage("You make the soft clay into " + GameplayHelper.getIndefiniteArticle(new ItemStack(this.recipe.getUnfiredItemId()).getDefinition().getName().toLowerCase()) + " " + new ItemStack(this.recipe.getUnfiredItemId()).getDefinition().getName().toLowerCase() + ".");
        this.player.getInventoryManager().removeItem(new ItemStack(1761));
        this.player.getInventoryManager().addItem(this.productItem);
        this.player.getSkillManager().addExperience(12, this.recipe.getWheelExperience());
        --this.remainingActions;
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

