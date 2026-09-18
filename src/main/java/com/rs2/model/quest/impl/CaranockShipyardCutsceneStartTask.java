package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class CaranockShipyardCutsceneStartTask
extends TickTask {
    private final Player player;

    public CaranockShipyardCutsceneStartTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(5);
        this.player = player;
    }

    @Override
    public final void execute() {
        Position position = new Position(2980, 3045, 0);
        position.getPlane();
        int plane = position.getPlane() + 4 + (this.player.getIndex() << 2);
        this.player.cutsceneReturnPosition = new Position(2802, 2707, 0);
        Npc npc = new Npc(1427);
        Npc npc2 = new Npc(1470);
        GameplayHelper.spawnNonRespawningNpc(npc, 2604, 4580, plane, 2);
        GameplayHelper.spawnNonRespawningNpc(npc2, 2604, 4582, plane, 0);
        this.player.temporaryCutsceneNpcs.add(npc);
        this.player.temporaryCutsceneNpcs.add(npc2);
        this.player.moveTo(new Position(2596, 4581, plane));
        Player player = this.player;
        player.packetSender.sendMapRegion();
        player = this.player;
        player.packetSender.sendCameraLookAt(3550, 3450, 400, 0, 100);
        player = this.player;
        player.packetSender.sendCameraPosition(16000, 3500, 0, 0, 100);
        player = this.player;
        player.packetSender.showWalkableInterface(-1);
        DialogueManager.continueDialogue(this.player, 1470, 100, 0);
        this.stop();
    }
}

