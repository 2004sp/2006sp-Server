package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.player.Player;

public class PoisonEffect extends CombatEffect {
    private double initialDamage;
    private boolean notifyTarget;

    public PoisonEffect(double initialDamage) {
        this.initialDamage = initialDamage;
        this.notifyTarget = true;
    }

    public PoisonEffect(double initialDamage, boolean notifyTarget) {
        this.initialDamage = initialDamage;
        this.notifyTarget = false;
    }

    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return entity.getPoisonImmunityTimer().hasElapsed();
    }

    @Override
    public boolean equals(Object value2) {
        return value2 instanceof PoisonEffect;
    }

    @Override
    public final void onApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
        PoisonDamageTask poisonDamageTask = (PoisonDamageTask)combatEffectTask;
        Entity target = combatAction.getTarget();
        if (target.isPlayer() && this.notifyTarget) {
            ((Player)target).packetSender.sendGameMessage("You've been poisoned.");
        }
        target.setPoisonDamage(this.initialDamage);
        poisonDamageTask.execute();
    }

    @Override
    public final CombatEffectTask createTask(Entity source, Entity target) {
        return new PoisonDamageTask(this, this, source, target);
    }

    static double getInitialDamage(PoisonEffect poisonEffect) {
        return poisonEffect.initialDamage;
    }
}
