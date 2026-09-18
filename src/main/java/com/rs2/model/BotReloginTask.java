package com.rs2.model;

import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.model.task.TickTask;

public final class BotReloginTask
extends TickTask {
    private final String username;
    private final int botMode;

    public BotReloginTask(int value2, String username, int botMode) {
        super(30);
        this.username = username;
        this.botMode = botMode;
    }

    @Override
    public final void execute() {
        BotPlayer.createNamedBot(this.username, "zxcvbn", this.botMode);
        this.stop();
    }
}

