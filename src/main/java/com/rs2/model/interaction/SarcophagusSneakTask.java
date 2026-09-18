package com.rs2.model.interaction;

import com.rs2.model.Position;
import com.rs2.model.interaction.FirstObjectActionTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SarcophagusSneakTask
extends CycleEvent {
    private final Player player;

    public SarcophagusSneakTask(FirstObjectActionTask firstObjectActionTask, Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.moveTo(new Position(3233, 2887));
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

