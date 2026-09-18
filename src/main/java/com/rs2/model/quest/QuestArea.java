package com.rs2.model.quest;

import com.rs2.model.Position;
import com.rs2.util.RectangularArea;

public final class QuestArea {
    private int areaId;
    private Position targetPosition;
    private RectangularArea areaBounds;

    public QuestArea(int areaId, Position position, RectangularArea rectangularArea) {
        this.areaId = areaId;
        this.targetPosition = position;
        this.areaBounds = rectangularArea;
    }

    public final int getAreaId() {
        return this.areaId;
    }

    public final Position getTargetPosition() {
        return this.targetPosition;
    }

    public final RectangularArea getAreaBounds() {
        return this.areaBounds;
    }
}

