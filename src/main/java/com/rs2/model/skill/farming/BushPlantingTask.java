package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.BushDefinition;
import com.rs2.model.skill.farming.BushPatch;
import com.rs2.model.skill.farming.BushPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class BushPlantingTask
extends CycleEvent {
    private BushPatchManager manager;
    private final BushPatch patch;
    private final int seedId;
    private final BushDefinition definition;

    public BushPlantingTask(BushPatchManager bushPatchManager, BushPatch bushPatch, int seedId, BushDefinition bushDefinition) {
        this.manager = bushPatchManager;
        this.patch = bushPatch;
        this.seedId = seedId;
        this.definition = bushDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 0;
        this.manager.cropIds[this.patch.getIndex()] = this.seedId;
        this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(this.definition.getGrowthCycleTicks());
        BushPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getPlantingExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        BushPatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

