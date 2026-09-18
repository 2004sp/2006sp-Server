package com.rs2.model.skill.mining;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class RuneEssenceMiningTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final int animationId;
    private final int essenceItemId;

    public RuneEssenceMiningTask(Player player, int actionSequence, int animationId, int essenceItemId) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.animationId = animationId;
        this.essenceItemId = essenceItemId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        this.player.getUpdateState().setAnimation(this.animationId);
        if (!this.player.getInventoryManager().addItem(new ItemStack(this.essenceItemId, 1))) {
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            cycleEventContainer.stop();
        } else {
            this.player.getSkillManager().addExperience(14, 5.0);
            RuneEssenceMiningTask runeEssenceMiningTask = this;
            runeEssenceMiningTask.player.rollActionReward();
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            player = this.player;
            player.packetSender.sendSoundEffect(1878, 1, 0);
            this.player.getUpdateState().setAnimation(-1);
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

