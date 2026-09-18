package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.task.TickTask;

public final class SwensenMazeRandomObjectExitStepTask
extends TickTask {
    private final int verticalOffset;
    private final Player player;
    private final int horizontalOffset;

    public SwensenMazeRandomObjectExitStepTask(FremennikTrialsQuest fremennikTrialsQuest, int value4, int verticalOffset, Player player, int horizontalOffset) {
        super(1);
        this.verticalOffset = verticalOffset;
        this.player = player;
        this.horizontalOffset = horizontalOffset;
    }

    @Override
    public final void execute() {
        Player player;
        if (this.verticalOffset != 0) {
            player = this.player;
            player.packetSender.queueRelativeMovementStep(0, this.verticalOffset < 0 ? 1 : -1, true);
        }
        if (this.horizontalOffset != 0) {
            player = this.player;
            player.packetSender.queueRelativeMovementStep(this.horizontalOffset < 0 ? 1 : -1, 0, true);
        }
        this.stop();
    }
}
