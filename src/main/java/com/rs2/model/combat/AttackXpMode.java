package com.rs2.model.combat;

public enum AttackXpMode {
    MELEE_ACCURATE(new int[]{0}),
    AGGRESSIVE(new int[]{2}),
    DEFENSIVE(new int[]{1}),
    CONTROLLED(new int[]{0, 1, 2}),
    RANGED_ACCURATE(new int[]{4}),
    LONGRANGE(new int[]{4, 1}),
    RAPID(new int[]{4}),
    MAGIC(new int[]{6}),
    KQ_RANGED(new int[]{4}),
    KQ_MAGIC(new int[]{6}),
    KBD_SPECIAL(new int[]{6}),
    DRAGONFIRE_FAR(new int[]{6}),
    DRAGONFIRE(new int[]{6}),
    ICY_BREATH(new int[]{6}),
    SARADOMIN_SWORD(new int[]{6}),
    MELEE_FAR(new int[]{0});

    private int[] skillIds;

    private AttackXpMode(int ... skillIds) {
        this.skillIds = skillIds;
    }

    public final int[] getSkillIds() {
        return this.skillIds;
    }
}

