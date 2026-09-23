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
        if (worldObject == null) {
            this.stop();
            return;
        }

        if (!this.approachQueued) {
            Position approach = getApproachPosition(worldObject);
            this.approachX = approach.getX();
            this.approachY = approach.getY();
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

        int deltaX = 0;
        int deltaY = 0;
        switch (worldObject.getOrientation() & 3) {
            case 0:
                deltaX = this.player.getPosition().getX() < this.objectX ? 1 : -1;
                break;
            case 1:
                deltaY = this.player.getPosition().getY() > this.objectY ? -1 : 1;
                break;
            case 2:
                deltaX = this.player.getPosition().getX() > this.objectX ? -1 : 1;
                break;
            default:
                deltaY = this.player.getPosition().getY() < this.objectY ? 1 : -1;
                break;
        }

        Position destination = new Position(
                this.player.getPosition().getX() + deltaX,
                this.player.getPosition().getY() + deltaY,
                this.objectPlane);
        this.player.getUpdateState().setFacePosition(destination);
        this.player.getUpdateState().setAnimation(839);
        AgilityObstacleHandler.startForcedMovement(
                this.player, deltaX, deltaY, 1, 80, 2, true, 0, 0);
        this.stop();
    }

    private boolean isAtApproach() {
        return this.player.getPosition().getX() == this.approachX
                && this.player.getPosition().getY() == this.approachY
                && this.player.getPosition().getPlane() == this.objectPlane;
    }

    private Position getApproachPosition(WorldObject worldObject) {
        int playerX = this.player.getPosition().getX();
        int playerY = this.player.getPosition().getY();
        switch (worldObject.getOrientation() & 3) {
            case 0:
                return new Position(playerX < this.objectX
                        ? this.objectX - 1 : this.objectX,
                        this.objectY, this.objectPlane);
            case 1:
                return new Position(this.objectX,
                        playerY > this.objectY
                                ? this.objectY + 1 : this.objectY,
                        this.objectPlane);
            case 2:
                return new Position(playerX > this.objectX
                        ? this.objectX + 1 : this.objectX,
                        this.objectY, this.objectPlane);
            default:
                return new Position(this.objectX,
                        playerY < this.objectY
                                ? this.objectY - 1 : this.objectY,
                        this.objectPlane);
        }
    }
}
