package com.rs2.bot;

import com.rs2.model.World;
import com.rs2.model.task.TickTask;

public final class MinigameBotStartTask
extends TickTask {
    private final BotPlayer botPlayer;

    public MinigameBotStartTask(BotPlayer botPlayer) {
        super(2);
        this.botPlayer = botPlayer;
    }

    @Override
    public final void execute() {
        MinigameBotManager.startMinigameBot(this.botPlayer);
        World.getTaskScheduler().schedule(new MinigameBotJoinTask(2, this.botPlayer));
        this.stop();
    }
}
