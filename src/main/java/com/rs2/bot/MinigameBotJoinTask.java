package com.rs2.bot;

import com.rs2.model.task.TickTask;

public final class MinigameBotJoinTask
extends TickTask {
    private final BotPlayer botPlayer;

    public MinigameBotJoinTask(int delay, BotPlayer botPlayer) {
        super(delay);
        this.botPlayer = botPlayer;
        // Split large Castle Wars bot populations across alternating ticks.
        // They still run at the same interval, but no longer all pathfind and
        // retarget on the exact same game tick.
        this.setRemainingTicks(1 + (int)(botPlayer.getNameHash() & 1L));
    }

    @Override
    public final void execute() {
        MinigameBotManager.processMinigameBot(this.botPlayer);
    }
}
