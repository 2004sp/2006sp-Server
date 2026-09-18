package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.SpecialTreePatch;
import com.rs2.model.skill.farming.SpecialTreePatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class SpecialTreeClearingTask
extends CycleEvent {
    private SpecialTreePatchManager manager;
    private final int animationId;
    private final SpecialTreePatch patch;

    public SpecialTreeClearingTask(SpecialTreePatchManager specialTreePatchManager, int animationId, SpecialTreePatch specialTreePatch) {
        this.manager = specialTreePatchManager;
        this.animationId = animationId;
        this.patch = specialTreePatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        SpecialTreePatchManager.getPlayer(this.manager).getUpdateState().setAnimation(this.animationId);
        boolean enabled = false;
        if (this.manager.growthStages[this.patch.getIndex()] <= 2) {
            int index = this.patch.getIndex();
            this.manager.growthStages[index] = this.manager.growthStages[index] + 1;
            SpecialTreePatchManager.getPlayer(this.manager).getInventoryManager().addOrDropItem(new ItemStack(6055));
        } else {
            this.manager.growthStages[this.patch.getIndex()] = 3;
            enabled = true;
            cycleEventContainer.stop();
        }
        SpecialTreePatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, 4.0);
        this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes();
        this.manager.refreshConfig();
        if (this.manager.growthStages[this.patch.getIndex()] == 3 && !enabled) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        SpecialTreePatchManager.resetPatch(this.manager, this.patch.getIndex());
        Player player = SpecialTreePatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You clear the patch.");
        SpecialTreePatchManager.getPlayer(this.manager).setActionLocked(false);
        SpecialTreePatchManager.getPlayer(this.manager).resetAnimation();
    }
}

