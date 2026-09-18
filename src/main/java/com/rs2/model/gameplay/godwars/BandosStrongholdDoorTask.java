package com.rs2.model.gameplay.godwars;

import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class BandosStrongholdDoorTask
extends TickTask {
    private final Player player;

    public BandosStrongholdDoorTask(int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        Player player = this.player;
        player.packetSender.queueRelativeMovementStep(this.player.getPosition().getX() >= 2851 ? -2 : 2, 0, true);
        this.stop();
    }
}

