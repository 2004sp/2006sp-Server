package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.handler.ItemActionPacketHandler;

public final class DigSearchTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final boolean sendNothingFoundMessage;

    public DigSearchTask(ItemActionPacketHandler itemActionPacketHandler, Player player, int actionSequence, boolean sendNothingFoundMessage) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.sendNothingFoundMessage = sendNothingFoundMessage;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        if (this.sendNothingFoundMessage) {
            Player player = this.player;
            player.packetSender.sendGameMessage("but do not find anything.");
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}

