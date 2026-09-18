package com.rs2.model.objects;

import com.rs2.model.Position;
import com.rs2.model.objects.ObjectRegionKey;
import com.rs2.model.skill.agility.AgilityObstacleHandler;
import java.util.HashMap;
import java.util.Map;

public final class WorldObjectRegionIndex {
    private static Map bucketsByRegion = new HashMap();

    public static AgilityObstacleHandler getOrCreateRegionBucket(Position position) {
        int y = position.getY() / 32;
        int x = position.getX() / 32;
        ObjectRegionKey objectRegionKey = new ObjectRegionKey(x, y);
        if (bucketsByRegion.containsKey(objectRegionKey)) {
            return (AgilityObstacleHandler)bucketsByRegion.get(objectRegionKey);
        }
        AgilityObstacleHandler agilityObstacleHandler = new AgilityObstacleHandler(objectRegionKey);
        bucketsByRegion.put(objectRegionKey, agilityObstacleHandler);
        return agilityObstacleHandler;
    }
}

