package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class ShipyardCrateHoleSearchTask
extends TickTask {
    private final Player player;

    public ShipyardCrateHoleSearchTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.player.getDialogueManager().showTwoLineStatement("You find a hole in the floor under the crate! All you can see is the", "faint glimmer of light from extremely far below.");
        this.stop();
    }
}

