package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.task.TickTask;

public final class SwensenMazeObject4155ExitStepTask
extends TickTask {
    private final Player player;

    public SwensenMazeObject4155ExitStepTask(FremennikTrialsQuest fremennikTrialsQuest, int value2, Player player) {
        super(1);
        this.player = player;
    }

    @Override
    public final void execute() {
        Player player = this.player;
        player.packetSender.queueRelativeMovementStep(-1, 0, true);
        this.stop();
    }
}

