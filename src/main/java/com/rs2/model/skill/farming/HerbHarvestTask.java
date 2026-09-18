package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.HerbDefinition;
import com.rs2.model.skill.farming.HerbPatch;
import com.rs2.model.skill.farming.HerbPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class HerbHarvestTask
extends CycleEvent {
    private HerbPatchManager manager;
    private final int actionSequence;
    private final HerbDefinition definition;
    private final HerbPatch patch;

    public HerbHarvestTask(HerbPatchManager herbPatchManager, int actionSequence, HerbDefinition herbDefinition, HerbPatch herbPatch) {
        this.manager = herbPatchManager;
        this.actionSequence = actionSequence;
        this.definition = herbDefinition;
        this.patch = herbPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!HerbPatchManager.getPlayer(this.manager).isCurrentActionSequence(this.actionSequence) || HerbPatchManager.getPlayer(this.manager).getInventoryManager().getContainer().getFreeSlots() <= 0) {
            cycleEventContainer.stop();
            return;
        }
        HerbPatchManager.getPlayer(this.manager).getUpdateState().setAnimation(2279);
        Player player = HerbPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You harvest the crop, and get some herbs.");
        HerbPatchManager.getPlayer(this.manager).getInventoryManager().addItem(new ItemStack(this.definition.getProduceItemId()));
        HerbPatchManager.getPlayer(this.manager).getSkillManager().addExperience(19, this.definition.getHarvestExperience());
        if (!GameUtil.rollLevelScaledChance(this.definition.getHarvestChanceLow(), this.definition.getHarvestChanceHigh(), HerbPatchManager.getPlayer(this.manager).getSkillManager().getCurrentLevels()[19])) {
            int index = this.patch.getIndex();
            this.manager.harvestAmounts[index] = this.manager.harvestAmounts[index] - 1;
        }
        if (this.manager.harvestAmounts[this.patch.getIndex()] <= 0) {
            HerbPatchManager.resetPatch(this.manager, this.patch.getIndex());
            this.manager.growthStages[this.patch.getIndex()] = 3;
            this.manager.lastUpdateTicks[this.patch.getIndex()] = Server.getElapsedMinutes();
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        HerbPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

