package com.rs2.model.skill.fletching;

public enum GemBoltTipDefinition {
    OYSTER_PEARL(411, 46, 6, 41, 3.0),
    OYSTER_PEARLS(413, 46, 24, 41, 9.0),
    OPAL(1609, 45, 12, 11, 1.5);

    private int gemItemId;
    private int boltTipItemId;
    private int boltTipAmount;
    private int requiredLevel;
    private double experience;

    public static GemBoltTipDefinition forGemItemId(int gemItemId) {
        for (GemBoltTipDefinition definition : GemBoltTipDefinition.values()) {
            if (definition.gemItemId == gemItemId) {
                return definition;
            }
        }
        return null;
    }

    private GemBoltTipDefinition(int gemItemId, int boltTipItemId, int boltTipAmount, int requiredLevel, double experience) {
        this.gemItemId = gemItemId;
        this.boltTipItemId = boltTipItemId;
        this.boltTipAmount = boltTipAmount;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getGemItemId() {
        return this.gemItemId;
    }

    public final int getBoltTipItemId() {
        return this.boltTipItemId;
    }

    public final int getBoltTipAmount() {
        return this.boltTipAmount;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}
