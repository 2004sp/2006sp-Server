package com.rs2.model.skill.herblore;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FinishedPotionTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final ItemStack secondaryIngredient;
    private final double unfinishedPotionItemId;
    private final double finishedPotionItemId;
    private final double experience;

    public FinishedPotionTask(Player player, int actionSequence, ItemStack itemStack, double unfinishedPotionItemId, double finishedPotionItemId, double experience) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.secondaryIngredient = itemStack;
        this.unfinishedPotionItemId = unfinishedPotionItemId;
        this.finishedPotionItemId = finishedPotionItemId;
        this.experience = experience;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        if (this.player.getInventoryManager().containsItemStack(this.secondaryIngredient) && this.player.getInventoryManager().containsItemStack(new ItemStack((int)this.unfinishedPotionItemId))) {
            this.player.getInventoryManager().removeItem(this.secondaryIngredient);
            this.player.getInventoryManager().removeItem(new ItemStack((int)this.unfinishedPotionItemId));
            this.player.getInventoryManager().addItem(new ItemStack((int)this.finishedPotionItemId));
        }
        this.player.getSkillManager().addExperience(15, this.experience);
        FinishedPotionTask finishedPotionTask = this;
        finishedPotionTask.player.rollActionReward();
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

