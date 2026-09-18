package com.rs2.model.npc;

import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AbyssMageTeleportEvent
extends CycleEvent {
    private final Player player;
    private final int destinationX;
    private final int destinationY;
    private final int destinationPlane;

    public AbyssMageTeleportEvent(Npc npc, Player player, int destinationX, int destinationY, int destinationPlane) {
        this.player = player;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationPlane = destinationPlane;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.getTeleportManager().startAbyssTeleport(this.destinationX, this.destinationY, this.destinationPlane);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

