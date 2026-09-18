package com.rs2.model.gameplay.barrows;

import com.rs2.model.gameplay.barrows.BarrowsPrayerDrainTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class BarrowsPrayerDrainResetTask
extends TickTask {
    private final Player player;
    private final int interfaceId;

    public BarrowsPrayerDrainResetTask(BarrowsPrayerDrainTask barrowsPrayerDrainTask, int value3, Player player, int interfaceId) {
        super(3);
        this.player = player;
        this.interfaceId = interfaceId;
    }

    @Override
    public final void execute() {
        Player player = this.player;
        player.packetSender.sendInterfaceModel(this.interfaceId, 200, -1);
        this.stop();
    }
}

