package com.rs2.net.packet.handler;

import com.rs2.model.EntityTargetMovement;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.handler.PlayerInteractionPacketHandler;

public final class FollowPlayerTask
extends TickTask {
    private final Player targetPlayer;
    private final Player requestingPlayer;
    private final int actionSequence;

    public FollowPlayerTask(PlayerInteractionPacketHandler playerInteractionPacketHandler, int value3, Player player, Player player2, int actionSequence) {
        super(1);
        this.targetPlayer = player;
        this.requestingPlayer = player2;
        this.actionSequence = actionSequence;
    }

    @Override
    public final void execute() {
        if (this.targetPlayer == null || this.targetPlayer.isDead() || !this.requestingPlayer.isCurrentActionSequence(this.actionSequence)) {
            EntityTargetMovement.clearMovementTarget(this.requestingPlayer);
            this.requestingPlayer.setInteractionTarget(null);
            this.requestingPlayer.getMovementQueue().clear();
            this.stop();
            return;
        }
        if (this.requestingPlayer.isWithinReach(this.targetPlayer, 1) && !this.requestingPlayer.isOverlapping(this.targetPlayer)) {
            this.requestingPlayer.getUpdateState().setAnimation(1833);
            EntityTargetMovement.clearMovementTarget(this.requestingPlayer);
            this.requestingPlayer.getUpdateState().setFacePosition(this.targetPlayer.getPosition());
            this.requestingPlayer.setInteractionTarget(null);
            this.requestingPlayer.getMovementQueue().clear();
            this.stop();
        }
    }
}

