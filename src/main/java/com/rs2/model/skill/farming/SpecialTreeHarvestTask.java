package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialTreeDefinition;
import com.rs2.model.skill.farming.SpecialTreePatch;
import com.rs2.model.skill.farming.SpecialTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialTreeHarvestTask
extends CycleEvent {
    private SpecialTreePatchManager manager;
    private final int actionSequence;
    private final SpecialTreePatch patch;
    private final SpecialTreeDefinition definition;

    public SpecialTreeHarvestTask(SpecialTreePatchManager specialTreePatchManager, int actionSequence, SpecialTreePatch specialTreePatch, SpecialTreeDefinition specialTreeDefinition) {
        this.manager = specialTreePatchManager;
        this.actionSequence = actionSequence;
        this.patch = specialTreePatch;
        this.definition = specialTreeDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!SpecialTreePatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence) || SpecialTreePatchManager.getPlayer(this.manager).getInventoryManager().getContainer().getFreeSlots() <= 0) {
            cycleEventContainer.stop();
            return;
        }
        if (this.manager.patchStates[this.patch.getIndex()] == 3) {
            Player player = SpecialTreePatchManager.getPlayer(this.manager);
            player.packetSender.sendGameMessage("You examine the plant for signs of disease and find that it's in perfect health.");
            SpecialTreePatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHealthCheckExperience());
            this.manager.patchStates[this.patch.getIndex()] = 0;
            this.manager.calquatRegrowthFlags[this.patch.getIndex()] = this.definition == SpecialTreeDefinition.CALQUAT;
            this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes() - (long)this.definition.getTotalGrowthTicks();
            this.manager.recalculateRegrowthStage(this.patch.getIndex());
            cycleEventContainer.stop();
            return;
        }
        Player player = SpecialTreePatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You harvest the crop, and pick some " + ItemDefinition.forId(this.definition.getProduceItemId()).getName().toLowerCase() + ".");
        SpecialTreePatchManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(this.definition.getProduceItemId()));
        SpecialTreePatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHarvestExperience());
        int index = this.patch.getIndex();
        this.manager.growthStages[index] = this.manager.growthStages[index] - 1;
        this.manager.refreshConfig();
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        SpecialTreePatchManager.getPlayer(this.manager).setActionLocked(false);
        SpecialTreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

