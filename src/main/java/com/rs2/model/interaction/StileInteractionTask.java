package com.rs2.model.interaction;

import com.rs2.model.Position;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.agility.AgilityObstacleHandler;
import com.rs2.model.task.TickTask;
import com.rs2.util.path.PathFinder;

public final class StileInteractionTask extends TickTask {
    private final Player player;
    private final int actionSequence;
    private final int objectId;
    private final int objectX;
    private final int objectY;
    private final int objectPlane;
    private boolean approachQueued;
    private int approachX;
    private int approachY;

    public StileInteractionTask(Player player, int actionSequence,
                                int objectId, int objectX, int objectY,
                                int objectPlane) {
        super(1, true);
        this.player = player;
        this.actionSequence = actionSequence;
        this.objectId = objectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.objectPlane = objectPlane;
    }

    @Override
    public void execute() {
        if (this.player == null
                || !this.player.isCurrentActionSequence(this.actionSequence)) {
            this.stop();
            return;
        }
        if (this.player.isStunned()) {
            return;
        }

        WorldObject worldObject = SkillActionHelper.findWorldObjectById(
                this.objectId, this.objectX, this.objectY, this.objectPlane);
        if (worldObject == null || worldObject.getType() != 10) {
            this.stop();
            return;
        }

        if (!this.approachQueued) {
            this.approachX = this.player.getPosition().getX() < this.objectX
                    ? this.objectX - 1
                    : this.objectX + 1;
            this.approachY = this.objectY;
            this.approachQueued = true;

            this.player.getMovementQueue().clear();
            if (!isAtApproach()) {
                if (!PathFinder.findPath(this.player,
                        this.approachX, this.approachY,
                        false, 0, 0)) {
                    this.stop();
                }
                return;
            }
        }

        if (this.player.isMoving()) {
            return;
        }
        if (!isAtApproach()) {
            this.stop();
            return;
        }

        int deltaX = this.approachX < this.objectX ? 2 : -2;
        Position stilePosition = new Position(
                this.objectX, this.objectY, this.objectPlane);
        this.player.getUpdateState().setFacePosition(stilePosition);
        this.player.getUpdateState().setAnimation(839);
        AgilityObstacleHandler.startForcedMovement(
                this.player, deltaX, 0, 1, 80, 2, true, 0, 0);
        this.stop();
    }

    private boolean isAtApproach() {
        return this.player.getPosition().getX() == this.approachX
                && this.player.getPosition().getY() == this.approachY
                && this.player.getPosition().getPlane() == this.objectPlane;
    }
}
