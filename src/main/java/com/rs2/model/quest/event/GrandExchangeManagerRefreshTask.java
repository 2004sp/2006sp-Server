package com.rs2.model.quest.event;

import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;

public final class GrandExchangeManagerRefreshTask
extends TickTask {
    public GrandExchangeManagerRefreshTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int value2) {
        super(1000);
    }

    @Override
    public final void execute() {
        GrandExchangeManager.rollInstantPriceFluctuation();
    }
}

