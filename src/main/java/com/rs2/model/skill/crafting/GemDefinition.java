package com.rs2.model.skill.crafting;

import java.util.HashMap;

public enum GemDefinition {
    SAPPHIRE(1623, 1607, 20, 888, 50.0),
    EMERALD(1621, 1605, 27, 889, 67.0),
    RUBY(1619, 1603, 34, 887, 85.0),
    DIAMOND(1617, 1601, 43, 886, 107.5),
    DRAGONSTONE(1631, 1615, 55, 885, 137.5),
    ONYX(6571, 6573, 67, 885, 168.0),
    OPAL(1625, 1609, 1, 891, 15.0),
    JADE(1627, 1611, 13, 890, 20.0),
    RED_TOPAZ(1629, 1613, 16, 887, 25.0);

    private short uncutItemId;
    private short cutItemId;
    private byte requiredLevel;
    private short animationId;
    private double experience;
    private static HashMap definitionsByUncutItemId;

    static {
        definitionsByUncutItemId = new HashMap();
        GemDefinition[] gemDefinitionArray = GemDefinition.values();
        int length = gemDefinitionArray.length;
        int index = 0;
        while (index < length) {
            GemDefinition gemDefinition;
            GemDefinition gemDefinition2 = gemDefinition = gemDefinitionArray[index];
            definitionsByUncutItemId.put(Integer.valueOf(gemDefinition2.uncutItemId), gemDefinition);
            ++index;
        }
    }

    public static GemDefinition forUncutItemId(int itemId) {
        return (GemDefinition)((Object)definitionsByUncutItemId.get(itemId));
    }

    private GemDefinition(int uncutItemId, int cutItemId, int requiredLevel, int animationId, double experience) {
        this.uncutItemId = (short)uncutItemId;
        this.cutItemId = (short)cutItemId;
        this.requiredLevel = (byte)requiredLevel;
        this.animationId = (short)animationId;
        this.experience = experience;
    }

    public final int getCutItemId() {
        return this.cutItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int getAnimationId() {
        return this.animationId;
    }

    public final double getExperience() {
        return this.experience;
    }
}

