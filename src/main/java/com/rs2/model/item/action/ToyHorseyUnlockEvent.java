package com.rs2.model.item.action;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class ToyHorseyUnlockEvent
extends CycleEvent {
    private final Player player;

    public ToyHorseyUnlockEvent(Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.setActionLocked(false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

