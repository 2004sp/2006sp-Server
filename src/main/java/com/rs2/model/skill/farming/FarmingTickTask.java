package com.rs2.model.skill.farming;

import com.rs2.Server;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

/**
 * The legacy player-local Farming clock. Its first tick is slightly longer after
 * login; subsequent ticks repeat every five minutes. Growth occurs only when one
 * of these ticks intersects the globally aligned window for a crop family.
 */
public final class FarmingTickTask extends CycleEvent {
    private final Player player;

    public FarmingTickTask(Player player) {
        this.player = player;
    }

    public static int getInitialTickDelay() {
        return Server.getMinuteIntervalTicks() * 5 + 5;
    }

    @Override
    public void execute(CycleEventContainer container) {
        container.setTickDelay(Server.getMinuteIntervalTicks() * 5);
        this.player.getAllotmentPatchManager().processGrowth();
        this.player.getFlowerPatchManager().processGrowth();
        this.player.getHerbPatchManager().processGrowth();
        this.player.getHopsPatchManager().processGrowth();
        this.player.getBushPatchManager().processGrowth();
        this.player.getTreePatchManager().processGrowth();
        this.player.getFruitTreePatchManager().processGrowth();
        this.player.getSpecialTreePatchManager().processGrowth();
        this.player.getSpecialCropPatchManager().processGrowth();
    }

    @Override
    public void onStop() {
    }
}
