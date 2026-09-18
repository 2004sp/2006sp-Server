package com.rs2.model.combat;

public enum AttackBonusType {
    STAB(0),
    SLASH(1),
    CRUSH(2),
    MAGIC(3),
    RANGED(4);

    private int index;

    private AttackBonusType(int index) {
        this.index = index;
    }

    public final int getIndex() {
        return this.index;
    }
}
