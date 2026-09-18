package com.rs2.model.travel;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.model.travel.GnomeGliderDestination;
import com.rs2.util.GameplayTrace;

public final class GnomeGliderTravelTask
extends CycleEvent {
    private final Player player;
    private final GnomeGliderDestination destination;

    public GnomeGliderTravelTask(Player player, GnomeGliderDestination gnomeGliderDestination) {
        this.player = player;
        this.destination = gnomeGliderDestination;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel gnome-glider move player=" + GameplayTrace.describe(this.player) + " destination=" + this.destination);
        }
        this.player.moveTo(this.destination.destination);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel gnome-glider moved player=" + GameplayTrace.describe(this.player) + " destination=" + this.destination);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

