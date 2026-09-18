package com.rs2.model.quest.event;

import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class DropPartyEventStartTask
extends TickTask {
    public DropPartyEventStartTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int value2) {
        super(500);
    }

    @Override
    public final void execute() {
        if (GameUtil.randomInt(DropPartyBotManager.dropPartyChanceDivisor) == 0 && !DropPartyBotManager.dropPartyActive) {
            GameplayHelper.startDropPartyBotEvent();
        }
    }
}

