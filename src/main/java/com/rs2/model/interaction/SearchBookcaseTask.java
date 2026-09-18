package com.rs2.model.interaction;

import com.rs2.model.interaction.FirstObjectActionTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SearchBookcaseTask
extends CycleEvent {
    private final Player player;

    public SearchBookcaseTask(FirstObjectActionTask firstObjectActionTask, Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = this.player;
        player.packetSender.sendGameMessage("None of them look very interesting.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

