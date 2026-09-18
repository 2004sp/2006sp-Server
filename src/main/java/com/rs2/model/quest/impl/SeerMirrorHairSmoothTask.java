package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.SeerMirrorGazeTask;
import com.rs2.model.task.TickTask;

public final class SeerMirrorHairSmoothTask
extends TickTask {
    private final SeerMirrorGazeTask gazeTask;
    private final Player player;

    public SeerMirrorHairSmoothTask(SeerMirrorGazeTask seerMirrorGazeTask, int value2, Player player) {
        super(4);
        this.gazeTask = seerMirrorGazeTask;
        this.player = player;
    }

    @Override
    public final void execute() {
        Player player = this.player;
        player.packetSender.sendGameMessage("The seer smoothes his hair with his hand");
        World.getTaskScheduler().schedule(this.gazeTask.resultDialogueTask);
        this.stop();
    }
}
