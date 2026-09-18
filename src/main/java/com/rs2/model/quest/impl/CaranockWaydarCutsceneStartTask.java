package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class CaranockWaydarCutsceneStartTask
extends TickTask {
    private final Player player;

    public CaranockWaydarCutsceneStartTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(5);
        this.player = player;
    }

    @Override
    public final void execute() {
        Position position = new Position(2980, 3045, 0);
        position.getPlane();
        int plane = position.getPlane() + 4 + (this.player.getIndex() << 2);
        this.player.cutsceneReturnPosition = new Position(2805, 9143, 0);
        Npc npc = new Npc(1427);
        Npc npc2 = new Npc(1409);
        GameplayHelper.spawnNonRespawningNpc(npc, 2672, 4559, plane, 5);
        GameplayHelper.spawnNonRespawningNpc(npc2, 2670, 4559, plane, 4);
        this.player.temporaryCutsceneNpcs.add(npc);
        this.player.temporaryCutsceneNpcs.add(npc2);
        this.player.moveTo(new Position(2670, 4573, plane));
        Player player = this.player;
        player.packetSender.sendMapRegion();
        player = this.player;
        player.packetSender.sendCameraLookAt(3600, 2900, 400, 0, 100);
        player = this.player;
        player.packetSender.sendCameraPosition(3500, 1000, 0, 0, 100);
        player = this.player;
        player.packetSender.showWalkableInterface(-1);
        DialogueManager.continueDialogue(this.player, 1427, 100, 0);
        this.stop();
    }
}

