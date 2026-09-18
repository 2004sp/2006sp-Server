package com.rs2.model.skill.cooking;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.cooking.DairyChurnRecipe;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class DairyChurnTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final DairyChurnRecipe recipe;

    public DairyChurnTask(Player player, int actionSequence, DairyChurnRecipe dairyChurnRecipe) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.recipe = dairyChurnRecipe;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            this.player.setSelectedSkillItemId(0);
            cycleEventContainer.stop();
            return;
        }
        int initialValue = -1;
        int index = 0;
        while (index <= this.recipe.getIngredientItemIds().length - 1) {
            int ingredientItemIds = this.recipe.getIngredientItemIds()[index];
            if (this.player.getInventoryManager().getContainer().containsItem(ingredientItemIds)) {
                initialValue = ingredientItemIds;
                break;
            }
            ++index;
        }
        cycleEventContainer.setTickDelay(5);
        if (initialValue == -1) {
            this.player.getDialogueManager().showOneLineStatement("You don't have the required items to use the churn.");
            cycleEventContainer.stop();
            return;
        }
        this.player.getUpdateState().setAnimation(894);
        Player player = this.player;
        player.packetSender.sendGameMessage("You make a " + ItemDefinition.forId(this.recipe.getProductItemId()).getName().toLowerCase() + ".");
        this.player.getInventoryManager().removeItem(new ItemStack(initialValue));
        this.player.getInventoryManager().addItem(new ItemStack(this.recipe.getProductItemId()));
        if (initialValue == 1927) {
            this.player.getInventoryManager().addItem(new ItemStack(1925));
        }
        this.player.getSkillManager().addExperience(7, this.recipe.getExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

