package com.rs2.model.quest.event;

import com.rs2.model.quest.event.ChristmasDropEventHook;
import com.rs2.model.task.TickTask;

public final class ChristmasDropRefreshTask
extends TickTask {
    private final ChristmasDropEventHook eventHook;

    public ChristmasDropRefreshTask(ChristmasDropEventHook christmasDropEventHook, int value2) {
        super(value2);
        this.eventHook = christmasDropEventHook;
    }

    @Override
    public final void execute() {
        this.eventHook.spawnDrops();
    }
}
