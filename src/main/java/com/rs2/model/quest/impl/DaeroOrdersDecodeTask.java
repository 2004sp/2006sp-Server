package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class DaeroOrdersDecodeTask
extends TickTask {
    private final Player player;

    public DaeroOrdersDecodeTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(4);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        DialogueManager.continueDialogue(this.player, 1407, 3, 0);
        this.stop();
    }
}

