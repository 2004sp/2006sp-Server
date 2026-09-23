package com.rs2.model.skill.agility;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AgilityMovementStepTask
extends CycleEvent {
    private final Player player;
    private final int forcedMovementEndXOffset;
    private final int forcedMovementEndYOffset;
    private final int forcedMovementStartDelay;
    private final int forcedMovementEndDelay;
    private final int forcedMovementDirection;
    private final int animationId;

    public AgilityMovementStepTask(Player player, int forcedMovementEndXOffset, int forcedMovementEndYOffset, int forcedMovementStartDelay, int forcedMovementEndDelay, int forcedMovementDirection) {
        this(player, forcedMovementEndXOffset, forcedMovementEndYOffset,
                forcedMovementStartDelay, forcedMovementEndDelay,
                forcedMovementDirection, -1);
    }

    public AgilityMovementStepTask(Player player, int forcedMovementEndXOffset, int forcedMovementEndYOffset, int forcedMovementStartDelay, int forcedMovementEndDelay, int forcedMovementDirection, int animationId) {
        this.player = player;
        this.forcedMovementEndXOffset = forcedMovementEndXOffset;
        this.forcedMovementEndYOffset = forcedMovementEndYOffset;
        this.forcedMovementStartDelay = forcedMovementStartDelay;
        this.forcedMovementEndDelay = forcedMovementEndDelay;
        this.forcedMovementDirection = forcedMovementDirection;
        this.animationId = animationId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.animationId >= 0) {
            this.player.getUpdateState().setAnimation(this.animationId);
        }
        this.player.getUpdateState().setForcedMovement(this.player, this.forcedMovementEndXOffset, this.forcedMovementEndYOffset, this.forcedMovementStartDelay, this.forcedMovementEndDelay, this.forcedMovementDirection);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

