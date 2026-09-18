package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FruitTreePatch;
import com.rs2.model.skill.farming.FruitTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FruitTreeClearingTask
extends CycleEvent {
    private FruitTreePatchManager manager;
    private final int animationId;
    private final FruitTreePatch patch;

    public FruitTreeClearingTask(FruitTreePatchManager fruitTreePatchManager, int animationId, FruitTreePatch fruitTreePatch) {
        this.manager = fruitTreePatchManager;
        this.animationId = animationId;
        this.patch = fruitTreePatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        FruitTreePatchManager.getPlayer(this.manager).getUpdateState().setAnimation(this.animationId);
        boolean enabled = false;
        if (this.manager.growthStages[this.patch.getIndex()] <= 2) {
            int index = this.patch.getIndex();
            this.manager.growthStages[index] = this.manager.growthStages[index] + 1;
            FruitTreePatchManager.getPlayer(this.manager).getInventoryManager().addOrDropItem(new ItemStack(6055));
        } else {
            this.manager.growthStages[this.patch.getIndex()] = 3;
            enabled = true;
            cycleEventContainer.stop();
        }
        FruitTreePatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, 4.0);
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes();
        this.manager.refreshConfig();
        if (this.manager.growthStages[this.patch.getIndex()] == 3 && !enabled) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        FruitTreePatchManager.resetPatch(this.manager, this.patch.getIndex());
        Player player = FruitTreePatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You clear the patch.");
        FruitTreePatchManager.getPlayer(this.manager).setActionLocked(false);
        FruitTreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

