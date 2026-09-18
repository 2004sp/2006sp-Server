package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ScorpionCatcherQuest;
import com.rs2.model.quest.impl.SeerMirrorHairSmoothTask;
import com.rs2.model.quest.impl.SeerMirrorResultDialogueTask;
import com.rs2.model.task.TickTask;

public final class SeerMirrorGazeTask
extends TickTask {
    private final TickTask hairSmoothTask;
    final TickTask resultDialogueTask;
    private final Player player;

    public SeerMirrorGazeTask(ScorpionCatcherQuest scorpionCatcherQuest, int value2, Player player) {
        super(4);
        this.player = player;
        this.hairSmoothTask = new SeerMirrorHairSmoothTask(this, 4, player);
        this.resultDialogueTask = new SeerMirrorResultDialogueTask(this, 4, player);
    }

    @Override
    public final void execute() {
        Player player = this.player;
        player.packetSender.sendGameMessage("The seer gazes into the mirror");
        World.getTaskScheduler().schedule(this.hairSmoothTask);
        this.stop();
    }
}
