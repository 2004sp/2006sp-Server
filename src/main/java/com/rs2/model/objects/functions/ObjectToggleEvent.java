/*
 * Source recovery overlay: make remapped event accessible to recovered callers.
 */
package com.rs2.model.objects.functions;

import com.rs2.ServerSettings;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class ObjectToggleEvent
extends CycleEvent {
    private final int restoreObjectId;
    private final WorldObject worldObject;
    private final int objectType;
    private final int toggledObjectId;
    private final int toggledOrientation;
    private final Player player;

    public ObjectToggleEvent(int restoreObjectId, WorldObject worldObject, int objectType, int toggledObjectId, int toggledOrientation, Player player) {
        this.restoreObjectId = restoreObjectId;
        this.worldObject = worldObject;
        this.objectType = objectType;
        this.toggledObjectId = toggledObjectId;
        this.toggledOrientation = toggledOrientation;
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        ObjectManager.getInstance();
        if (ObjectManager.findDynamicObjectByIdAt(this.restoreObjectId, this.worldObject.getPosition().getX(), this.worldObject.getPosition().getY(), this.worldObject.getPosition().getPlane()) != null) {
            int objectType = this.objectType;
            int plane = this.worldObject.getPosition().getPlane();
            int y = this.worldObject.getPosition().getY();
            int x = this.worldObject.getPosition().getX();
            int objectId = this.restoreObjectId;
            ObjectManager objectManager = ObjectManager.getInstance();
            DynamicObject dynamicObject = ObjectManager.findDynamicObjectByIdAt(objectId, x, y, plane);
            if (dynamicObject != null) {
                ObjectManager.removeObjectCollision(dynamicObject.getWorldObject().getObjectId(), x, y, plane, objectType, dynamicObject.getWorldObject().getOrientation());
                ObjectManager.activeDynamicObjects.remove(dynamicObject);
                objectManager.revertDynamicObject(dynamicObject);
            }
            if (this.restoreObjectId == 883) {
                ObjectManager.getInstance().removeDynamicObjectAt(this.worldObject.getPosition().getX(), this.worldObject.getPosition().getY() + 1, this.worldObject.getPosition().getPlane(), this.objectType);
            }
        } else {
            new DynamicObject(this.toggledObjectId, this.worldObject.getPosition().getX(), this.worldObject.getPosition().getY(), this.worldObject.getPosition().getPlane(), this.toggledOrientation, this.objectType, this.restoreObjectId, 999999);
            if (this.restoreObjectId == 881) {
                new DynamicObject(883, this.worldObject.getPosition().getX(), this.worldObject.getPosition().getY() - 1, this.worldObject.getPosition().getPlane(), this.toggledOrientation, this.objectType, ServerSettings.placeholderObjectId, 999999);
            }
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}
