package com.rs2.model.skill.farming;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class HopsClearingTask
extends CycleEvent {
    private HopsPatchManager manager;
    private final int animationId;
    private final HopsPatch patch;

    public HopsClearingTask(HopsPatchManager hopsPatchManager, int animationId, HopsPatch hopsPatch) {
        this.manager = hopsPatchManager;
        this.animationId = animationId;
        this.patch = hopsPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        HopsPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(this.animationId);
        boolean enabled = false;
        if (this.manager.growthStages[this.patch.getIndex()] <= 2) {
            int index = this.patch.getIndex();
            this.manager.growthStages[index] = this.manager.growthStages[index] + 1;
            HopsPatchManager.getPlayer(this.manager).getInventoryManager().addOrDropItem(new ItemStack(6055));
        } else {
            this.manager.growthStages[this.patch.getIndex()] = 3;
            enabled = true;
            cycleEventContainer.stop();
        }
        HopsPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, 4.0);
        this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(5);
        this.manager.refreshConfig();
        if (this.manager.growthStages[this.patch.getIndex()] == 3 && !enabled) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        HopsPatchManager.resetPatch(this.manager, this.patch.getIndex());
        Player player = HopsPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You clear the patch.");
        HopsPatchManager.getPlayer(this.manager).setActionLocked(false);
        HopsPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

