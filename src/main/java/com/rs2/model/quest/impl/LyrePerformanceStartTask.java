package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.quest.impl.LyrePerformanceFinishDialogueTask;
import com.rs2.model.quest.impl.LyrePerformanceHeckleTask;
import com.rs2.model.quest.impl.LyrePerformanceSecondLineTask;
import com.rs2.model.quest.impl.LyrePerformanceThirdLineTask;
import com.rs2.model.task.TickTask;

public final class LyrePerformanceStartTask
extends TickTask {
    private TickTask secondLineTask;
    final TickTask thirdLineTask;
    final TickTask heckleTask;
    final TickTask finishDialogueTask;
    final FremennikTrialsQuest quest;
    private final int performanceLineIndex;
    private final Player player;

    public LyrePerformanceStartTask(FremennikTrialsQuest fremennikTrialsQuest, int value5, int performanceLineIndex, Player player, int value32, int value42) {
        super(2);
        this.quest = fremennikTrialsQuest;
        this.performanceLineIndex = performanceLineIndex;
        this.player = player;
        this.secondLineTask = new LyrePerformanceSecondLineTask(this, 4, player, performanceLineIndex);
        this.thirdLineTask = new LyrePerformanceThirdLineTask(this, 4, player, performanceLineIndex);
        this.heckleTask = new LyrePerformanceHeckleTask(this, 4, value32, value42, player, performanceLineIndex);
        this.finishDialogueTask = new LyrePerformanceFinishDialogueTask(this, 4, player);
    }

    @Override
    public final void execute() {
        String username = this.quest.lyrePerformanceLines[this.performanceLineIndex][0].replaceAll("PLAYERNAME", this.player.getUsername());
        this.player.getUpdateState().setForcedText(username);
        World.getTaskScheduler().schedule(this.secondLineTask);
        this.stop();
    }
}

