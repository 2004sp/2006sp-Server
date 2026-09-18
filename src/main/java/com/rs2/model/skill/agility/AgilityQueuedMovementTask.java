package com.rs2.model.skill.agility;

import com.rs2.model.player.Player;
import com.rs2.model.skill.agility.AgilityQueuedMovementFinishTask;
import com.rs2.model.skill.agility.AgilityQueuedMovementStepTask;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.model.task.CycleEventHandler;

public final class AgilityQueuedMovementTask
extends CycleEvent {
    private final Player player;
    private final int deltaX;
    private final int deltaY;
    private final int forcedMovementEndDelay;
    private final int completionDelay;
    private final int experience;
    private final int animationId;

    public AgilityQueuedMovementTask(Player player, int deltaX, int deltaY, int forcedMovementEndDelay, int completionDelay, int experience, int animationId) {
        this.player = player;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.forcedMovementEndDelay = forcedMovementEndDelay;
        this.completionDelay = completionDelay;
        this.experience = experience;
        this.animationId = animationId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = this.player;
        player.packetSender.sendObjectAnimation(this.player.getInteractionTargetX(), this.player.getInteractionTargetY(), 3, 127);
        int value = this.animationId;
        int value2 = this.experience;
        boolean enabled = true;
        int value3 = this.completionDelay;
        int value4 = this.forcedMovementEndDelay;
        enabled = true;
        int value5 = this.deltaY;
        int value6 = this.deltaX;
        Player player2 = this.player;
        player2.setActionLocked(true);
        player2.getMovementQueue().clear();
        if (value > 0) {
            player2.setStandAnimationOverride(value);
            player2.setRunAnimationOverride(value);
            player2.setWalkAnimationOverride(value);
            player2.setAppearanceUpdateRequired(true);
        }
        value = 2;
        if (value6 > 0) {
            value = 1;
        } else if (value6 < 0) {
            value = 3;
        } else if (value5 > 0) {
            value = 0;
        }
        int position = player2.getPosition().getX() + value6;
        int position2 = player2.getPosition().getY() + value5;
        int position3 = player2.getPosition().getPlane();
        player2.forcedMovementActive = true;
        CycleEventHandler.getInstance().schedule(player2, new AgilityQueuedMovementStepTask(player2, value6, value5, 1, value4, value), 1);
        CycleEventHandler.getInstance().schedule(player2, new AgilityQueuedMovementFinishTask(player2, value2, true, position, position2, position3), value3 + 1);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

