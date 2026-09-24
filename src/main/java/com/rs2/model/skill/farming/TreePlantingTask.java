package com.rs2.model.skill.farming;

import com.rs2.model.skill.farming.FarmedTreeDefinition;
import com.rs2.model.skill.farming.TreePatch;
import com.rs2.model.skill.farming.TreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class TreePlantingTask
extends CycleEvent {
    private TreePatchManager manager;
    private final TreePatch patch;
    private final int saplingId;
    private final FarmedTreeDefinition definition;

    public TreePlantingTask(TreePatchManager treePatchManager, TreePatch treePatch, int saplingId, FarmedTreeDefinition farmedTreeDefinition) {
        this.manager = treePatchManager;
        this.patch = treePatch;
        this.saplingId = saplingId;
        this.definition = farmedTreeDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 0;
        this.manager.treeIds[this.patch.getIndex()] = this.saplingId;
        this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(this.definition.getGrowthCycleTicks());
        TreePatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getPlantingExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        TreePatchManager.getPlayer(this.manager).setActionLocked(false);
    }
}

