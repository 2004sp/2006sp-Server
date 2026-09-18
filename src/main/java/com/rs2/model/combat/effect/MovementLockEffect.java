package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;

public class MovementLockEffect extends CombatEffect {
    private int durationTicks;

    public MovementLockEffect(int durationTicks) {
        this.durationTicks = durationTicks;
    }

    @Override
    public void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return entity.getMovementLockImmunityTimer().hasElapsed();
    }

    @Override
    public final void onApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
        Entity target = combatAction.getTarget();
        target.getMovementQueue().clear();
        target.getMovementLockTimer().setDelayTicks(this.durationTicks);
        target.getMovementLockTimer().reset();
        target.getMovementLockImmunityTimer().setDelayTicks(this.durationTicks + 6);
        target.getMovementLockImmunityTimer().reset();
    }

    @Override
    public final CombatEffectTask createTask(Entity entity, Entity target) {
        return null;
    }

    @Override
    public boolean equals(Object value2) {
        return value2 instanceof MovementLockEffect;
    }

    public final int getDurationTicks() {
        return this.durationTicks;
    }
}
