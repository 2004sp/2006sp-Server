package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FlowerDefinition;
import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FlowerHarvestTask
extends CycleEvent {
    private FlowerPatchManager manager;
    private final int actionSequence;
    private final FlowerPatch patch;
    private final FlowerDefinition definition;

    public FlowerHarvestTask(FlowerPatchManager flowerPatchManager, int actionSequence, FlowerPatch flowerPatch, FlowerDefinition flowerDefinition) {
        this.manager = flowerPatchManager;
        this.actionSequence = actionSequence;
        this.patch = flowerPatch;
        this.definition = flowerDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!FlowerPatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        FlowerPatchManager.resetPatch(this.manager, this.patch.getIndex());
        this.manager.growthStages[this.patch.getIndex()] = 3;
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes();
        FlowerPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(830);
        Player player = FlowerPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You harvest the crop.");
        FlowerPatchManager.getPlayer(this.manager).getInventoryManager().addOrDropItem(new ItemStack(this.definition.getProduceItemId(), this.definition.getProduceItemId() == 1973 || this.definition.getProduceItemId() == 225 ? 3 : 1));
        FlowerPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHarvestExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        FlowerPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

