package com.rs2.model.skill.farming;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialCropDefinition;
import com.rs2.model.skill.farming.SpecialCropPatch;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialCropHarvestTask
extends CycleEvent {
    private SpecialCropPatchManager manager;
    private final int actionSequence;
    private final SpecialCropPatch patch;
    private final SpecialCropDefinition definition;

    public SpecialCropHarvestTask(SpecialCropPatchManager specialCropPatchManager, int actionSequence, SpecialCropPatch specialCropPatch, SpecialCropDefinition specialCropDefinition) {
        this.manager = specialCropPatchManager;
        this.actionSequence = actionSequence;
        this.patch = specialCropPatch;
        this.definition = specialCropDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!SpecialCropPatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence) || SpecialCropPatchManager.getPlayer(this.manager).getInventoryManager().getContainer().getFreeSlots() <= 0) {
            cycleEventContainer.stop();
            return;
        }
        if (this.manager.patchStates[this.patch.getIndex()] == 3) {
            Player player = SpecialCropPatchManager.getPlayer(this.manager);
            player.packetSender.sendGameMessage("You examine the plant for signs of disease and find that it's in perfect health.");
            SpecialCropPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHealthCheckExperience());
            this.manager.patchStates[this.patch.getIndex()] = 0;
            this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(this.definition.getGrowthCycleTicks()) - (long)this.definition.getTotalGrowthTicks();
            this.manager.recalculateRegrowthStage(this.patch.getIndex());
            cycleEventContainer.stop();
            return;
        }
        Player player = SpecialCropPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You harvest the crop, and pick some " + ItemDefinition.forId(this.definition.getProduceItemId()).getName().toLowerCase() + ".");
        SpecialCropPatchManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(this.definition.getProduceItemId()));
        SpecialCropPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHarvestExperience());
        switch (this.definition) {
            case BELLADONNA: {
                SpecialCropPatchManager.resetPatch(this.manager, this.patch.getIndex());
                this.manager.growthStages[this.patch.getIndex()] = 3;
                this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(5);
                break;
            }
            case CACTUS: {
                int index = this.patch.getIndex();
                this.manager.growthStages[index] = this.manager.growthStages[index] - 1;
                break;
            }
            case MUSHROOM: {
                int index2 = this.patch.getIndex();
                this.manager.growthStages[index2] = this.manager.growthStages[index2] + 1;
                if (this.manager.growthStages[this.patch.getIndex()] != 16) break;
                SpecialCropPatchManager.resetPatch(this.manager, this.patch.getIndex());
                this.manager.growthStages[this.patch.getIndex()] = 3;
                this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(5);
            }
        }
        this.manager.refreshConfig();
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        SpecialCropPatchManager.getPlayer(this.manager).setActionLocked(false);
        SpecialCropPatchManager.getPlayer(this.manager).resetAnimation();
    }
}
