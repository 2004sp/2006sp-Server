package com.rs2.model.skill.runecrafting;

import com.rs2.CacheCoordinateTranslator;
import com.rs2.model.Position;

public enum RunecraftingAltarDefinition {
    AIR_ALTAR(1438, 5527, 2452, new Position(2987, 3292, 0), 2478, 2465, new Position(2841, 4829, 0), 25.0, false),
    MIND_ALTAR(1448, 5529, 2453, new Position(2982, 3512, 0), 2479, 2466, new Position(2793, 4828, 0), 27.5, false),
    WATER_ALTAR(1444, 5531, 2454, new Position(3183, 3165, 0), 2480, 2467, new Position(2726, 4832, 0), 30.0, false),
    EARTH_ALTAR(1440, 5535, 2455, new Position(3304, 3474, 0), 2481, 2468, new Position(2655, 4830, 0), 32.5, false),
    FIRE_ALTAR(1442, 5537, 2456, new Position(3312, 3253, 0), 2482, 2469, new Position(2574, 4849, 0), 35.0, false),
    BODY_ALTAR(1446, 5533, 2457, new Position(3051, 3445, 0), 2483, 2470, new Position(2523, 4826, 0), 37.5, false),
    COSMIC_ALTAR(1454, 5539, 2458, CacheCoordinateTranslator.dungeonCoordinateShiftActive ? new Position(3175, 9499, 0) : new Position(2407, 4379, 0), 2484, 2471, new Position(2142, 4813, 0), 40.0, true),
    CHAOS_ALTAR(1452, 5543, 2461, new Position(3062, 3591, 0), 2487, 2474, new Position(2275, 4847, 3), 42.5, true),
    NATURE_ALTAR(1462, 5541, 2460, new Position(2868, 3017, 0), 2486, 2473, new Position(2400, 4835, 0), 45.0, true),
    LAW_ALTAR(1458, 5545, 2459, new Position(2859, 3379, 0), 2485, 2472, new Position(2464, 4818, 0), 47.5, true),
    DEATH_ALTAR(1456, 5547, 2462, new Position(1862, 4639, 0), 2488, 2475, new Position(2208, 4830, 0), 50.0, true),
    BLOOD_ALTAR(1450, 5549, 2464, new Position(3469, 3391, 0), 2490, 2477, new Position(2254, 4913, 0), 52.5, true),
    SOUL_ALTAR(1460, 5551, 2463, new Position(2241, 3199, 0), 2489, 2476, new Position(2331, 4826, 0), 55.0, true);

    int talismanItemId;
    int tiaraItemId;
    int ruinsObjectId;
    int altarObjectId;
    int portalObjectId;
    Position altarPosition;
    Position ruinsPosition;
    double tiaraExperience;
    boolean membersOnly;

    private RunecraftingAltarDefinition(int talismanItemId, int tiaraItemId, int ruinsObjectId, Position position, int altarObjectId, int portalObjectId, Position position2, double tiaraExperience, boolean membersOnly) {
        this.talismanItemId = talismanItemId;
        this.tiaraItemId = tiaraItemId;
        this.ruinsObjectId = ruinsObjectId;
        this.ruinsPosition = position;
        this.altarObjectId = altarObjectId;
        this.portalObjectId = portalObjectId;
        this.altarPosition = position2;
        this.tiaraExperience = tiaraExperience;
        this.membersOnly = membersOnly;
    }

    public final int getTalismanItemId() {
        return this.talismanItemId;
    }

    public final int getTiaraItemId() {
        return this.tiaraItemId;
    }

    public final double getTiaraExperience() {
        return this.tiaraExperience;
    }

    public final int getAltarObjectId() {
        return this.altarObjectId;
    }
}

