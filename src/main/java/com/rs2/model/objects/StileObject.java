package com.rs2.model.objects;

/**
 * Shared recognition for stile scenery.
 *
 * Keep this independent of interaction code so movement/collision and object
 * dispatch agree on exactly which objects are allowed to use stile traversal.
 */
public final class StileObject {
    private static final int[] KNOWN_STILE_IDS = {
            993,    // Catherby
            3730,   // Death Plateau
            7527,
            12982,  // Fred's farm
            19222   // Piscatoris falconry area
    };

    private StileObject() {
    }

    public static boolean isStile(int objectId) {
        if (objectId < 0) {
            return false;
        }

        for (int knownId : KNOWN_STILE_IDS) {
            if (objectId == knownId) {
                return true;
            }
        }

        ObjectDefinition definition = ObjectDefinition.forId(objectId);
        return definition != null
                && definition.getName() != null
                && definition.getName().equalsIgnoreCase("stile");
    }

    public static boolean isStile(WorldObject worldObject) {
        return worldObject != null && isStile(worldObject.getObjectId());
    }

    public static int getAgilityExperience(int objectId) {
        // The Catherby stile is the only documented revision-era stile that
        // awards Agility experience for crossing.
        return objectId == 993 ? 2 : 0;
    }
}
