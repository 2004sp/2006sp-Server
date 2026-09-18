package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.LyrePerformanceStartTask;
import com.rs2.model.task.TickTask;

public final class LyrePerformanceThirdLineTask
extends TickTask {
    private LyrePerformanceStartTask startTask;
    private final Player player;
    private final int performanceLineIndex;

    public LyrePerformanceThirdLineTask(LyrePerformanceStartTask lyrePerformanceStartTask, int value3, Player player, int performanceLineIndex) {
        super(4);
        this.startTask = lyrePerformanceStartTask;
        this.player = player;
        this.performanceLineIndex = performanceLineIndex;
    }

    @Override
    public final void execute() {
        LyrePerformanceStartTask lyrePerformanceStartTask = this.startTask;
        this.player.getUpdateState().setForcedText(lyrePerformanceStartTask.quest.lyrePerformanceLines[this.performanceLineIndex][2]);
        World.getTaskScheduler().schedule(this.startTask.heckleTask);
        this.stop();
    }
}

