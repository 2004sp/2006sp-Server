package com.rs2.model.skill.runecrafting;

public enum RuneDefinition {
    FIRE_RUNE(554, 14, 7.0, 2482, 35),
    WATER_RUNE(555, 5, 6.0, 2480, 19),
    AIR_RUNE(556, 1, 5.0, 2478, 11),
    EARTH_RUNE(557, 9, 6.5, 2481, 26),
    MIND_RUNE(558, 2, 5.5, 2479, 14),
    BODY_RUNE(559, 20, 7.5, 2483, 46),
    DEATH_RUNE(560, 65, 10.0, 2488, -1),
    NATURE_RUNE(561, 44, 9.0, 2486, 91),
    CHAOS_RUNE(562, 35, 8.5, 2487, 74),
    LAW_RUNE(563, 54, 9.5, 2485, -1),
    COSMIC_RUNE(564, 27, 8.0, 2484, 59),
    BLOOD_RUNE(565, 77, 10.5, 2490, -1),
    SOUL_RUNE(566, 90, 11.0, 2489, -1);

    private int runeItemId;
    private int requiredLevel;
    private double experience;
    private int multipleRunesLevelInterval;

    private RuneDefinition(int runeItemId, int requiredLevel, double experience, int value33, int multipleRunesLevelInterval) {
        this.runeItemId = runeItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.multipleRunesLevelInterval = multipleRunesLevelInterval;
    }

    public final int getRuneItemId() {
        return this.runeItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }

    public final int getMultipleRunesLevelInterval() {
        return this.multipleRunesLevelInterval;
    }
}

