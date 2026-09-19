package com.rs2.model.interaction;

import com.rs2.ServerSettings;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.GameplayHelper;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.BankManager;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.skill.runecrafting.RunecraftingHandler;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class SecondNpcActionTask
extends TickTask {
    private final Player player;
    private final int actionSequence;
    private final Npc npc;

    public SecondNpcActionTask(int value3, boolean enabled2, Player player, int actionSequence, Npc npc) {
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
        if (this.npc.isBanker()) {
            if (this.npc.isFacingInteractionPosition(this.player.getPosition(), 2)) {
                this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
                this.player.setInteractionTarget(this.npc);
                this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
                BankManager.openBank(this.player);
                EntityTargetMovement.clearMovementTarget(this.player);
                this.stop();
            }
            return;
        }
        if (!this.player.isWithinReach(this.npc, 1) || this.player.isOverlapping(this.npc)) {
            return;
        }
        if (this.player.getFishingHandler().handleFishingSpot(this.npc, 2)) {
            EntityTargetMovement.clearMovementTarget(this.player);
            this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
            this.stop();
            return;
        }
        if (!GameUtil.hasClearPath(this.player.getPosition(), this.npc.getPosition(), true)) {
            return;
        }
        EntityTargetMovement.clearMovementTarget(this.player);
        this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
        if (this.npc.getNpcId() == CastleWarsManager.LANTHUS_NPC_ID) {
            this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
            this.player.setInteractionTarget(this.npc);
            ShopManager.openCastleWarsRewardShop(this.player);
            this.stop();
            return;
        }
        if (CombatAction.handlePickpocketAttempt(this.player, this.npc)) {
            this.stop();
            return;
        }
        if (this.npc.isInApeAtoll()) {
            int npcShopId = GameplayHelper.getNpcShopId(this.player.getInteractionTargetId());
            if (npcShopId >= 0 && !this.player.isWearingMonkeyDisguise()) {
                Player player = this.player;
                player.packetSender.sendGameMessage("This npc is not interested in talking with you right now.");
                this.stop();
                return;
            }
        }
        if (this.npc.getNpcId() == 836 || this.npc.getNpcId() == 2257) {
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
        this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
        this.player.setInteractionTarget(this.npc);
        if (GameplayHelper.openNpcShop(this.player, this.npc.getNpcId())) {
            this.stop();
            return;
        }
        switch (this.player.getInteractionTargetId()) {
            case 166: 
            case 494: 
            case 495: 
            case 496: 
            case 498: 
            case 499: 
            case 902: 
            case 2619: {
                this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
                this.player.setInteractionTarget(this.npc);
                this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
                BankManager.openBank(this.player);
                EntityTargetMovement.clearMovementTarget(this.player);
                break;
            }
            case 3863: {
                GrandExchangeManager.openGrandExchange(this.player);
                break;
            }
            case 2437: {
                DialogueManager.continueDialogue(this.player, 2437, 4, 0);
                break;
            }
            case 1595: {
                DialogueManager.continueDialogue(this.player, 1595, 3, 1);
                break;
            }
            case 804: 
            case 1041: 
            case 2824: {
                GameplayHelper.openTanningInterface(this.player);
                break;
            }
            case 171: 
            case 300: 
            case 462: 
            case 844: {
                RunecraftingHandler.startAbyssMageTeleport(this.player, this.npc);
                break;
            }
            case 960: 
            case 961: 
            case 962: {
                this.player.getDuelSession().restoreHitpoints();
                break;
            }
            case 3021: {
                if (!this.player.isMember()) {
                    this.player.packetSender.sendGameMessage("You need a members account to access members content.");
                    break;
                }
                if (ServerSettings.freeToPlayWorld) {
                    this.player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    break;
                }
                this.player.getFarmingToolStore().open();
                break;
            }
            case 958: {
                BankManager.openBank(this.player);
            }
        }
        this.stop();
    }
}

