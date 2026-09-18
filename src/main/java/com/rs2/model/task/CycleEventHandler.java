package com.rs2.model.task;

import com.rs2.model.Entity;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.ProfilerRegistry;
import com.rs2.util.ProfilerTimer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public final class CycleEventHandler {
    private static CycleEventHandler instance;
    private Queue activeEvents = new LinkedList();
    private Queue pendingEvents = new LinkedList();

    public static CycleEventHandler getInstance() {
        if (instance == null) {
            instance = new CycleEventHandler();
        }
        return instance;
    }

    public final CycleEventContainer schedule(Entity entity, CycleEvent cycleEvent, int value2) {
        CycleEventContainer cycleEventContainer = new CycleEventContainer(entity, cycleEvent, value2);
        if (entity.getIndex() == -1) {
            return cycleEventContainer;
        }
        this.pendingEvents.add(cycleEventContainer);
        return cycleEventContainer;
    }

    public final void process() {
        ProfilerTimer profilerTimer = ProfilerRegistry.getTimer("cycleEvents");
        profilerTimer.start();
        this.activeEvents.addAll(this.pendingEvents);
        this.pendingEvents.clear();
        Iterator iterator = this.activeEvents.iterator();
        while (iterator.hasNext()) {
            CycleEventContainer cycleEventContainer = (CycleEventContainer)iterator.next();
            if (cycleEventContainer == null) continue;
            if (cycleEventContainer.getEntity() == null || cycleEventContainer.getEntity().getIndex() == -1) {
                iterator.remove();
                continue;
            }
            if (cycleEventContainer.tick()) {
                cycleEventContainer.execute();
            }
            if (cycleEventContainer.isActive()) continue;
            iterator.remove();
        }
        profilerTimer.stop();
    }
}

