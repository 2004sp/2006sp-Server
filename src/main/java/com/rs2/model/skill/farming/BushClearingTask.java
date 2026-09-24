package com.rs2.model.skill.farming;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.BushPatch;
import com.rs2.model.skill.farming.BushPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class BushClearingTask
extends CycleEvent {
    private BushPatchManager manager;
    private final int animationId;
    private final BushPatch patch;

    public BushClearingTask(BushPatchManager bushPatchManager, int animationId, BushPatch bushPatch) {
        this.manager = bushPatchManager;
        this.animationId = animationId;
        this.patch = bushPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        BushPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(this.animationId);
        boolean enabled = false;
        if (this.manager.growthStages[this.patch.getIndex()] <= 2) {
            int index = this.patch.getIndex();
            this.manager.growthStages[index] = this.manager.growthStages[index] + 1;
            BushPatchManager.getPlayer(this.manager).getInventoryManager().addOrDropItem(new ItemStack(6055));
        } else {
            this.manager.growthStages[this.patch.getIndex()] = 3;
            enabled = true;
            cycleEventContainer.stop();
        }
        BushPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, 4.0);
        this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(5);
        this.manager.refreshConfig();
        if (this.manager.growthStages[this.patch.getIndex()] == 3 && !enabled) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        BushPatchManager.resetPatch(this.manager, this.patch.getIndex());
        Player player = BushPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You clear the patch.");
        BushPatchManager.getPlayer(this.manager).setActionLocked(false);
        BushPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

