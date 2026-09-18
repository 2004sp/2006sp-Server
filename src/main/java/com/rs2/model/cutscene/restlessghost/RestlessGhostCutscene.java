package com.rs2.model.cutscene.restlessghost;

import com.rs2.model.Position;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.restlessghost.RestlessGhostAnimationStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostCompletionStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostReleaseStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostTextStep;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.quest.QuestScript;
import com.rs2.model.skill.woodcutting.WoodcuttingHandler;
import java.util.ArrayList;

public final class RestlessGhostCutscene
extends Cutscene {
    static QuestScript questScript = QuestDefinition.getQuestScript(12);
    Player player = this.getPlayer();
    Npc ghost = this.getNpc(0);

    public RestlessGhostCutscene(Player player, ArrayList arrayList) {
        super(player, arrayList);
    }

    @Override
    public final void addCustomSteps() {
        RestlessGhostTextStep restlessGhostTextStep = new RestlessGhostTextStep(this, this, 3);
        RestlessGhostAnimationStep restlessGhostAnimationStep = new RestlessGhostAnimationStep(this, this, 3);
        RestlessGhostReleaseStep restlessGhostReleaseStep = new RestlessGhostReleaseStep(this, this, 2);
        RestlessGhostCompletionStep restlessGhostCompletionStep = new RestlessGhostCompletionStep(this, this, 5);
        this.addStep(restlessGhostTextStep);
        this.addStep(restlessGhostAnimationStep);
        this.addStep(restlessGhostReleaseStep);
        this.addStep(restlessGhostCompletionStep);
    }

    @Override
    public final void setupScene() {
        this.ghost.moveTo(new Position(3248, 3193));
        this.player.moveTo(new Position(3248, 3192, 0));
        Player player = this.player;
        player.packetSender.sendMapRegion();
        player = this.player;
        player.packetSender.sendCameraLookAt(3340, 3149, 330, 0, 100);
        player = this.player;
        player.packetSender.sendCameraPosition(3325, 3149, 298, 0, 100);
        this.player.getUpdateState().setFaceEntity(this.ghost.getEncodedIndex());
        this.ghost.getUpdateState().setFacePositionValue(this.player.getPosition());
    }

    @Override
    public final void startDialogue() {
        this.player.getDialogueManager().setDialogueNpcId(this.ghost.getNpcId());
        this.player.setQuestState(questScript.getQuestId(), 6);
        this.player.getDialogueManager().showAlternateNpcThreeLineDialogue("", "Release! Thank you stranger..", "", 591);
        this.ghost.getUpdateState().setForcedText("Release! Thank you");
    }

    public static void sendReleaseProjectile(Position position, Position position2, int value2) {
        new WoodcuttingHandler(position, 0, position2, 0, new ProjectileDefinition(605, ProjectileTiming.MAGIC)).sendProjectileToNearbyPlayers();
    }
}

