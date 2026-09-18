package com.rs2.model.task;

import com.rs2.model.task.CycleEventContainer;

public abstract class CycleEvent {
    public abstract void execute(CycleEventContainer container);

    public abstract void onStop();
}
