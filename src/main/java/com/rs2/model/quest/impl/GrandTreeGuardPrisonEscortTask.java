package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

public final class GrandTreeGuardPrisonEscortTask
extends TickTask {
    private final Player player;
    private final int questId;

    public GrandTreeGuardPrisonEscortTask(GrandTreeQuest grandTreeQuest, int value3, Player player, int questId) {
        super(3);
        this.player = player;
        this.questId = questId;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.player.moveTo(new Position(2464, 3496, 3));
        this.player.setQuestState(this.questId, 9);
        DialogueManager.continueDialogue(this.player, 673, 1, 0);
        this.stop();
    }
}
