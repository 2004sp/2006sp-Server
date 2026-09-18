package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.EntityReference;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.task.TickTask;

public abstract class CombatEffectTask
extends TickTask {
    private EntityReference sourceReference;
    private EntityReference targetReference;
    private CombatEffect effect;

    public CombatEffectTask(int value2, CombatEffect combatEffect, Entity entity, Entity entity2) {
        super(30);
        this.effect = combatEffect;
        this.sourceReference = new EntityReference(entity);
        this.targetReference = new EntityReference(entity2);
    }

    public final CombatEffect getEffect() {
        return this.effect;
    }

    public final EntityReference getSourceReference() {
        return this.sourceReference;
    }

    public final EntityReference getTargetReference() {
        return this.targetReference;
    }

    @Override
    public final void stop() {
        super.stop();
        CombatEffectTask combatEffectTask = this;
        if (combatEffectTask.targetReference.resolve() != null) {
            CombatEffectTask combatEffectTask2 = this;
            combatEffectTask = combatEffectTask2;
            combatEffectTask = this;
            combatEffectTask2.targetReference.resolve().removeCombatEffect(combatEffectTask.effect);
        }
    }
}

