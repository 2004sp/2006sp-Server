package com.rs2.model.combat.hit;

public enum HitType {
    NORMAL(1),
    POISON(2),
    DISEASE(3),
    BLOCKED(0);

    private int clientId;

    private HitType(int clientId) {
        this.clientId = clientId;
    }

    public final int getClientId() {
        return this.clientId;
    }
}

