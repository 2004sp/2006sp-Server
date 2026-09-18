package com.rs2.model.objects.functions;

import com.rs2.model.Position;
import com.rs2.model.objects.functions.ObeliskTick;
import com.rs2.util.RectangularArea;
import java.awt.Rectangle;

public enum WildernessObelisk {
    LEVEL_13(14829, new Position(3154, 3618)),
    LEVEL_19(14830, new Position(3225, 3665)),
    LEVEL_27(14827, new Position(3033, 3730)),
    LEVEL_35(14828, new Position(3104, 3792)),
    LEVEL_44(14826, new Position(2978, 3864)),
    LEVEL_50(14831, new Position(3305, 3914));

    Position basePosition;
    boolean active;
    Rectangle teleportBounds;
    RectangularArea effectArea;
    Position[] cornerPositions;
    int objectId;
    private WildernessObelisk(int objectId, Position position) {
        this.objectId = objectId;
        this.basePosition = position;
        this.active = false;
        this.effectArea = RectangularArea.fromPositionOffset(new Position(position.getX() + 1, position.getY() + 1, position.getPlane()), 2, 2);
        this.teleportBounds = new Rectangle(position.getX(), position.getY(), 5, 5);
        this.cornerPositions = ObeliskTick.getCornerPositions(this);
    }
}

