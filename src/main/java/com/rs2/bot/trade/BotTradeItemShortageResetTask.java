package com.rs2.bot.trade;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class BotTradeItemShortageResetTask
extends TickTask {
    private final Player player;

    public BotTradeItemShortageResetTask(int value2, Player player) {
        super(10);
        this.player = player;
    }

    @Override
    public final void execute() {
        GameplayHelper.startNextBotTask(this.player);
        this.stop();
    }
}

