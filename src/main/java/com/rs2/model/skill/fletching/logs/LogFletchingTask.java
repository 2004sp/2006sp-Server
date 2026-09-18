package com.rs2.model.skill.fletching.logs;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.LogFletchingAction;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class LogFletchingTask
extends CycleEvent {
    private int remainingActions;
    private LogFletchingAction action;
    private final int actionSequence;

    public LogFletchingTask(LogFletchingAction logFletchingAction, int actionSequence) {
        this.action = logFletchingAction;
        this.actionSequence = actionSequence;
        this.remainingActions = logFletchingAction.menuQuantity != 0 ? logFletchingAction.menuQuantity : logFletchingAction.requestedQuantity;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.action.player.isCurrentActionSequence(this.actionSequence) || this.remainingActions == 0 || !this.action.player.getInventoryManager().getContainer().containsItem(this.action.logItemId)) {
            String text = "";
            Player player = this.action.player;
            this.action.player.interfaceAction = text;
            this.action.player.resetAnimation();
            cycleEventContainer.stop();
            return;
        }
        this.action.player.getUpdateState().setAnimation(1248);
        Player player = this.action.player;
        player.packetSender.sendSoundEffect(811, 1, 0);
        String definition = new ItemStack(this.action.productItemId).getDefinition().getName().toLowerCase();
        player = this.action.player;
        player.packetSender.sendGameMessage("You carefully cut the " + new ItemStack(this.action.logItemId).getDefinition().getName().toLowerCase() + " into " + (definition.contains("shaft") ? "some arrow shafts" : (definition.contains("longbow") ? "a longbow" : "a shortbow")) + ".");
        this.action.player.getInventoryManager().removeItem(new ItemStack(this.action.logItemId));
        this.action.player.getInventoryManager().addItem(new ItemStack(this.action.productItemId, this.action.productItemId == 52 ? 15 : 1));
        this.action.player.getSkillManager().addExperience(9, this.action.experience);
        --this.remainingActions;
        cycleEventContainer.setTickDelay(3);
    }

    @Override
    public final void onStop() {
        this.action.player.resetAnimation();
    }
}

