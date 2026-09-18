package com.rs2.model.gameplay;

import com.rs2.model.Position;

public final class PositionRange {
    private Position minPosition;
    private Position maxPosition;

    public PositionRange(Position position, Position position2) {
        this.minPosition = position;
        this.maxPosition = position2;
    }

    public final Position getMinPosition() {
        return this.minPosition;
    }

    public final Position getMaxPosition() {
        return this.maxPosition;
    }

    public final void setPlane(int plane) {
        this.minPosition.setPlane(plane);
        this.maxPosition.setPlane(plane);
    }
}

