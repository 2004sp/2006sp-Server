package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.skill.farming.SpecialTreeDefinition;
import com.rs2.model.skill.farming.SpecialTreePatch;
import com.rs2.model.skill.farming.SpecialTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialTreePlantingTask
extends CycleEvent {
    private SpecialTreePatchManager manager;
    private final SpecialTreePatch patch;
    private final int saplingId;
    private final SpecialTreeDefinition definition;

    public SpecialTreePlantingTask(SpecialTreePatchManager specialTreePatchManager, SpecialTreePatch specialTreePatch, int saplingId, SpecialTreeDefinition specialTreeDefinition) {
        this.manager = specialTreePatchManager;
        this.patch = specialTreePatch;
        this.saplingId = saplingId;
        this.definition = specialTreeDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.manager.patchStates[this.patch.getIndex()] = 0;
        this.manager.treeIds[this.patch.getIndex()] = this.saplingId;
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes();
        SpecialTreePatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getPlantingExperience());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        SpecialTreePatchManager.getPlayer(this.manager).setActionLocked(false);
        SpecialTreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

