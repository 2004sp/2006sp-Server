package com.rs2.model.dialogue;

import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class TenthSquadSigilTeleportTask
extends TickTask {
    private final Player player;

    public TenthSquadSigilTeleportTask(int value2, Player player) {
        super(5);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        Position position = new Position(2980, 3045, 1);
        position.getPlane();
        int plane = position.getPlane() + 4 + (this.player.getIndex() << 2);
        this.player.moveTo(new Position(2702, 9173, plane));
        Player player = this.player;
        player.packetSender.closeInterfaces();
        this.player.spawnTenthSquadSigilNpcs(plane);
        DialogueManager.continueDialogue(this.player, 1412, 1, 0);
        this.stop();
    }
}

