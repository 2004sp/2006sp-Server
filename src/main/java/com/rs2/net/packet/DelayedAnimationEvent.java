package com.rs2.net.packet;

import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;

public final class DelayedAnimationEvent
extends CycleEvent {
    private PacketSender packetSender;
    private final int animationId;

    public DelayedAnimationEvent(PacketSender packetSender, int animationId) {
        this.packetSender = packetSender;
        this.animationId = animationId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        PacketSender.getPlayer(this.packetSender).getUpdateState().setAnimation(this.animationId);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

