package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.AllotmentPatch;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AllotmentClearingTask
extends CycleEvent {
    private AllotmentPatchManager manager;
    private final int animationId;
    private final AllotmentPatch patch;

    public AllotmentClearingTask(AllotmentPatchManager allotmentPatchManager, int animationId, AllotmentPatch allotmentPatch) {
        this.manager = allotmentPatchManager;
        this.animationId = animationId;
        this.patch = allotmentPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        AllotmentPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(this.animationId);
        boolean enabled = false;
        if (this.manager.growthStages[this.patch.getIndex()] <= 2) {
            int index = this.patch.getIndex();
            this.manager.growthStages[index] = this.manager.growthStages[index] + 1;
            AllotmentPatchManager.getPlayer(this.manager).getInventoryManager().addOrDropItem(new ItemStack(6055));
        } else {
            this.manager.growthStages[this.patch.getIndex()] = 3;
            cycleEventContainer.stop();
            enabled = true;
        }
        AllotmentPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, 4.0);
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes();
        this.manager.refreshConfig();
        if (this.manager.growthStages[this.patch.getIndex()] == 3 && !enabled) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        AllotmentPatchManager.resetPatch(this.manager, this.patch.getIndex());
        Player player = AllotmentPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You clear the patch.");
        AllotmentPatchManager.getPlayer(this.manager).setActionLocked(false);
        AllotmentPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

