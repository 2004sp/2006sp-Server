package com.rs2.model;

import com.rs2.model.MovementQueue;
import com.rs2.model.Position;

public final class MovementStep
extends Position {
    private int direction;

    public MovementStep(MovementQueue movementQueue, int direction, int value22, int value32) {
        super(direction, value22);
        direction = value32;
        this.direction = direction;
    }

    public final int getDirection() {
        return this.direction;
    }
}

