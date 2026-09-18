package com.rs2.model.gameplay.abyss;

import com.rs2.model.gameplay.abyss.AbyssManager;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class AbyssObstacleEvent
extends CycleEvent {
    private int remainingObstaclePhase;
    private final Player player;
    private final String obstacleAction;
    private final int temporaryObjectId;
    private final int objectX;
    private final int objectY;
    private final LoadedWorldObject sourceObject;
    private final int finalObjectId;
    private final int destinationX;
    private final int destinationY;

    public AbyssObstacleEvent(int remainingObstaclePhase, Player player, String obstacleAction, int temporaryObjectId, int objectX, int objectY, LoadedWorldObject loadedWorldObject, int finalObjectId, int destinationX, int destinationY) {
        this.player = player;
        this.obstacleAction = obstacleAction;
        this.temporaryObjectId = temporaryObjectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.sourceObject = loadedWorldObject;
        this.finalObjectId = finalObjectId;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.remainingObstaclePhase = remainingObstaclePhase;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        AbyssManager.replayObstacleAttemptAnimation(this.player, this.obstacleAction);
        if (this.remainingObstaclePhase == 2) {
            if (GameUtil.randomInclusive(3) == 0) {
                --this.remainingObstaclePhase;
                new DynamicObject(this.temporaryObjectId, this.objectX, this.objectY, this.player.getPosition().getPlane(), this.sourceObject.getOrientation(), this.sourceObject.getType(), this.sourceObject.getWorldObject().getObjectId(), 1000);
                return;
            }
            AbyssManager.sendObstacleFailureMessage(this.player, this.obstacleAction);
            cycleEventContainer.stop();
            return;
        }
        if (this.remainingObstaclePhase == 1) {
            --this.remainingObstaclePhase;
            ObjectManager.getInstance().removeDynamicObjectAt(this.objectX, this.objectY, this.player.getPosition().getPlane(), 10);
            new DynamicObject(this.finalObjectId, this.objectX, this.objectY, this.player.getPosition().getPlane(), this.sourceObject.getOrientation(), this.sourceObject.getType(), this.sourceObject.getWorldObject().getObjectId(), 20);
            return;
        }
        if (this.remainingObstaclePhase == 0) {
            AbyssManager.sendObstacleSuccessMessage(this.player, this.obstacleAction);
            AbyssManager.scheduleDelayedObstacleMove(this.player, this.destinationX, this.destinationY);
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

