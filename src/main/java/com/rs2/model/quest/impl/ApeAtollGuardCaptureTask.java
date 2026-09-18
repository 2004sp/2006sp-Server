package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class ApeAtollGuardCaptureTask
extends TickTask {
    private final Player player;

    public ApeAtollGuardCaptureTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.actionSucceeded = false;
        this.player.setActionLocked(false);
        this.player.moveTo(new Position(2772, 2794, 0));
        this.player.resetAnimation();
        Player player = this.player;
        player.packetSender.showWalkableInterface(-1);
        this.stop();
    }
}

