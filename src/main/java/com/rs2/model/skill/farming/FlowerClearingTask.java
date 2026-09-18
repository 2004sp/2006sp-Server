package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FlowerClearingTask
extends CycleEvent {
    private FlowerPatchManager manager;
    private final int animationId;
    private final FlowerPatch patch;

    public FlowerClearingTask(FlowerPatchManager flowerPatchManager, int animationId, FlowerPatch flowerPatch) {
        this.manager = flowerPatchManager;
        this.animationId = animationId;
        this.patch = flowerPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        FlowerPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(this.animationId);
        boolean enabled = false;
        if (this.manager.growthStages[this.patch.getIndex()] <= 2) {
            int index = this.patch.getIndex();
            this.manager.growthStages[index] = this.manager.growthStages[index] + 1;
            FlowerPatchManager.getPlayer(this.manager).getInventoryManager().addOrDropItem(new ItemStack(6055));
        } else {
            this.manager.growthStages[this.patch.getIndex()] = 3;
            enabled = true;
            cycleEventContainer.stop();
        }
        FlowerPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, 4.0);
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes();
        this.manager.refreshConfig();
        if (this.manager.growthStages[this.patch.getIndex()] == 3 && !enabled) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        FlowerPatchManager.resetPatch(this.manager, this.patch.getIndex());
        Player player = FlowerPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You clear the patch.");
        FlowerPatchManager.getPlayer(this.manager).setActionLocked(false);
        FlowerPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

