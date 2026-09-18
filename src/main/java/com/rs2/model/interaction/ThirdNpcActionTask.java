package com.rs2.model.interaction;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.gameplay.abyss.AbyssManager;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaRewardShop;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.skill.runecrafting.RunecraftingHandler;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class ThirdNpcActionTask
extends TickTask {
    private final Player player;
    private final int actionSequence;
    private final Npc npc;

    public ThirdNpcActionTask(int value3, boolean enabled2, Player player, int actionSequence, Npc npc) {
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
        if (!this.player.isWithinReach(this.npc, 1) || this.player.isOverlapping(this.npc)) {
            return;
        }
        if (this.npc.getNpcId() == 3103) {
            MageTrainingArenaRewardShop.openRewardShop(this.player);
        }
        if (!GameUtil.hasClearPath(this.player.getPosition(), this.npc.getPosition(), true)) {
            return;
        }
        EntityTargetMovement.clearMovementTarget(this.player);
        Object npcs = World.getNpcs()[this.player.getInteractionTargetIndex()];
        this.player.getUpdateState().setFaceEntity(((Entity)npcs).getEncodedIndex());
        ((Entity)npcs).getUpdateState().setFaceEntity(this.player.getEncodedIndex());
        switch (this.player.getInteractionTargetId()) {
            case 553: {
                RunecraftingHandler.startAbyssMageTeleport(this.player, (Npc)npcs);
                break;
            }
            case 70: 
            case 1596: 
            case 1597: 
            case 1598: 
            case 1599: 
            case 3887: {
                ShopManager.openShop(this.player, GameplayHelper.getNpcShopId(this.player.getInteractionTargetId()));
                break;
            }
            case 2257: {
                if (!this.player.isMember()) {
                    this.player.packetSender.sendGameMessage("You need a members account to access members content.");
                    break;
                }
                if (ServerSettings.freeToPlayWorld) {
                    this.player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    break;
                }
                if (this.player.getQuestState(14) != 1) {
                    npcs = QuestDefinition.forId(14);
                    npcs = ((QuestDefinition)npcs).getName();
                    this.player.getDialogueManager().showOneLineStatement("You need to complete " + (String)npcs + " to do this.");
                    this.player.getDialogueManager().finishDialogue();
                    break;
                }
                if (this.player.enterTheAbyssMiniquestState == 1) {
                    AbyssManager.startAbyssMageTeleport(this.player, (Npc)npcs);
                    break;
                }
                this.player.getDialogueManager().showOneLineStatement("You need to complete Enter the Abyss miniquest to do this.");
                this.player.getDialogueManager().finishDialogue();
            }
        }
        this.stop();
    }
}

