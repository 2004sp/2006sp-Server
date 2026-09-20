package com.rs2.bot;

import com.rs2.model.World;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.task.TickTask;

public final class MinigameBotJoinTask
extends TickTask {
    private final BotPlayer botPlayer;

    public MinigameBotJoinTask(int delay, BotPlayer botPlayer) {
        // Wake cheaply every tick and decide whether this bot owns this AI phase.
        // This lets large Castle Wars matches spread expensive AI/pathfinding
        // across more ticks without making small matches feel sluggish.
        super(1, false);
        this.botPlayer = botPlayer;
    }

    @Override
    public final void execute() {
        int phaseCount = 2;
        if (CastleWarsManager.isInGame(this.botPlayer)) {
            if (CastleWarsManager.isCarryingFlag(this.botPlayer)) {
                phaseCount = 1;
            } else {
                int population = CastleWarsManager.getTotalGamePlayerCount();
                if (population >= 300) {
                    phaseCount = 4;
                } else if (population >= 160) {
                    phaseCount = 3;
                }
            }
        }

        long nameHash = this.botPlayer.getNameHash();
        int phase = (int)((nameHash ^ nameHash >>> 32) & 0x7FFFFFFFL) % phaseCount;
        if (phaseCount > 1 && Math.floorMod(World.tickCount, phaseCount) != phase) {
            return;
        }

        MinigameBotManager.processMinigameBot(this.botPlayer);
    }
}
