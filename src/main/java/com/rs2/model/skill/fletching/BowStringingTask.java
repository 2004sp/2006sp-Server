package com.rs2.model.skill.fletching;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.BowStringingDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class BowStringingTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final BowStringingDefinition definition;

    public BowStringingTask(Player player, int actionSequence, BowStringingDefinition bowStringingDefinition) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.definition = bowStringingDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!(this.player.isCurrentActionSequence(this.actionSequence) && this.player.getInventoryManager().getContainer().containsItem(this.definition.getUnstrungBowItemId()) && this.player.getInventoryManager().getContainer().containsItem(this.definition.getBowStringItemId()))) {
            cycleEventContainer.stop();
            return;
        }
        this.player.getUpdateState().setAnimation(713);
        Player player = this.player;
        player.packetSender.sendGameMessage("You add a string to the bow.");
        this.player.getInventoryManager().removeItem(new ItemStack(this.definition.getUnstrungBowItemId()));
        this.player.getInventoryManager().removeItem(new ItemStack(this.definition.getBowStringItemId()));
        this.player.getInventoryManager().addItem(new ItemStack(this.definition.getStrungBowItemId()));
        this.player.getSkillManager().addExperience(9, this.definition.getExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

