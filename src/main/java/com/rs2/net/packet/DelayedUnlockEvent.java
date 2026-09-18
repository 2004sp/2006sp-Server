package com.rs2.net.packet;

import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;

public final class DelayedUnlockEvent
extends CycleEvent {
    private PacketSender packetSender;

    public DelayedUnlockEvent(PacketSender packetSender) {
        this.packetSender = packetSender;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        PacketSender.getPlayer(this.packetSender).setActionLocked(false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

