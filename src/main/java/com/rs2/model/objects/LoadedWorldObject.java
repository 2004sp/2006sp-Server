package com.rs2.model.objects;

import com.rs2.model.Position;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.WorldObject;

public final class LoadedWorldObject {
    private Position position;
    private WorldObject worldObject;
    private int type = 0;
    private int orientation = 0;

    public LoadedWorldObject(ObjectDefinition objectDefinition, Position position, int type, int orientation) {
        this.worldObject = new WorldObject(objectDefinition.getObjectId(), type, orientation, position);
        this.position = position;
        this.type = type;
        this.orientation = orientation;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final WorldObject getWorldObject() {
        return this.worldObject;
    }

    public final int getType() {
        return this.type;
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final String toString() {
        return ObjectDefinition.forId(this.worldObject.getObjectId()) + " " + this.position + " " + this.type + " " + this.orientation;
    }
}

