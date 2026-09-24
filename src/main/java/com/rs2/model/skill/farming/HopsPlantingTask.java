package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.HopsDefinition;
import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HopsPlantingTask
extends CycleEvent {
    private HopsPatchManager manager;
    private final HopsPatch patch;
    private final int seedId;
    private final HopsDefinition definition;

    public HopsPlantingTask(HopsPatchManager hopsPatchManager, HopsPatch hopsPatch, int seedId, HopsDefinition hopsDefinition) {
        this.manager = hopsPatchManager;
        this.patch = hopsPatch;
        this.seedId = seedId;
        this.definition = hopsDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 0;
        this.manager.cropIds[this.patch.getIndex()] = this.seedId;
        this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(this.definition.getGrowthCycleTicks());
        HopsPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getPlantingExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        HopsPatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

