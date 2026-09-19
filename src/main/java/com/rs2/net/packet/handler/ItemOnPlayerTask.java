package com.rs2.net.packet.handler;

import com.rs2.model.EntityTargetMovement;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.handler.PlayerInteractionPacketHandler;
import com.rs2.util.GameUtil;

public final class ItemOnPlayerTask
extends TickTask {
    private final Player targetPlayer;
    private final Player requestingPlayer;
    private final int actionSequence;
    private final ItemStack usedItem;
    private final int inventorySlot;

    public ItemOnPlayerTask(PlayerInteractionPacketHandler playerInteractionPacketHandler, int value4, Player player, Player player2, int actionSequence, ItemStack itemStack, int inventorySlot) {
        super(1);
        this.targetPlayer = player;
        this.requestingPlayer = player2;
        this.actionSequence = actionSequence;
        this.usedItem = itemStack;
        this.inventorySlot = inventorySlot;
    }

    @Override
    public final void execute() {
        if (this.targetPlayer == null || this.targetPlayer.isDead() || !this.requestingPlayer.isCurrentActionSequence(this.actionSequence)) {
            EntityTargetMovement.clearMovementTarget(this.requestingPlayer);
            this.requestingPlayer.setInteractionTarget(null);
            this.requestingPlayer.getMovementQueue().clear();
            this.stop();
            return;
        }
        if (this.requestingPlayer.isWithinReach(this.targetPlayer, 1) && !this.requestingPlayer.isOverlapping(this.targetPlayer) && !EntityTargetMovement.isDiagonalTo(this.requestingPlayer.getPosition(), this.targetPlayer.getPosition())) {
            if (this.usedItem.getDefinition().canBeTransferredByDropping() && (this.requestingPlayer.gameMode != 0 || this.targetPlayer.gameMode != 0)) {
                this.requestingPlayer.pendingDialogueItem = this.usedItem;
                this.requestingPlayer.pendingItemDropTarget = this.targetPlayer;
                DialogueManager.startDialogue(this.requestingPlayer, 12345);
                EntityTargetMovement.clearMovementTarget(this.requestingPlayer);
                this.requestingPlayer.getUpdateState().setFacePosition(this.targetPlayer.getPosition());
                this.requestingPlayer.setInteractionTarget(null);
                this.requestingPlayer.getMovementQueue().clear();
                this.stop();
            }
            switch (this.usedItem.getId()) {
                case 4049: {
                    if (!CastleWarsManager.areTeamMates(this.requestingPlayer, this.targetPlayer)) {
                        this.requestingPlayer.packetSender.sendGameMessage("You can only use bandages on your Castle Wars teammates.");
                        break;
                    }
                    if (!this.requestingPlayer.getSkillManager().tryStartActionDelay(1800) || this.requestingPlayer.getCurrentHitpoints() <= 0) {
                        break;
                    }
                    if (!this.requestingPlayer.getInventoryManager().removeItemFromSlot(new ItemStack(4049, 1), this.inventorySlot)) {
                        break;
                    }
                    this.requestingPlayer.getUpdateState().setAnimation(829);
                    this.targetPlayer.heal(this.targetPlayer.getMaxHitpoints() / 10);
                    this.targetPlayer.addRunEnergyPercent(30);
                    this.targetPlayer.packetSender.sendRunEnergy();
                    this.targetPlayer.setPoisonDamage(0.0);
                    this.requestingPlayer.nextActionSequence();
                    this.requestingPlayer.getAttackDelayTimer().setDelayTicks(this.requestingPlayer.getAttackDelayTimer().getDelayTicks() + 2);
                    break;
                }
                case 962: {
                    this.requestingPlayer.getInventoryManager().removeItem(this.usedItem);
                    Player player = this.requestingPlayer;
                    player.packetSender.sendGameMessage("You pull the cracker with " + this.targetPlayer.getUsername() + "...");
                    player = this.targetPlayer;
                    player.packetSender.sendGameMessage(String.valueOf(this.requestingPlayer.getUsername()) + " pulls a Christmas cracker with you...");
                    if (GameUtil.randomInclusive(1) == 1) {
                        player = this.requestingPlayer;
                        player.packetSender.sendGameMessage("  ... and get a partyhat! Merry Christmas!");
                        player = this.targetPlayer;
                        player.packetSender.sendGameMessage("  ... and they get a partyhat! But have some coins anyways, Merry Christmas!");
                        this.targetPlayer.getInventoryManager().addItem(new ItemStack(995, 5 + GameUtil.randomInclusive(100)));
                        this.requestingPlayer.getInventoryManager().setItemInSlot(new ItemStack(1038 + (GameUtil.randomInclusive(5) << 1)), this.inventorySlot);
                        break;
                    }
                    player = this.targetPlayer;
                    player.packetSender.sendGameMessage("  ... and you get a partyhat! Merry Christmas!");
                    player = this.requestingPlayer;
                    player.packetSender.sendGameMessage("  ... and they get a partyhat! But have some coins anyways, Merry Christmas!");
                    this.requestingPlayer.getInventoryManager().setItemInSlot(new ItemStack(995, 5 + GameUtil.randomInclusive(100)), this.inventorySlot);
                    this.targetPlayer.getInventoryManager().addItem(new ItemStack(1038 + (GameUtil.randomInclusive(5) << 1)));
                    break;
                }
                default: {
                    Player player = this.requestingPlayer;
                    player.packetSender.sendGameMessage("Nothing interesting happens.");
                }
            }
            EntityTargetMovement.clearMovementTarget(this.requestingPlayer);
            this.requestingPlayer.getUpdateState().setFacePosition(this.targetPlayer.getPosition());
            this.requestingPlayer.setInteractionTarget(null);
            this.requestingPlayer.getMovementQueue().clear();
            this.stop();
        }
    }
}
