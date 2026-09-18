package com.rs2.model.gameplay.castlewars;

import com.rs2.model.task.TickTask;

public final class CastleWarsTickTask
extends TickTask {
    public CastleWarsTickTask() {
        super(1);
    }

    @Override
    public final void execute() {
        CastleWarsManager.process();
    }
}
