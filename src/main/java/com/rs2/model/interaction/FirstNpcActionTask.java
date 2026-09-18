package com.rs2.model.interaction;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheDefinitionIndex;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.GameplayHelper;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaRewardShop;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.woodcutting.TreeDefinition;
import com.rs2.model.skill.woodcutting.WoodcuttingHandler;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class FirstNpcActionTask
extends TickTask {
    private final Player player;
    private final int actionSequence;
    private final Npc npc;

    public FirstNpcActionTask(int value3, boolean enabled2, Player player, int actionSequence, Npc npc) {
        super(1, true);
        this.player = player;
        this.actionSequence = actionSequence;
        this.npc = npc;
    }

    @Override
    public final void execute() {
        if (this.player == null || !this.player.isCurrentActionSequence(this.actionSequence) || this.npc.isDead()) {
            this.stop();
            return;
        }
        if (this.npc.isBanker() || this.npc.getNpcId() == 736 || this.npc.getNpcId() == 745 || this.npc.getNpcId() == 3859 || this.npc.getNpcId() == 482) {
            if (this.npc.isFacingInteractionPosition(this.player.getPosition(), 2)) {
                this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
                this.player.setInteractionTarget(this.npc);
                this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
                DialogueManager.startDialogue(this.player, this.player.getInteractionTargetId());
                EntityTargetMovement.clearMovementTarget(this.player);
                this.stop();
            }
            return;
        }
        if (this.npc.getNpcId() == 673 || this.npc.getNpcId() == 1413) {
            if (this.npc.isWithinInteractionDistance(this.player.getPosition(), 1)) {
                this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
                this.player.setInteractionTarget(this.npc);
                this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
                DialogueManager.startDialogue(this.player, this.player.getInteractionTargetId());
                EntityTargetMovement.clearMovementTarget(this.player);
                this.stop();
            }
            return;
        }
        if (this.npc.getNpcId() == 1461 && this.player.getPosition().getY() >= 2759) {
            if (this.npc.isWithinInteractionDistance(this.player.getPosition(), 2)) {
                this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
                this.player.setInteractionTarget(this.npc);
                this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
                DialogueManager.startDialogue(this.player, this.player.getInteractionTargetId());
                EntityTargetMovement.clearMovementTarget(this.player);
                this.stop();
            }
            return;
        }
        if (!this.player.isWithinReach(this.npc, 1) || this.player.isOverlapping(this.npc)) {
            return;
        }
        if (this.npc.getNpcId() == 3103) {
            this.npc.setInteractionTarget(this.player);
            MageTrainingArenaRewardShop.openRewardShop(this.player);
            this.stop();
            return;
        }
        if (this.player.getFishingHandler().handleFishingSpot(this.npc, 1)) {
            EntityTargetMovement.clearMovementTarget(this.player);
            this.player.setInteractionTarget(this.npc);
            this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
            this.stop();
            return;
        }
        if (this.npc.getNpcId() != 767 && this.npc.getNpcId() != 249 && this.npc.getNpcId() != 221 && !GameUtil.hasClearPath(this.player.getPosition(), this.npc.getPosition(), true)) {
            return;
        }
        this.npc.setInteractionTarget(this.player);
        EntityTargetMovement.clearMovementTarget(this.player);
        if (this.npc.getNpcId() == 836) {
            if (!this.player.isMember()) {
                this.player.packetSender.sendGameMessage("You need a members account to access members content.");
                this.stop();
                return;
            }
            if (ServerSettings.freeToPlayWorld) {
                this.player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                this.stop();
                return;
            }
        }
        this.player.setInteractionTarget(this.npc);
        this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
        if (this.npc.getOwnerPlayer() != null && this.npc.getOwnerPlayer() != this.player) {
            Player player = this.player;
            player.packetSender.sendGameMessage(String.valueOf(this.npc.getDefinition().getName()) + " is not interested in interacting with you right now.");
            this.stop();
            return;
        }
        if (this.player.getSlayerManager().handleZygomiteSpawn(this.npc)) {
            this.stop();
            return;
        }
        if (TreeDefinition.forEntNpcId(this.npc.getNpcId()) != null) {
            WoodcuttingHandler.startWoodcutting(this.player, this.npc.getNpcId(), this.npc.getPosition().getX(), this.npc.getPosition().getY(), true);
            this.stop();
            return;
        }
        if (this.player.getQuestManager().handleFirstNpcAction(this.player.getInteractionTargetId())) {
            this.stop();
            return;
        }
        if (GameplayHelper.handleAnagramClueNpc(this.player, this.player.getInteractionTargetId())) {
            this.stop();
            return;
        }
        if (CacheDefinitionIndex.handleNpcClueNpc(this.player, this.player.getInteractionTargetId())) {
            this.stop();
            return;
        }
        if (DialogueManager.startDialogue(this.player, this.player.getInteractionTargetId())) {
            this.stop();
            return;
        }
        if (this.player.getInteractionTargetId() >= 0) {
            if (GameplayHelper.getNpcShopId(this.player.getInteractionTargetId()) >= 0) {
                DialogueManager.continueDialogueWithNpcId(this.player, 10008, 1, 0, this.player.getInteractionTargetId());
                this.stop();
                return;
            }
        }
        switch (this.player.getInteractionTargetId()) {
            case 166: 
            case 494: 
            case 495: 
            case 496: 
            case 499: 
            case 2619: {
                this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
                this.player.setInteractionTarget(this.npc);
                this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
                DialogueManager.startDialogue(this.player, this.player.getInteractionTargetId());
                EntityTargetMovement.clearMovementTarget(this.player);
            }
        }
        if (this.npc.getNpcId() != 767) {
            Player player = this.player;
            player.packetSender.sendGameMessage("This npc is not interested in talking with you right now.");
        }
        this.stop();
    }
}

