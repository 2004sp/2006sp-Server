package com.rs2.model.quest.impl;

import com.rs2.model.Entity;
import com.rs2.model.EntityUpdateState;
import com.rs2.model.World;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.LyrePerformanceStartTask;
import com.rs2.model.task.TickTask;

public final class LyrePerformanceHeckleTask
extends TickTask {
    private LyrePerformanceStartTask startTask;
    private final int hecklerNpcIndex;
    private final int heckleLineIndex;
    private final Player player;
    private final int performanceLineIndex;

    public LyrePerformanceHeckleTask(LyrePerformanceStartTask lyrePerformanceStartTask, int value5, int hecklerNpcIndex, int heckleLineIndex, Player player, int performanceLineIndex) {
        super(4);
        this.startTask = lyrePerformanceStartTask;
        this.hecklerNpcIndex = hecklerNpcIndex;
        this.heckleLineIndex = heckleLineIndex;
        this.player = player;
        this.performanceLineIndex = performanceLineIndex;
    }

    @Override
    public final void execute() {
        Object value = this.startTask;
        value = Npc.findByDefinitionId(((LyrePerformanceStartTask)value).quest.lyreAudienceNpcIds[this.hecklerNpcIndex]);
        if (value != null) {
            EntityUpdateState entityUpdateState = ((Entity)value).getUpdateState();
            value = this.startTask;
            entityUpdateState.setForcedText(((LyrePerformanceStartTask)value).quest.lyreAudienceHeckleLines[this.heckleLineIndex]);
        }
        value = this.startTask;
        this.player.getUpdateState().setForcedText(((LyrePerformanceStartTask)value).quest.lyrePerformanceLines[this.performanceLineIndex][3]);
        World.getTaskScheduler().schedule(this.startTask.finishDialogueTask);
        this.stop();
    }
}

