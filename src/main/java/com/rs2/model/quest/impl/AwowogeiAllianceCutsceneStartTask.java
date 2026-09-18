package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class AwowogeiAllianceCutsceneStartTask
extends TickTask {
    private final Player player;

    public AwowogeiAllianceCutsceneStartTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(5);
        this.player = player;
    }

    @Override
    public final void execute() {
        Position position = new Position(2980, 3045, 1);
        position.getPlane();
        int plane = position.getPlane() + 4 + (this.player.getIndex() << 2);
        this.player.cutsceneReturnPosition = new Position(2807, 2761, 0);
        Npc npc = new Npc(1448);
        Npc npc2 = new Npc(1428);
        Npc npc3 = new Npc(1409);
        GameplayHelper.spawnNonRespawningNpc(npc, 2678, 4579, plane, 5);
        GameplayHelper.spawnNonRespawningNpc(npc2, 2677, 4580, plane, 0);
        GameplayHelper.spawnNonRespawningNpc(npc3, 2677, 4578, plane, 2);
        this.player.temporaryCutsceneNpcs.add(npc);
        this.player.temporaryCutsceneNpcs.add(npc2);
        this.player.temporaryCutsceneNpcs.add(npc3);
        this.player.moveTo(new Position(2690, 4578, plane));
        Player player = this.player;
        player.packetSender.sendMapRegion();
        player = this.player;
        player.packetSender.sendCameraLookAt(2100, 3300, 500, 0, 100);
        player = this.player;
        player.packetSender.sendCameraPosition(8000, 3500, 0, 0, 100);
        player = this.player;
        player.packetSender.showWalkableInterface(-1);
        DialogueManager.continueDialogue(this.player, 1428, 100, 0);
        this.stop();
    }
}

