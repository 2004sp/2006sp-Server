package com.rs2.model.quest.event;

import com.rs2.ServerSettings;
import com.rs2.bot.ClanWarsBotManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class ClanWarsEventStartTask
extends TickTask {
    public ClanWarsEventStartTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int value2) {
        super(500);
    }

    @Override
    public final void execute() {
        if (GameUtil.randomInt(ServerSettings.clanWarsEventChanceDivisor) == 0 && !ClanWarsBotManager.clanWarsEventActive) {
            GameplayHelper.startClanWarsBotEvent();
        }
    }
}

