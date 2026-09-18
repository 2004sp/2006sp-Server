package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.AllotmentCropDefinition;
import com.rs2.model.skill.farming.AllotmentPatch;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class AllotmentHarvestTask
extends CycleEvent {
    private AllotmentPatchManager manager;
    private final int actionSequence;
    private final AllotmentCropDefinition definition;
    private final AllotmentPatch patch;

    public AllotmentHarvestTask(AllotmentPatchManager allotmentPatchManager, int actionSequence, AllotmentCropDefinition allotmentCropDefinition, AllotmentPatch allotmentPatch) {
        this.manager = allotmentPatchManager;
        this.actionSequence = actionSequence;
        this.definition = allotmentCropDefinition;
        this.patch = allotmentPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!AllotmentPatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence) || AllotmentPatchManager.getPlayer(this.manager).getInventoryManager().getContainer().getFreeSlots() <= 0) {
            cycleEventContainer.stop();
            return;
        }
        AllotmentPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(830);
        Player player = AllotmentPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You harvest the crop, and get some vegetables.");
        AllotmentPatchManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(this.definition.getProduceItemId()));
        AllotmentPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHarvestExperience());
        if (!GameUtil.rollLevelScaledChance(this.definition.getHarvestChanceLow(), this.definition.getHarvestChanceHigh(), AllotmentPatchManager.getPlayer(this.manager).getSkillManager().getCurrentLevels()[19])) {
            int index = this.patch.getIndex();
            this.manager.harvestAmounts[index] = this.manager.harvestAmounts[index] - 1;
        }
        if (this.manager.harvestAmounts[this.patch.getIndex()] <= 0) {
            AllotmentPatchManager.resetPatch(this.manager, this.patch.getIndex());
            this.manager.growthStages[this.patch.getIndex()] = 3;
            this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes();
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        AllotmentPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

