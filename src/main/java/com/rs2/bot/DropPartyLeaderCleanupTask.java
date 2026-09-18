package com.rs2.bot;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class DropPartyLeaderCleanupTask
extends TickTask {
    private final Player leader;

    public DropPartyLeaderCleanupTask(int value2, Player player) {
        super(10);
        this.leader = player;
    }

    @Override
    public final void execute() {
        if (this.leader.isDead() || !this.leader.isRegistered()) {
            this.stop();
            return;
        }
        this.leader.moveTo(new Position(9999, 9999, 0));
        this.stop();
    }
}

