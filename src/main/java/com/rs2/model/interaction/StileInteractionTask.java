package com.rs2.model.interaction;

import com.rs2.model.Position;
import com.rs2.model.objects.ObjectDefinition;
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

    private boolean routeSelected;
    private boolean entryStepQueued;
    private boolean settledAtEntry;
    private int approachX;
    private int approachY;
    private int entryX;
    private int entryY;
    private int destinationX;
    private int destinationY;

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

        if (!this.routeSelected) {
            ObjectDefinition definition = ObjectDefinition.forId(this.objectId);
            if (definition == null || !selectRoute(worldObject, definition)) {
                this.stop();
                return;
            }
            this.routeSelected = true;

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

        if (!this.entryStepQueued) {
            if (!isAtApproach()) {
                this.stop();
                return;
            }

            this.player.getMovementQueue().clear();
            this.player.getMovementQueue().addStep(new Position(
                    this.entryX, this.entryY, this.objectPlane));
            this.entryStepQueued = true;
            return;
        }

        if (!isAtEntry()) {
            return;
        }

        // The entry tile is part of the stile's blocked footprint. Let the
        // normal one-tile walk finish visually before beginning the climb.
        if (!this.settledAtEntry) {
            this.player.getMovementQueue().clear();
            this.settledAtEntry = true;
            return;
        }

        int deltaX = this.destinationX - this.entryX;
        int deltaY = this.destinationY - this.entryY;

        this.player.getUpdateState().setFacePosition(new Position(
                this.objectX, this.objectY, this.objectPlane));
        AgilityObstacleHandler.startForcedMovement(
                this.player, deltaX, deltaY, 1, 80, 2, true, 0, 0, 839);
        this.player.setInteractionTargetId(-1);
        this.stop();
    }

    private boolean selectRoute(WorldObject worldObject,
                                ObjectDefinition definition) {
        int orientation = worldObject.getOrientation();
        int footprintWidth = Math.max(1,
                definition.getWidthForOrientation(orientation));
        int footprintLength = Math.max(1,
                definition.getLengthForOrientation(orientation));

        if (footprintWidth > footprintLength) {
            return selectXAxisRoute(footprintWidth);
        }
        if (footprintLength > footprintWidth) {
            return selectYAxisRoute(footprintLength);
        }

        // Square fallback. Real 2-tile stiles are resolved above from their
        // rotated footprint; this only handles any 1x1 variants safely.
        if ((orientation & 1) == 0) {
            return selectXAxisRoute(footprintWidth)
                    || selectYAxisRoute(footprintLength);
        }
        return selectYAxisRoute(footprintLength)
                || selectXAxisRoute(footprintWidth);
    }

    private boolean selectXAxisRoute(int footprintWidth) {
        int playerX = this.player.getPosition().getX();
        int playerY = this.player.getPosition().getY();

        int westApproachX = this.objectX - 1;
        int eastApproachX = this.objectX + footprintWidth;
        int westEntryX = this.objectX;
        int eastEntryX = this.objectX + footprintWidth - 1;

        int westDistance = Math.abs(playerX - westApproachX)
                + Math.abs(playerY - this.objectY);
        int eastDistance = Math.abs(playerX - eastApproachX)
                + Math.abs(playerY - this.objectY);

        if (westDistance <= eastDistance) {
            if (setRouteIfReachable(
                    westApproachX, this.objectY,
                    westEntryX, this.objectY,
                    eastApproachX, this.objectY)) {
                return true;
            }
            return setRouteIfReachable(
                    eastApproachX, this.objectY,
                    eastEntryX, this.objectY,
                    westApproachX, this.objectY);
        }

        if (setRouteIfReachable(
                eastApproachX, this.objectY,
                eastEntryX, this.objectY,
                westApproachX, this.objectY)) {
            return true;
        }
        return setRouteIfReachable(
                westApproachX, this.objectY,
                westEntryX, this.objectY,
                eastApproachX, this.objectY);
    }

    private boolean selectYAxisRoute(int footprintLength) {
        int playerX = this.player.getPosition().getX();
        int playerY = this.player.getPosition().getY();

        int southApproachY = this.objectY - 1;
        int northApproachY = this.objectY + footprintLength;
        int southEntryY = this.objectY;
        int northEntryY = this.objectY + footprintLength - 1;

        int southDistance = Math.abs(playerX - this.objectX)
                + Math.abs(playerY - southApproachY);
        int northDistance = Math.abs(playerX - this.objectX)
                + Math.abs(playerY - northApproachY);

        if (southDistance <= northDistance) {
            if (setRouteIfReachable(
                    this.objectX, southApproachY,
                    this.objectX, southEntryY,
                    this.objectX, northApproachY)) {
                return true;
            }
            return setRouteIfReachable(
                    this.objectX, northApproachY,
                    this.objectX, northEntryY,
                    this.objectX, southApproachY);
        }

        if (setRouteIfReachable(
                this.objectX, northApproachY,
                this.objectX, northEntryY,
                this.objectX, southApproachY)) {
            return true;
        }
        return setRouteIfReachable(
                this.objectX, southApproachY,
                this.objectX, southEntryY,
                this.objectX, northApproachY);
    }

    private boolean setRouteIfReachable(int approachX, int approachY,
                                        int entryX, int entryY,
                                        int destinationX, int destinationY) {
        if (this.player.getPosition().getPlane() != this.objectPlane) {
            return false;
        }

        boolean alreadyAtApproach = this.player.getPosition().getX() == approachX
                && this.player.getPosition().getY() == approachY;
        if (!alreadyAtApproach
                && !PathFinder.isReachable(this.player, approachX, approachY)) {
            return false;
        }

        this.approachX = approachX;
        this.approachY = approachY;
        this.entryX = entryX;
        this.entryY = entryY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        return true;
    }

    private boolean isAtApproach() {
        return this.player.getPosition().getX() == this.approachX
                && this.player.getPosition().getY() == this.approachY
                && this.player.getPosition().getPlane() == this.objectPlane;
    }

    private boolean isAtEntry() {
        return this.player.getPosition().getX() == this.entryX
                && this.player.getPosition().getY() == this.entryY
                && this.player.getPosition().getPlane() == this.objectPlane;
    }
}
