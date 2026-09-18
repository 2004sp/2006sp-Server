package com.rs2.model.skill.agility;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AgilityQueuedMovementFinishTask
extends CycleEvent {
    private final Player player;
    private final int experience;
    private final boolean unlockPlayer;
    private final int destinationX;
    private final int destinationY;
    private final int destinationPlane;

    public AgilityQueuedMovementFinishTask(Player player, int experience, boolean unlockPlayer, int destinationX, int destinationY, int destinationPlane) {
        this.player = player;
        this.experience = experience;
        this.unlockPlayer = unlockPlayer;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationPlane = destinationPlane;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.getSkillManager().addExperience(16, this.experience);
        Player player = this.player;
        player.packetSender.sendGameMessage("...You make it safely to the other side.");
        this.player.getUpdateState().setForcedMovementUpdateRequired(false);
        if (this.unlockPlayer) {
            this.player.setActionLocked(false);
        }
        this.player.forcedMovementActive = false;
        this.player.moveTo(new Position(this.destinationX, this.destinationY, this.destinationPlane));
        this.player.getUpdateState().clearForcedMovement();
        this.player.setStandAnimationOverride(-1);
        this.player.setRunAnimationOverride(-1);
        this.player.setWalkAnimationOverride(-1);
        this.player.setAppearanceUpdateRequired(true);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

