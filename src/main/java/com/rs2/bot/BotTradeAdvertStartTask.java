package com.rs2.bot;

import com.rs2.bot.BotPlayer;
import com.rs2.cache.CacheArchiveEntry;
import com.rs2.model.GameplayHelper;
import com.rs2.model.task.TickTask;

public final class BotTradeAdvertStartTask
extends TickTask {
    private final BotPlayer bot;

    public BotTradeAdvertStartTask(BotPlayer botPlayer, int value2, BotPlayer botPlayer2) {
        super(2);
        this.bot = botPlayer2;
    }

    @Override
    public final void execute() {
        GameplayHelper.startNextBotTask(this.bot);
        BotPlayer botPlayer = this.bot;
        CacheArchiveEntry.startTradeOfferTick(botPlayer);
        this.stop();
    }
}

