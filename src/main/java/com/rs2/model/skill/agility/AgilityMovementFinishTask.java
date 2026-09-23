package com.rs2.model.skill.agility;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AgilityMovementFinishTask
extends CycleEvent {
    private final Player player;
    private final int experience;
    private final boolean unlockPlayer;
    private final int destinationX;
    private final int destinationY;
    private final int destinationPlane;
    private final boolean unlockAfterLandingSync;

    public AgilityMovementFinishTask(Player player, int experience, boolean unlockPlayer, int destinationX, int destinationY, int destinationPlane) {
        this(player, experience, unlockPlayer, destinationX, destinationY,
                destinationPlane, false);
    }

    public AgilityMovementFinishTask(Player player, int experience,
                                     boolean unlockPlayer,
                                     int destinationX, int destinationY,
                                     int destinationPlane,
                                     boolean unlockAfterLandingSync) {
        this.player = player;
        this.experience = experience;
        this.unlockPlayer = unlockPlayer;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationPlane = destinationPlane;
        this.unlockAfterLandingSync = unlockAfterLandingSync;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.getSkillManager().addExperience(16, this.experience);
        this.player.getUpdateState().setForcedMovementUpdateRequired(false);
        this.player.forcedMovementActive = false;

        Position destination = new Position(
                this.destinationX, this.destinationY, this.destinationPlane);
        if (this.unlockAfterLandingSync) {
            // Keep the stile action lock in place while moveTo() synchronizes
            // the server with the tile the client has already reached. If we
            // unlock first, moveTo() re-locks the player and its post-teleport
            // task does not release that lock until the following game tick.
            this.player.moveTo(destination);
            if (this.unlockPlayer) {
                this.player.setActionLocked(false);
            }
        } else {
            if (this.unlockPlayer) {
                this.player.setActionLocked(false);
            }
            this.player.moveTo(destination);
        }

        this.player.getUpdateState().clearForcedMovement();
        this.player.setRunAnimationOverride(-1);
        this.player.setWalkAnimationOverride(-1);
        this.player.setAppearanceUpdateRequired(true);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

