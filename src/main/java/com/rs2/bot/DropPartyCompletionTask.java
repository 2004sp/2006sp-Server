package com.rs2.bot;

import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class DropPartyCompletionTask
extends TickTask {
    private final Player participant;

    public DropPartyCompletionTask(int value2, Player player) {
        super(10);
        this.participant = player;
    }

    @Override
    public final void execute() {
        if (this.participant.isDead() || !this.participant.isRegistered()) {
            this.stop();
            DropPartyBotManager.dropPartyActive = false;
            return;
        }
        this.participant.moveTo(new Position(9999, 9999, 0));
        DropPartyBotManager.dropPartyActive = false;
        this.stop();
    }
}

