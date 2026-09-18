package com.rs2.model.skill.agility;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AgilityObstacleCompletionTask
extends CycleEvent {
    private final Player player;
    private final int deltaX;
    private final int deltaY;
    private final int animationId;
    private final int delayedAnimationId;
    private final int duration;
    private final double experience;
    private final String completionMessage;

    public AgilityObstacleCompletionTask(Player player, int deltaX, int deltaY, int animationId, int delayedAnimationId, int duration, double experience, String completionMessage) {
        this.player = player;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.animationId = animationId;
        this.delayedAnimationId = delayedAnimationId;
        this.duration = duration;
        this.experience = experience;
        this.completionMessage = completionMessage;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.setActionLocked(false);
        Player player = this.player;
        player.packetSender.queueAgilityMovement(this.deltaX, this.deltaY, true, this.animationId, this.delayedAnimationId, this.duration, this.experience, this.player.getMovementQueue().isRunning(), this.completionMessage);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

