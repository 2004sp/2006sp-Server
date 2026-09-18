package com.rs2.model.quest.event;

import com.rs2.model.quest.event.HalloweenMaskDropEventHook;
import com.rs2.model.task.TickTask;

public final class HalloweenMaskDropRefreshTask
extends TickTask {
    private final HalloweenMaskDropEventHook eventHook;

    public HalloweenMaskDropRefreshTask(HalloweenMaskDropEventHook halloweenMaskDropEventHook, int value2) {
        super(value2);
        this.eventHook = halloweenMaskDropEventHook;
    }

    @Override
    public final void execute() {
        this.eventHook.spawnDrops();
    }
}
