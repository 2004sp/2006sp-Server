package com.rs2.model.objects;

import com.rs2.cache.CacheStore;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.WorldObjectRegionIndex;
import com.rs2.model.skill.agility.AgilityObstacleHandler;
import java.util.logging.Logger;

public class WorldObjectLookup {
    static {
        Logger.getLogger(WorldObjectLookup.class.getName());
    }

    public static void loadWorldObjects() {
        Object instance = CacheStore.getInstance();
        try {
            ((CacheStore)instance).getDefinitionIndex().getObjectDefinitionEntries();
            GameplayHelper.loadObjectDefinitions();
            return;
        }
        catch (Exception exception) {
            instance = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static LoadedWorldObject findObjectAt(int objectId, int value2, int value32) {
        Position position = new Position(objectId, value2, value32);
        World.getInstance().getObjectRegionIndex();
        AgilityObstacleHandler bucket = WorldObjectRegionIndex.getOrCreateRegionBucket(position);
        for (Object loadedObject : bucket.getLoadedObjects()) {
            LoadedWorldObject loadedWorldObject = (LoadedWorldObject)loadedObject;
            if (!loadedWorldObject.getPosition().equals(position)) continue;
            ObjectDefinition objectDefinition = ObjectDefinition.forId(loadedWorldObject.getWorldObject().getObjectId());
            if (loadedWorldObject.getType() == 0 && !objectDefinition.interactive || loadedWorldObject.getType() == 3 && !objectDefinition.interactive || loadedWorldObject.getType() == 4 && !objectDefinition.interactive || loadedWorldObject.getType() == 22 && !objectDefinition.interactive) continue;
            return loadedWorldObject;
        }
        return null;
    }

    public static LoadedWorldObject findObjectByIdAt(int objectId, int value2, int value32, int value42) {
        Position position = new Position(value2, value32, value42);
        World.getInstance().getObjectRegionIndex();
        AgilityObstacleHandler bucket = WorldObjectRegionIndex.getOrCreateRegionBucket(position);
        for (Object loadedObject : bucket.getLoadedObjects()) {
            LoadedWorldObject loadedWorldObject = (LoadedWorldObject)loadedObject;
            if (loadedWorldObject.getWorldObject().getObjectId() != objectId || !loadedWorldObject.getPosition().equals(position)) continue;
            return loadedWorldObject;
        }
        return null;
    }

    public static LoadedWorldObject findObjectByNameAt(String objectId, int objectId2, int value2, int value32) {
        Position position = new Position(objectId2, value2, value32);
        World.getInstance().getObjectRegionIndex();
        AgilityObstacleHandler bucket = WorldObjectRegionIndex.getOrCreateRegionBucket(position);
        for (Object loadedObject : bucket.getLoadedObjects()) {
            LoadedWorldObject loadedWorldObject = (LoadedWorldObject)loadedObject;
            String objectName;
            if (ObjectDefinition.forId(loadedWorldObject.getWorldObject().getObjectId()) != null) {
                ObjectDefinition objectDefinition = ObjectDefinition.forId(loadedWorldObject.getWorldObject().getObjectId());
                objectName = objectDefinition.name.toLowerCase();
            } else {
                objectName = "";
            }
            if (!objectName.contains(objectId.toLowerCase()) || !loadedWorldObject.getPosition().equals(position)) continue;
            return loadedWorldObject;
        }
        return null;
    }

}

