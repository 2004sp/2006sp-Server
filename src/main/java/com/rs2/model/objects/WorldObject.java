package com.rs2.model.objects;

import com.rs2.model.Position;

public final class WorldObject {
    private int objectId;
    private int type;
    private int orientation;
    private Position position;

    public WorldObject(int objectId, int type, int orientation, Position position) {
        this.objectId = objectId;
        this.type = type;
        this.orientation = orientation;
        this.position = position;
    }

    public final int getObjectId() {
        return this.objectId;
    }

    public final int getType() {
        return this.type;
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final Position getPosition() {
        return this.position;
    }
}

