package com.rs2.model.task;

import com.rs2.model.task.TickTask;
import java.util.LinkedList;
import java.util.List;

public final class TaskScheduler {
    private List tasks = new LinkedList();

    public final List getTasks() {
        return this.tasks;
    }

    public final void schedule(TickTask tickTask) {
        this.tasks.add(tickTask);
    }
}
