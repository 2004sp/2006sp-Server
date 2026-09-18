package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.CombatEffectTask;

public final class BloodSpellHealEffect
extends CombatEffect {
    private double healFraction = 0.4;

    public BloodSpellHealEffect(double value2) {
    }

    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return true;
    }

    @Override
    public final void onApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
        int damage = (int)((double)combatAction.getDamage() * this.healFraction);
        combatAction.getAttacker().heal(damage);
    }

    @Override
    public final CombatEffectTask createTask(Entity entity, Entity entity2) {
        return null;
    }

    @Override
    public final boolean equals(Object value2) {
        return value2 instanceof BloodSpellHealEffect;
    }
}

