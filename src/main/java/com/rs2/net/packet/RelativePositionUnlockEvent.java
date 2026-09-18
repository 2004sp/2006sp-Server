package com.rs2.net.packet;

import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;

public final class RelativePositionUnlockEvent
extends CycleEvent {
    private boolean delayElapsed = false;
    private PacketSender packetSender;
    private final boolean clearForcedMovementFlag;

    public RelativePositionUnlockEvent(PacketSender packetSender, boolean clearForcedMovementFlag) {
        this.packetSender = packetSender;
        this.clearForcedMovementFlag = clearForcedMovementFlag;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.delayElapsed) {
            PacketSender.getPlayer(this.packetSender).setActionLocked(false);
            cycleEventContainer.stop();
        }
        this.delayElapsed = true;
    }

    @Override
    public final void onStop() {
        if (this.clearForcedMovementFlag) {
            PacketSender.getPlayer((PacketSender)this.packetSender).forcedMovementActive = false;
        }
    }
}

