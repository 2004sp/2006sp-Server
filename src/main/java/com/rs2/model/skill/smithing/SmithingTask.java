package com.rs2.model.skill.smithing;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.smithing.SmithableItemDefinition;
import com.rs2.model.skill.smithing.SmithingBarDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SmithingTask
extends CycleEvent {
    private int remainingActions;
    private final Player player;
    private final int actionSequence;
    private final ItemStack requiredBars;
    private final int productItemId;
    private final SmithableItemDefinition smithableItem;
    private final SmithingBarDefinition barDefinition;
    private final ItemStack productItem;

    public SmithingTask(int remainingActions, Player player, int actionSequence, ItemStack itemStack, int productItemId, SmithableItemDefinition smithableItemDefinition, SmithingBarDefinition smithingBarDefinition, ItemStack itemStack2) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.requiredBars = itemStack;
        this.productItemId = productItemId;
        this.smithableItem = smithableItemDefinition;
        this.barDefinition = smithingBarDefinition;
        this.productItem = itemStack2;
        this.remainingActions = remainingActions;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player;
        if (this.player.getSelectedSmithingBarItemId() <= 0 || !this.player.isCurrentActionSequence(this.actionSequence)) {
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            cycleEventContainer.stop();
            return;
        }
        if (!this.player.getInventoryManager().containsItemStack(this.requiredBars)) {
            Player player2 = this.player;
            player2.packetSender.sendGameMessage("You have run out of bars!");
            cycleEventContainer.stop();
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            return;
        }
        this.player.getInventoryManager().removeItem(this.requiredBars);
        this.player.getInventoryManager().addItem(new ItemStack(this.productItemId, this.smithableItem.getOutputAmount()));
        boolean skillManager = this.player.getSkillManager().addExperience(13, (double)this.smithableItem.getBarCount() * this.barDefinition.getExperiencePerBar());
        if (this.player.getQuestState(0) != 1) {
            if (this.player.getQuestState(0) == 38) {
                this.player.advanceTutorialStage();
            }
            this.player.setInteractionTargetId(0);
            this.player.getDialogueManager().showOneLineStatement("You hammer the " + this.requiredBars.getDefinition().getName().toLowerCase() + " and make " + this.productItem.getDefinition().getName().toLowerCase() + ".");
            this.player.getQuestManager().refreshQuestJournal();
        } else {
            player = this.player;
            player.packetSender.sendGameMessage("You hammer the " + this.requiredBars.getDefinition().getName().toLowerCase() + " and make " + this.productItem.getDefinition().getName().toLowerCase() + ".");
        }
        --this.remainingActions;
        if (this.remainingActions <= 0) {
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            cycleEventContainer.stop();
            return;
        }
        if (!this.player.getInventoryManager().containsItemStack(this.requiredBars)) {
            player = this.player;
            player.packetSender.sendGameMessage("You have run out of bars!");
            if (this.player.botEnabled) {
                this.player.currentBotTask.startWalkToBank(this.player);
            }
            cycleEventContainer.stop();
            return;
        }
        player = this.player;
        player.packetSender.sendSoundEffect(468, 1, 0);
        this.player.getUpdateState().setAnimation(898);
        if (this.player.botEnabled && skillManager) {
            cycleEventContainer.stop();
            this.player.interactWithBotObjectTargets(this.player.botInteractionTargetIds);
            return;
        }
    }

    @Override
    public final void onStop() {
        this.player.nextActionSequence();
        this.player.resetAnimation();
    }
}

