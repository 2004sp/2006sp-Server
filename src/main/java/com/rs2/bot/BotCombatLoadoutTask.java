package com.rs2.bot;

import com.rs2.bot.BotPlayer;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.task.TickTask;

public final class BotCombatLoadoutTask
extends TickTask {
    private final BotPlayer bot;

    public BotCombatLoadoutTask(BotPlayer botPlayer, int value2, BotPlayer botPlayer2) {
        super(2);
        this.bot = botPlayer2;
    }

    @Override
    public final void execute() {
        BotCombatLoadoutManager.startCombatLoadoutBot(this.bot);
        this.stop();
    }
}

