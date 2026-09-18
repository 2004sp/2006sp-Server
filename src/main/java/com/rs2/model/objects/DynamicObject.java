package com.rs2.model.objects;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.Player;

public final class DynamicObject {
    private WorldObject worldObject;
    public int orientation;
    public int restoreObjectId;
    public int remainingTicks;
    public boolean updatesCollision;
    public Player owner;

    public DynamicObject(int value9, int value22, int value32, int value42, int value52, int value62, int restoreObjectId, int remainingTicks) {
        if (!GameplayHelper.isObjectDefinitionIdValid(value9)) {
            value9 = ServerSettings.placeholderObjectId;
        }
        if (!GameplayHelper.isObjectDefinitionIdValid(restoreObjectId)) {
            restoreObjectId = ServerSettings.placeholderObjectId;
        }
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(value22, value32, value42);
        if (dynamicObject != null && value9 == dynamicObject.worldObject.getObjectId()) {
            return;
        }
        this.worldObject = new WorldObject(value9, value62, value52, new Position(value22, value32, value42));
        this.orientation = this.worldObject.getOrientation();
        this.restoreObjectId = restoreObjectId;
        this.remainingTicks = remainingTicks;
        this.updatesCollision = true;
        ObjectManager.getInstance().addDynamicObject(this, this.updatesCollision);
    }

    public DynamicObject(int value9, int value22, int value32, int value42, int value52, int value62, int restoreObjectId, int remainingTicks, Player player) {
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(value22, value32, value42);
        if (dynamicObject != null && value9 == dynamicObject.worldObject.getObjectId()) {
            return;
        }
        this.worldObject = new WorldObject(value9, 10, -1, new Position(value22, value32, value42));
        this.orientation = this.worldObject.getOrientation();
        this.restoreObjectId = restoreObjectId;
        this.remainingTicks = remainingTicks;
        this.updatesCollision = true;
        this.owner = player;
        ObjectManager.getInstance().addDynamicObject(this, this.updatesCollision);
    }

    public DynamicObject(int value9, int value22, int value32, int value42, int value52, int value62, int restoreObjectId, int remainingTicks, boolean updatesCollision) {
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(value22, value32, value42);
        if (dynamicObject != null && value9 == dynamicObject.worldObject.getObjectId()) {
            return;
        }
        this.worldObject = new WorldObject(value9, value62, value52, new Position(value22, value32, value42));
        this.orientation = this.worldObject.getOrientation();
        this.restoreObjectId = restoreObjectId;
        this.remainingTicks = remainingTicks;
        this.updatesCollision = updatesCollision;
        ObjectManager.getInstance().addDynamicObject(this, this.updatesCollision);
    }

    public DynamicObject(int value12, int value22, int value32, int value42, int value52, int value62, int restoreObjectId, int remainingTicks, int orientation, int value102, int value112, boolean enabled2) {
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(value22, value32, value42);
        if (dynamicObject != null && value12 == dynamicObject.worldObject.getObjectId()) {
            return;
        }
        this.worldObject = new WorldObject(value12, value62, value52, new Position(value22, value32, value42));
        this.orientation = orientation;
        this.restoreObjectId = restoreObjectId;
        this.remainingTicks = remainingTicks;
        this.updatesCollision = false;
        ObjectManager.getInstance().addDynamicObject(this, this.updatesCollision);
    }

    public final WorldObject getWorldObject() {
        return this.worldObject;
    }
}

