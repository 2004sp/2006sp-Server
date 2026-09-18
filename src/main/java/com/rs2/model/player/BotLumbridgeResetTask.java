package com.rs2.model.player;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class BotLumbridgeResetTask
extends TickTask {
    private Player resetPlayer;
    private final Player bot;

    public BotLumbridgeResetTask(Player player, int value2, Player player2) {
        super(2);
        this.resetPlayer = player;
        this.bot = player2;
    }

    @Override
    public final void execute() {
        this.resetPlayer.botLumbridgeResetPending = false;
        this.bot.deferredBotTask = null;
        GameplayHelper.selectAndStartProgressiveBotTask(this.bot, false);
        this.stop();
    }
}

