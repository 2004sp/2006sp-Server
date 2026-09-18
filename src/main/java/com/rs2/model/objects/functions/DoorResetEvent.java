package com.rs2.model.objects.functions;

import com.rs2.model.objects.functions.DoorHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class DoorResetEvent
extends CycleEvent {
    private final Player player;
    private final DoorHandler door;

    public DoorResetEvent(Player player, DoorHandler doorHandler) {
        this.player = player;
        this.door = doorHandler;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        DoorHandler.handleDoor(this.player, DoorHandler.getCurrentObjectId(this.door), DoorHandler.getCurrentX(this.door), DoorHandler.getCurrentY(this.door), DoorHandler.getPlane(this.door));
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

