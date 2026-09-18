package com.rs2.bot;

import com.rs2.bot.BotPlayer;
import com.rs2.bot.BotTaskPlanner;
import com.rs2.model.task.TickTask;

public final class BotTaskPlanningTask
extends TickTask {
    private final BotPlayer bot;

    public BotTaskPlanningTask(BotPlayer botPlayer, int value2, BotPlayer botPlayer2) {
        super(2);
        this.bot = botPlayer2;
    }

    @Override
    public final void execute() {
        BotTaskPlanner.startInitialProgressiveBotTask(this.bot);
        this.stop();
    }
}

