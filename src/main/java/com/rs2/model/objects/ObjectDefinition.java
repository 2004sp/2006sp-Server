package com.rs2.model.objects;

import java.util.logging.Logger;

public class ObjectDefinition {
    public static ObjectDefinition[] definitionsById;
    private int objectId;
    public String name;
    public String description;
    public int width;
    public int length;
    public boolean solid;
    public boolean interactive;
    public boolean blocksProjectiles;
    public boolean projectileCollisionIgnored;

    static {
        Logger.getLogger(ObjectDefinition.class.getName());
    }

    public static ObjectDefinition forId(int value3) {
        if (value3 < definitionsById.length) {
            if (definitionsById[value3] == null) {
                int value2 = value3;
                ObjectDefinition.definitionsById[value3] = new ObjectDefinition(value2, "Object: #" + value2, "Its an object!", 1, 1, false, false, false, true, 2);
            }
            return definitionsById[value3];
        }
        return null;
    }

    private ObjectDefinition(int objectId, String name, String text22, int value22, int value32, boolean enabled5, boolean enabled22, boolean enabled32, boolean enabled42, int value42) {
        this.objectId = objectId;
        this.name = name;
        if (name == null) {
            this.name = "";
        }
        this.width = 1;
        this.length = 1;
        this.solid = false;
        this.interactive = false;
        this.blocksProjectiles = true;
        this.projectileCollisionIgnored = this.isProjectileCollisionIgnored();
    }

    public final int getObjectId() {
        return this.objectId;
    }

    public final String getName() {
        return this.name;
    }

    public final int getWidthForOrientation(int width) {
        if (width == 1 || width == 3) {
            return this.length;
        }
        return this.width;
    }

    public final int getLengthForOrientation(int value2) {
        if (value2 == 1 || value2 == 3) {
            return this.width;
        }
        return this.length;
    }

    public final int getMaxDimension() {
        if (this.length > this.width) {
            return this.length;
        }
        return this.width;
    }

    public final boolean isProjectileCollisionIgnored() {
        if (this.objectId == 1116 || this.objectId == 1117) {
            return false;
        }
        if (this.objectId == 6771 || this.objectId == 6772 || this.objectId == 6773 || this.objectId == 6821 || this.objectId == 6822 || this.objectId == 6823) {
            return false;
        }
        int[] integerValues = new int[]{2440, 2441, 2442, 2443, 2637, 9563, 9565, 14462, 14464, 14465, 14466, 14467, 14468, 14470, 14502, 11754, 3007, 980, 997, 4262, 14437, 14438, 4437, 4439, 3487, 3457};
        int index = 0;
        while (index < 26) {
            int value = integerValues[index];
            if (value == this.objectId) {
                return true;
            }
            ++index;
        }
        if (this.name != null) {
            String objectName = this.name.toLowerCase();
            String[] stringValues = new String[]{"fungus", "mushroom", "sarcophagus", "counter", "plant", "altar", "pew", "log", "stump", "stool", "sign", "cart", "chest", "rock", "bush", "hedge", "chair", "table", "crate", "barrel", "box", "skeleton", "corpse", "vent", "stone", "rockslide"};
            int index2 = 0;
            while (index2 < 26) {
                String ignoredNameFragment = stringValues[index2];
                if (objectName.contains(ignoredNameFragment)) {
                    return true;
                }
                ++index2;
            }
        }
        return false;
    }
}
