package com.rs2.model.skill.agility;

import com.rs2.model.player.Player;
import com.rs2.model.skill.agility.AgilityObstacleHandler;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AgilityShortcutStartTask
extends CycleEvent {
    private final Player player;
    private final int deltaX;
    private final int deltaY;
    private final int forcedMovementEndDelay;
    private final int completionDelay;
    private final int experience;

    public AgilityShortcutStartTask(Player player, int deltaX, int deltaY, int forcedMovementEndDelay, int completionDelay, int experience) {
        this.player = player;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.forcedMovementEndDelay = forcedMovementEndDelay;
        this.completionDelay = completionDelay;
        this.experience = experience;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = this.player;
        player.packetSender.sendObjectAnimation(this.player.getInteractionTargetX(), this.player.getInteractionTargetY(), 3, 127);
        AgilityObstacleHandler.startForcedMovement(this.player, this.deltaX, this.deltaY, 1, this.forcedMovementEndDelay, this.completionDelay, true, this.experience, 0);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

