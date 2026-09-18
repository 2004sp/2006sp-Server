package com.rs2.model.skill.fletching;

public enum BowStringingDefinition {
    NORMAL_SHORTBOW(50, 1777, 841, 5, 5.0),
    NORMAL_LONGBOW(48, 1777, 839, 10, 10.0),
    OAK_SHORTBOW(54, 1777, 843, 20, 16.5),
    OAK_LONGBOW(56, 1777, 845, 25, 25.0),
    OGRE_COMPOSITE_BOW(4825, 1777, 4827, 30, 45.0),
    WILLOW_SHORTBOW(60, 1777, 849, 35, 33.3),
    WILLOW_LONGBOW(58, 1777, 847, 40, 41.5),
    MAPLE_SHORTBOW(64, 1777, 853, 50, 50.0),
    MAPLE_LONGBOW(62, 1777, 851, 55, 58.3),
    YEW_SHORTBOW(68, 1777, 857, 65, 68.5),
    YEW_LONGBOW(66, 1777, 855, 70, 75.0),
    MAGIC_SHORTBOW(72, 1777, 861, 80, 83.3),
    MAGIC_LONGBOW(70, 1777, 859, 85, 91.5);

    private int unstrungBowItemId;
    private int bowStringItemId;
    private int strungBowItemId;
    private int requiredLevel;
    private double experience;

    public static BowStringingDefinition forComponents(int firstItemId, int secondItemId) {
        for (BowStringingDefinition definition : BowStringingDefinition.values()) {
            if (definition.unstrungBowItemId == firstItemId && definition.bowStringItemId == secondItemId || definition.bowStringItemId == firstItemId && definition.unstrungBowItemId == secondItemId) {
                return definition;
            }
        }
        return null;
    }

    private BowStringingDefinition(int unstrungBowItemId, int bowStringItemId, int strungBowItemId, int requiredLevel, double experience) {
        this.unstrungBowItemId = unstrungBowItemId;
        this.bowStringItemId = bowStringItemId;
        this.strungBowItemId = strungBowItemId;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
    }

    public final int getUnstrungBowItemId() {
        return this.unstrungBowItemId;
    }

    public final int getBowStringItemId() {
        return this.bowStringItemId;
    }

    public final int getStrungBowItemId() {
        return this.strungBowItemId;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }
}
