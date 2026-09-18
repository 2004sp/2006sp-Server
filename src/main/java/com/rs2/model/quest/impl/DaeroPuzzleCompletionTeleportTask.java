package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class DaeroPuzzleCompletionTeleportTask
extends TickTask {
    private final Player player;

    public DaeroPuzzleCompletionTeleportTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(5);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.moveTo(new Position(this.player.getPosition().getX() + 64, this.player.getPosition().getY(), 0));
        this.player.setActionLocked(false);
        Player player = this.player;
        player.packetSender.closeInterfaces();
        this.stop();
    }
}

