package com.rs2.model.skill.runecrafting;

public enum CombinationRuneDefinition {
    MIST_WITH_AIR_TALISMAN(1438, 556, 2480, 4695, 6, 8.5),
    MIST_WITH_WATER_TALISMAN(1444, 555, 2478, 4695, 6, 8.0),
    DUST_WITH_AIR_TALISMAN(1438, 556, 2481, 4696, 10, 9.0),
    DUST_WITH_EARTH_TALISMAN(1440, 557, 2478, 4696, 10, 8.3),
    MUD_WITH_WATER_TALISMAN(1444, 555, 2481, 4698, 13, 9.5),
    MUD_WITH_EARTH_TALISMAN(1440, 557, 2480, 4698, 13, 9.3),
    SMOKE_WITH_AIR_TALISMAN(1438, 556, 2482, 4697, 15, 9.5),
    SMOKE_WITH_FIRE_TALISMAN(1442, 554, 2478, 4697, 15, 8.5),
    STEAM_WITH_WATER_TALISMAN(1444, 555, 2482, 4694, 19, 10.0),
    STEAM_WITH_FIRE_TALISMAN(1442, 554, 2480, 4694, 19, 9.3),
    LAVA_WITH_EARTH_TALISMAN(1440, 557, 2482, 4699, 23, 10.5),
    LAVA_WITH_FIRE_TALISMAN(1442, 554, 2481, 4699, 23, 10.0);

    private int talismanItemId;
    private int runeItemId;
    private int altarObjectId;
    private int productRuneItemId;
    private int requiredLevel;
    private double experience;

    public static CombinationRuneDefinition forTalismanAndAltarObjectId(int objectId, int value2) {
        CombinationRuneDefinition[] combinationRuneDefinitionArray = CombinationRuneDefinition.values();
        int length = combinationRuneDefinitionArray.length;
        int index = 0;
        while (index < length) {
            CombinationRuneDefinition combinationRuneDefinition = combinationRuneDefinitionArray[index];
            if (objectId == combinationRuneDefinition.talismanItemId && value2 == combinationRuneDefinition.altarObjectId) {
                return combinationRuneDefinition;
            }
            ++index;
        }
        return null;
    }

    private CombinationRuneDefinition(int talismanItemId, int runeItemId, int altarObjectId, int productRuneItemId, int requiredLevel, double experience) {
        this.talismanItemId = talismanItemId;
        this.runeItemId = runeItemId;
        this.altarObjectId = altarObjectId;
        this.productRuneItemId = productRuneItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getTalismanItemId() {
        return this.talismanItemId;
    }

    public final int getRuneItemId() {
        return this.runeItemId;
    }

    public final int getProductRuneItemId() {
        return this.productRuneItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}

