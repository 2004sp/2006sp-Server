package com.rs2.model.travel;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameplayTrace;

public final class GnomeGliderLandingTask
extends CycleEvent {
    private final Player player;

    public GnomeGliderLandingTask(Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel gnome-glider landing player=" + GameplayTrace.describe(this.player));
        }
        this.player.setActionLocked(false);
        Player player = this.player;
        player.packetSender.closeInterfaces();
        player = this.player;
        player.packetSender.sendConfig(153, -1);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("travel gnome-glider landed player=" + GameplayTrace.describe(this.player));
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

