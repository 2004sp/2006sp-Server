package com.rs2.bot;

import com.rs2.model.task.TickTask;

public final class MinigameBotJoinTask
extends TickTask {
    private final BotPlayer botPlayer;

    public MinigameBotJoinTask(int delay, BotPlayer botPlayer) {
        super(delay);
        this.botPlayer = botPlayer;
    }

    @Override
    public final void execute() {
        if (MinigameBotManager.joinCastleWars(this.botPlayer)) {
            this.stop();
        }
    }
}
