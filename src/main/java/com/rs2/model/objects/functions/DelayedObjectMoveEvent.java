/*
 * Source recovery overlay: make remapped event accessible to recovered callers.
 */
package com.rs2.model.objects.functions;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameplayTrace;

public final class DelayedObjectMoveEvent
extends CycleEvent {
    private final Player player;
    private final Position destination;

    public DelayedObjectMoveEvent(Player player, Position position) {
        this.player = player;
        this.destination = position;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("object travel delayed-move execute player=" + GameplayTrace.describe(this.player) + " destination=" + this.destination.getX() + "," + this.destination.getY() + "," + this.destination.getPlane());
        }
        if (this.player.getPosition() != this.destination) {
            this.player.moveTo(this.destination);
        }
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("object travel delayed-move complete player=" + GameplayTrace.describe(this.player));
        }
        this.player.getUpdateState().setAnimation(65535);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}
