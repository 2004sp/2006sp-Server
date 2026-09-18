package com.rs2.model.player;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class BotBankContinuationTask
extends TickTask {
    private final Player bot;

    public BotBankContinuationTask(int value2, Player player) {
        super(value2);
        this.bot = player;
    }

    @Override
    public final void execute() {
        if (this.bot.botMode != 4) {
            GameplayHelper.startNextBotTask(this.bot);
        } else {
            GameplayHelper.selectAndStartNextProgressiveBotTask(this.bot);
        }
        this.stop();
    }
}

