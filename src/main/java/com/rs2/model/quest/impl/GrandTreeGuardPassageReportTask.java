package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

public final class GrandTreeGuardPassageReportTask
extends TickTask {
    private final Player player;

    public GrandTreeGuardPassageReportTask(GrandTreeQuest grandTreeQuest, int value2, Player player) {
        super(5);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        Player player = this.player;
        player.packetSender.closeInterfaces();
        DialogueManager.continueDialogue(this.player, 2781, 100, 0);
        this.stop();
    }
}

