package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.PiratesTreasureQuest;
import com.rs2.model.task.TickTask;

public final class InitialBananaCratePromptTask
extends TickTask {
    private final Player player;

    public InitialBananaCratePromptTask(PiratesTreasureQuest piratesTreasureQuest, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.getDialogueManager().showTwoOptionsWithTitle("Do you want to take a banana?", "Yes.", "No.");
        this.stop();
    }
}

