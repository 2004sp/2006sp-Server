package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.attack.CombatAttackState;
import com.rs2.model.task.CycleEventContainer;

public abstract class CombatAttack {
    private Entity attacker;
    private Entity target;

    public CombatAttack(Entity entity, Entity entity2) {
        this.attacker = entity;
        this.target = entity2;
    }

    public final Entity getAttacker() {
        return this.attacker;
    }

    public final Entity getTarget() {
        return this.target;
    }

    public abstract CombatType getCombatType();

    public abstract int getAttackRange();

    public abstract int execute(CycleEventContainer container);

    public abstract CombatAttackState getState();

    public abstract void prepare();
}
