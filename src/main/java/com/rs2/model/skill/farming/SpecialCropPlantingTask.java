package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.SpecialCropDefinition;
import com.rs2.model.skill.farming.SpecialCropPatch;
import com.rs2.model.skill.farming.SpecialCropPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialCropPlantingTask
extends CycleEvent {
    private SpecialCropPatchManager manager;
    private final SpecialCropPatch patch;
    private final int seedId;
    private final SpecialCropDefinition definition;

    public SpecialCropPlantingTask(SpecialCropPatchManager specialCropPatchManager, SpecialCropPatch specialCropPatch, int seedId, SpecialCropDefinition specialCropDefinition) {
        this.manager = specialCropPatchManager;
        this.patch = specialCropPatch;
        this.seedId = seedId;
        this.definition = specialCropDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 0;
        this.manager.cropIds[this.patch.getIndex()] = this.seedId;
        this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(this.definition.getGrowthCycleTicks());
        SpecialCropPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getPlantingExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        SpecialCropPatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

