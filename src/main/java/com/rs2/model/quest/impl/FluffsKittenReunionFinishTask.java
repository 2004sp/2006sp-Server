package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FluffsKittenReunionStartTask;
import com.rs2.model.task.TickTask;

public final class FluffsKittenReunionFinishTask
extends TickTask {
    private final Npc fluffs;
    private final Player player;
    private final Npc kitten;

    public FluffsKittenReunionFinishTask(FluffsKittenReunionStartTask fluffsKittenReunionStartTask, int value2, Npc npc, Player player, Npc npc2) {
        super(3);
        this.fluffs = npc;
        this.player = player;
        this.kitten = npc2;
    }

    @Override
    public final void execute() {
        CombatManager.finishDeath(this.fluffs, this.player, false);
        GameplayHelper.unregisterTemporaryNpc(this.kitten);
        this.player.setActionLocked(false);
        this.player.getDialogueManager().showOneLineStatement("Fluffs has run off home with her offspring.");
        this.stop();
    }
}
