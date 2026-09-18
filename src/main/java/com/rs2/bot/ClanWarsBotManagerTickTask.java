package com.rs2.bot;

import com.rs2.bot.ClanWarsBotManager;
import com.rs2.model.task.TickTask;

public final class ClanWarsBotManagerTickTask
extends TickTask {
    public ClanWarsBotManagerTickTask(int value2) {
        super(10);
    }

    @Override
    public final void execute() {
        if (!ClanWarsBotManager.clanWarsEventActive) {
            this.stop();
            return;
        }
        ClanWarsBotManager.processClanWarsBotEvent();
    }
}

