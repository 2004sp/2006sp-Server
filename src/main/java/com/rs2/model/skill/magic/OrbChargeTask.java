package com.rs2.model.skill.magic;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class OrbChargeTask
extends CycleEvent {
    private int remainingCasts;
    private final Player player;

    public OrbChargeTask(int remainingCasts, Player player) {
        this.player = player;
        this.remainingCasts = remainingCasts;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.remainingCasts == 0 || !this.player.activeMagicSpellAction.tryStartCast()) {
            cycleEventContainer.stop();
            return;
        }
        --this.remainingCasts;
    }

    @Override
    public final void onStop() {
        this.player.resetAnimation();
    }
}
