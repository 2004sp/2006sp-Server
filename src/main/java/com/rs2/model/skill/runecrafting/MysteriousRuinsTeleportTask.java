package com.rs2.model.skill.runecrafting;

import com.rs2.model.player.Player;
import com.rs2.model.skill.runecrafting.RunecraftingAltarDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class MysteriousRuinsTeleportTask
extends CycleEvent {
    private final Player player;
    private final RunecraftingAltarDefinition altarDefinition;

    public MysteriousRuinsTeleportTask(Player player, RunecraftingAltarDefinition runecraftingAltarDefinition) {
        this.player = player;
        this.altarDefinition = runecraftingAltarDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.packetSender.sendGameMessage("You feel a powerful force take hold of you...");
        Object value = this.altarDefinition;
        this.player.moveTo(((RunecraftingAltarDefinition)((Object)value)).altarPosition);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

