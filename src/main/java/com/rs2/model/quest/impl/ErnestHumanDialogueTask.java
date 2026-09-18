package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.task.TickTask;

public final class ErnestHumanDialogueTask
extends TickTask {
    private final Player player;

    public ErnestHumanDialogueTask(ErnestTheChickenQuest ernestTheChickenQuest, int value2, Player player) {
        super(value2);
        this.player = player;
    }

    @Override
    public final void execute() {
        Npc npc = Npc.findByDefinitionId(288);
        npc.transformToNpcId(287, 100);
        DialogueManager.continueDialogue(this.player, 287, 100, 0);
        this.stop();
    }
}

