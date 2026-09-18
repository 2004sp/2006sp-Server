package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.TreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class TreePruneTask
extends CycleEvent {
    private TreePatchManager manager;

    public TreePruneTask(TreePatchManager treePatchManager) {
        this.manager = treePatchManager;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = TreePatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You prune the area with your secateurs.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        TreePatchManager.getPlayer(this.manager).setActionLocked(false);
        TreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

