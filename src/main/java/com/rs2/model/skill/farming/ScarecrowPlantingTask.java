package com.rs2.model.skill.farming;

import com.rs2.model.player.Player;
import com.rs2.model.skill.farming.FlowerPatch;
import com.rs2.model.skill.farming.FlowerPatchManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class ScarecrowPlantingTask
extends CycleEvent {
    private FlowerPatchManager manager;
    private final FlowerPatch patch;

    public ScarecrowPlantingTask(FlowerPatchManager flowerPatchManager, FlowerPatch flowerPatch) {
        this.manager = flowerPatchManager;
        this.patch = flowerPatch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = FlowerPatchManager.getPlayer(this.manager);
        player.packetSender.sendGameMessage("You put a scarecrow on the flower patch, and some weeds start to grow around it.");
        this.manager.cropIds[this.patch.getIndex()] = 36;
        this.manager.growthStages[this.patch.getIndex()] = 4;
        this.manager.lastUpdateTicks[this.patch.getIndex()] = FarmingPatchUtils.getCurrentGrowthCycleStart(5);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.manager.refreshConfig();
        FlowerPatchManager.getPlayer(this.manager).setActionLocked(false);
        FlowerPatchManager.getPlayer(this.manager).resetAnimation();
    }
}

