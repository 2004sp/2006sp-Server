package com.rs2.model.item.action;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class GodBookRecitationEvent
extends CycleEvent {
    private final Player player;
    private final String[] recitationLines;

    public GodBookRecitationEvent(Player player, String[] recitationLines) {
        this.player = player;
        this.recitationLines = recitationLines;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.player.sharedActionValue == this.recitationLines.length) {
            cycleEventContainer.stop();
            return;
        }
        this.player.getUpdateState().setForcedText(this.recitationLines[this.player.sharedActionValue]);
        ++this.player.sharedActionValue;
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

