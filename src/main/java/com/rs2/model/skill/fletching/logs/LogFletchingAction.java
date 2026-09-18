package com.rs2.model.skill.fletching.logs;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.logs.LogFletchingTask;
import com.rs2.model.task.CycleEventHandler;

public abstract class LogFletchingAction {
    protected Player player;
    protected int logItemId;
    protected int productItemId;
    private int requiredLevel;
    protected double experience;
    protected int menuQuantity;
    protected int requestedQuantity;

    public LogFletchingAction(Player player, int logItemId, int productItemId, int requiredLevel, double experience, int menuQuantity, int requestedQuantity) {
        this.player = player;
        this.logItemId = logItemId;
        this.productItemId = productItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.menuQuantity = menuQuantity;
        this.requestedQuantity = requestedQuantity;
    }

    public final void start() {
        Player player = this.player;
        player.packetSender.closeInterfaces();
        if (!ServerSettings.fletchingEnabled) {
            player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(946)) {
            this.player.getDialogueManager().showOneLineStatement("You need a knife to do this.");
            this.player.getDialogueManager().finishDialogue();
            return;
        }
        if (!this.player.getInventoryManager().getContainer().containsItem(this.logItemId)) {
            this.player.getDialogueManager().showOneLineStatement("You need a " + new ItemStack(this.logItemId).getDefinition().getName().toLowerCase() + "s to do this.");
            this.player.getDialogueManager().finishDialogue();
            return;
        }
        if (this.player.getSkillManager().getCurrentLevels()[9] < this.requiredLevel) {
            this.player.getDialogueManager().showOneLineStatement("You need a fletching level of " + this.requiredLevel + " to make this.");
            this.player.getDialogueManager().finishDialogue();
            return;
        }
        int value = this.player.nextActionSequence();
        this.player.setActiveCycleEvent(new LogFletchingTask(this, value));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 1);
    }
}

