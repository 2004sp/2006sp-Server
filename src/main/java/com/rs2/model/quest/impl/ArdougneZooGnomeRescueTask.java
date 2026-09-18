package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class ArdougneZooGnomeRescueTask
extends TickTask {
    private final Player player;

    public ArdougneZooGnomeRescueTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(5);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.player.moveTo(new Position(2606, 3274, 0));
        Player player = this.player;
        player.packetSender.closeInterfaces();
        this.stop();
    }
}

