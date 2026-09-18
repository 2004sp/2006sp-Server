package com.rs2.model.combat.hit;

import com.rs2.model.Entity;
import com.rs2.model.EntityReference;
import com.rs2.model.task.DelayTimer;

public final class DamageContribution
extends EntityReference {
    private int damage;
    private DelayTimer expiryTimer = new DelayTimer(500);

    public DamageContribution(Entity entity) {
        super(entity);
    }

    public final boolean isExpired() {
        return this.expiryTimer.hasElapsed();
    }

    public final void addDamage(int value2) {
        this.damage += value2;
        this.expiryTimer.reset();
    }

    public final int getDamage() {
        return this.damage;
    }
}

