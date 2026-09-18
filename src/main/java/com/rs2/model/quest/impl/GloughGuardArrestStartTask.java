package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

public final class GloughGuardArrestStartTask
extends TickTask {
    private final Player player;
    private final Npc guardNpc;

    public GloughGuardArrestStartTask(GrandTreeQuest grandTreeQuest, int value2, Player player, Npc npc) {
        super(3);
        this.player = player;
        this.guardNpc = npc;
    }

    @Override
    public final void execute() {
        DialogueManager.continueDialogue(this.player, 2781, 100, 0);
        this.guardNpc.setScriptedMovementEnabled(true);
        this.stop();
    }
}

