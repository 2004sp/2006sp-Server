package com.rs2.model.skill.agility;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AgilityQueuedMovementStepTask
extends CycleEvent {
    private final Player player;
    private final int forcedMovementEndXOffset;
    private final int forcedMovementEndYOffset;
    private final int forcedMovementStartDelay;
    private final int forcedMovementEndDelay;
    private final int forcedMovementDirection;

    public AgilityQueuedMovementStepTask(Player player, int forcedMovementEndXOffset, int forcedMovementEndYOffset, int forcedMovementStartDelay, int forcedMovementEndDelay, int forcedMovementDirection) {
        this.player = player;
        this.forcedMovementEndXOffset = forcedMovementEndXOffset;
        this.forcedMovementEndYOffset = forcedMovementEndYOffset;
        this.forcedMovementStartDelay = forcedMovementStartDelay;
        this.forcedMovementEndDelay = forcedMovementEndDelay;
        this.forcedMovementDirection = forcedMovementDirection;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.getUpdateState().setForcedMovement(this.player, this.forcedMovementEndXOffset, this.forcedMovementEndYOffset, this.forcedMovementStartDelay, this.forcedMovementEndDelay, this.forcedMovementDirection);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

