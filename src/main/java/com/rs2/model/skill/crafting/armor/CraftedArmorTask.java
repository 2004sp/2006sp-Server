package com.rs2.model.skill.crafting.armor;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.armor.CraftedArmorAction;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class CraftedArmorTask
extends CycleEvent {
    private int remainingActions;
    private CraftedArmorAction action;
    private final int actionSequence;

    public CraftedArmorTask(CraftedArmorAction craftedArmorAction, int actionSequence) {
        this.action = craftedArmorAction;
        this.actionSequence = actionSequence;
        this.remainingActions = craftedArmorAction.recipeQuantity != 0 && !craftedArmorAction.player.botEnabled ? craftedArmorAction.recipeQuantity : craftedArmorAction.requestedQuantity;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.action.player.isCurrentActionSequence(this.actionSequence) || this.remainingActions == 0) {
            this.action.player.resetAnimation();
            if (this.action.player.botEnabled) {
                this.action.player.currentBotTask.startWalkToBank(this.action.player);
            }
            cycleEventContainer.stop();
            return;
        }
        if (!this.action.player.getInventoryManager().getContainer().containsItem(1734)) {
            if (this.action.player.botEnabled) {
                this.action.player.currentBotTask.startWalkToBank(this.action.player);
            }
            Player player = this.action.player;
            player.packetSender.sendGameMessage("You have run out of thread!");
            cycleEventContainer.stop();
            return;
        }
        if (!this.action.player.getInventoryManager().containsItemStack(new ItemStack(this.action.materialItemId, this.action.materialAmount))) {
            Player player = this.action.player;
            player.packetSender.sendGameMessage("You have run out of " + new ItemStack(this.action.materialItemId).getDefinition().getName().toLowerCase() + "!");
            if (this.action.player.botEnabled) {
                this.action.player.currentBotTask.startWalkToBank(this.action.player);
            }
            cycleEventContainer.stop();
            return;
        }
        this.action.player.getUpdateState().setAnimation(1249);
        Player player = this.action.player;
        player.packetSender.sendGameMessage("You make some " + new ItemStack(this.action.productItemId).getDefinition().getName().toLowerCase() + ".");
        ++this.action.player.craftingThreadUseCount;
        if (this.action.player.craftingThreadUseCount >= 5) {
            this.action.player.getInventoryManager().removeItem(new ItemStack(1734));
            this.action.player.craftingThreadUseCount = 0;
        }
        this.action.player.getInventoryManager().removeItem(new ItemStack(this.action.materialItemId, this.action.materialAmount));
        this.action.player.getInventoryManager().addItem(new ItemStack(this.action.productItemId));
        this.action.player.getSkillManager().addExperience(12, this.action.experience);
        --this.remainingActions;
        cycleEventContainer.setTickDelay(3);
    }

    @Override
    public final void onStop() {
        this.action.player.resetAnimation();
    }
}

