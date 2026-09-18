package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FarmedTreeDefinition;
import com.rs2.model.skill.farming.TreePatch;
import com.rs2.model.skill.farming.TreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class TreeHealthCheckTask
extends CycleEvent {
    private TreePatchManager manager;
    private final int actionSequence;
    private final FarmedTreeDefinition definition;
    private final TreePatch patch;

    public TreeHealthCheckTask(TreePatchManager treePatchManager, int actionSequence, FarmedTreeDefinition farmedTreeDefinition, TreePatch treePatch) {
        this.manager = treePatchManager;
        this.actionSequence = actionSequence;
        this.definition = farmedTreeDefinition;
        this.patch = treePatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!TreePatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        Player player = TreePatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You examine the tree for signs of disease and find that it is in perfect health");
        TreePatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHealthCheckExperience());
        this.manager.patchStates[this.patch.getIndex()] = 6;
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
    }
}

