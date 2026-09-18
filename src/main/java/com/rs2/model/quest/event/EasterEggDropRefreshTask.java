package com.rs2.model.quest.event;

import com.rs2.model.quest.event.EasterEggDropEventHook;
import com.rs2.model.task.TickTask;

public final class EasterEggDropRefreshTask
extends TickTask {
    private final EasterEggDropEventHook eventHook;

    public EasterEggDropRefreshTask(EasterEggDropEventHook easterEggDropEventHook, int value2) {
        super(value2);
        this.eventHook = easterEggDropEventHook;
    }

    @Override
    public final void execute() {
        this.eventHook.spawnDrops();
    }
}
