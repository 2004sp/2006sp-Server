package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MerlinsCrystalQuest;
import com.rs2.model.task.TickTask;

public final class MerlinsCrystalThrantaxSummonTask
extends TickTask {
    private final Player player;

    public MerlinsCrystalThrantaxSummonTask(MerlinsCrystalQuest merlinsCrystalQuest, int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        Npc npc = new Npc(238);
        GameplayHelper.replaceOwnedRoamingNpcAtPosition(this.player, npc, 2780, 3515, 0, 86, false, false);
        DialogueManager.continueDialogue(this.player, 238, 100, 0);
        npc.setScriptedMovementEnabled(true);
        this.stop();
    }
}

