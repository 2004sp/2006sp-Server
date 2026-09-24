package com.rs2.model.skill.farming;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.HopsDefinition;
import com.rs2.model.skill.farming.HopsPatch;
import com.rs2.model.skill.farming.HopsPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class HopsHarvestTask
extends CycleEvent {
    private HopsPatchManager manager;
    private final int actionSequence;
    private final HopsDefinition definition;
    private final HopsPatch patch;

    public HopsHarvestTask(HopsPatchManager hopsPatchManager, int actionSequence, HopsDefinition hopsDefinition, HopsPatch hopsPatch) {
        this.manager = hopsPatchManager;
        this.actionSequence = actionSequence;
        this.definition = hopsDefinition;
        this.patch = hopsPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!HopsPatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence) || HopsPatchManager.getPlayer(this.manager).getInventoryManager().getContainer().getFreeSlots() <= 0) {
            cycleEventContainer.stop();
            return;
        }
        HopsPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(830);
        Player player = HopsPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You harvest the crop, and get some vegetables.");
        HopsPatchManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(this.definition.getProduceItemId()));
        HopsPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHarvestExperience());
        if (!GameUtil.rollLevelScaledChance(this.definition.getHarvestChanceLow(), this.definition.getHarvestChanceHigh(), HopsPatchManager.getPlayer(this.manager).getSkillManager().getCurrentLevels()[19])) {
            int index = this.patch.getIndex();
            this.manager.harvestAmounts[index] = this.manager.harvestAmounts[index] - 1;
        }
        if (this.manager.harvestAmounts[this.patch.getIndex()] <= 0) {
            HopsPatchManager.resetPatch(this.manager, this.patch.getIndex());
            this.manager.growthStages[this.patch.getIndex()] = 3;
            this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(5);
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        HopsPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

