package com.rs2.model.skill.agility;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

/**
 * Segmented forced movement for the Gnome Stronghold double pipe.
 *
 * The squeeze animation is restarted for each movement section instead of
 * being stretched across the whole obstacle. The middle section remains
 * hidden inside the pipe while the final section brings the player back out.
 */
public final class AgilityPipeTraversalTask extends CycleEvent {
    private static final int PIPE_ANIMATION = 749;
    private static final int EXACT_MOVE_START_CYCLE = 30;
    private static final int OUTER_SEGMENT_END_CYCLE = 126;
    private static final int MIDDLE_SEGMENT_END_CYCLE = 158;
    private static final int SEGMENT_DELAY_TICKS = 4;
    private static final int FINAL_DELAY_TICKS = 5;

    private static final int STAGE_ALIGN = 0;
    private static final int STAGE_FIRST = 1;
    private static final int STAGE_MIDDLE = 2;
    private static final int STAGE_FINAL = 3;

    private final Player player;
    private final int entranceX;
    private final int entranceY;
    private final int destinationX;
    private final int destinationY;
    private final int destinationPlane;
    private final double experience;
    private final boolean restoreRunning;

    private int stage = STAGE_ALIGN;
    private int alignmentTicks;
    private boolean completed;

    public AgilityPipeTraversalTask(Player player,
                                    int entranceX,
                                    int entranceY,
                                    int destinationX,
                                    int destinationY,
                                    int destinationPlane,
                                    double experience,
                                    boolean restoreRunning) {
        this.player = player;
        this.entranceX = entranceX;
        this.entranceY = entranceY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationPlane = destinationPlane;
        this.experience = experience;
        this.restoreRunning = restoreRunning;
    }

    @Override
    public void execute(CycleEventContainer container) {
        if (this.player.getPosition().getPlane() != this.destinationPlane) {
            container.stop();
            return;
        }

        if (this.stage == STAGE_ALIGN) {
            ++this.alignmentTicks;
            if (this.player.getPosition().getX() != this.entranceX
                    || this.player.getPosition().getY() != this.entranceY) {
                if (this.alignmentTicks > 6) {
                    container.stop();
                }
                return;
            }

            this.startSegment(container, 0, 3,
                    OUTER_SEGMENT_END_CYCLE, SEGMENT_DELAY_TICKS,
                    EXACT_MOVE_START_CYCLE);
            this.stage = STAGE_FIRST;
            return;
        }

        if (this.stage == STAGE_FIRST) {
            this.setHiddenServerAnchor(this.entranceX, this.entranceY + 3);
            this.startSegment(container, 0, 1,
                    MIDDLE_SEGMENT_END_CYCLE, SEGMENT_DELAY_TICKS, 0);
            this.stage = STAGE_MIDDLE;
            return;
        }

        if (this.stage == STAGE_MIDDLE) {
            this.setHiddenServerAnchor(this.entranceX, this.entranceY + 4);
            this.startSegment(container, 0,
                    this.destinationY - (this.entranceY + 4),
                    OUTER_SEGMENT_END_CYCLE, FINAL_DELAY_TICKS,
                    EXACT_MOVE_START_CYCLE);
            this.stage = STAGE_FINAL;
            return;
        }

        if (this.stage == STAGE_FINAL) {
            this.player.applyTeleportPosition(new Position(
                    this.destinationX, this.destinationY, this.destinationPlane));
            this.player.recordMovementTick();
            this.player.getSkillManager().addExperience(16, this.experience);
            this.completed = true;
            container.stop();
        }
    }

    private void startSegment(CycleEventContainer container,
                              int deltaX, int deltaY,
                              int endCycle, int delayTicks,
                              int animationDelay) {
        int direction = 2;
        if (deltaX > 0) {
            direction = 1;
        } else if (deltaX < 0) {
            direction = 3;
        } else if (deltaY > 0) {
            direction = 0;
        }

        this.player.getMovementQueue().clear();
        this.player.forcedMovementActive = true;
        this.player.getUpdateState().setFacePosition(new Position(
                this.player.getPosition().getX() + deltaX,
                this.player.getPosition().getY() + deltaY,
                this.destinationPlane));
        this.player.getUpdateState().setAnimation(
                PIPE_ANIMATION, animationDelay);
        this.player.getUpdateState().setForcedMovement(
                this.player,
                deltaX,
                deltaY,
                EXACT_MOVE_START_CYCLE,
                endCycle,
                direction);
        container.setTickDelay(delayTicks);
    }

    private void setHiddenServerAnchor(int x, int y) {
        this.player.setPosition(new Position(x, y, this.destinationPlane));
        this.player.recordMovementTick();
        this.player.getMovementQueue().clear();
    }

    @Override
    public void onStop() {
        this.player.getMovementQueue().clear();
        this.player.getUpdateState().setForcedMovementUpdateRequired(false);
        this.player.getUpdateState().clearForcedMovement();
        this.player.forcedMovementActive = false;
        this.player.setActionLocked(false);

        if (this.restoreRunning) {
            this.player.getMovementQueue().setRunning(true);
        }

        if (!this.completed) {
            return;
        }
    }
}
