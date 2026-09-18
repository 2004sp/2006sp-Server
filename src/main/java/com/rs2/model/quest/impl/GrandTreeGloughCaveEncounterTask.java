package com.rs2.model.quest.impl;

import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

public final class GrandTreeGloughCaveEncounterTask
extends TickTask {
    private final Player player;

    public GrandTreeGloughCaveEncounterTask(GrandTreeQuest grandTreeQuest, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        Entity entity = this.player;
        ((Player)entity).packetSender.sendGameMessage("You can hear footsteps approaching.");
        DialogueManager.continueDialogue(this.player, 671, 100, 0);
        entity = new Npc(671);
        GameplayHelper.replaceOwnedRoamingNpcAtPosition(this.player, (Npc)entity, 2484, 9864, 0, -1, false, false);
        ((Npc)entity).queueScriptedPath(new Position[]{new Position(2490, 9864, 0)});
        this.player.setInteractionTarget(entity);
        this.player.setActionLocked(false);
        this.stop();
    }
}

