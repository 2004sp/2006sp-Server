package com.rs2.model.gameplay.abyss;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AbyssDelayedMoveEvent
extends CycleEvent {
    private final Player player;
    private final int destinationX;
    private final int destinationY;

    public AbyssDelayedMoveEvent(Player player, int destinationX, int destinationY) {
        this.player = player;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.moveTo(new Position(this.destinationX, this.destinationY, this.player.getPosition().getPlane()));
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

