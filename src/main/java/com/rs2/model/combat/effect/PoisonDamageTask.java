package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.effect.CombatEffectTask;
import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;

public final class PoisonDamageTask
extends CombatEffectTask {
    private double currentDamage;

    public PoisonDamageTask(PoisonEffect poisonEffect, PoisonEffect poisonEffect2, Entity entity, Entity entity2) {
        super(30, poisonEffect2, entity, entity2);
        this.currentDamage = PoisonEffect.getInitialDamage(poisonEffect2);
    }

    @Override
    public final void execute() {
        Object sourceReference = this.getSourceReference().resolve();
        Entity entity = this.getTargetReference().resolve();
        this.getEffect();
        if (entity == null) {
            this.stop();
            return;
        }
        if (this.currentDamage > 0.0) {
            HitDefinition hitDefinition = new HitDefinition(null, HitType.POISON, Math.ceil(this.currentDamage)).setDelay(1).setAlwaysHits(true).setBlockAnimationEnabled(false);
            sourceReference = new CombatAction((Entity)sourceReference, entity, hitDefinition);
            ((CombatAction)sourceReference).queue();
            this.currentDamage -= 0.2;
            entity.setPoisonDamage(this.currentDamage);
        }
        if (this.currentDamage <= 0.0) {
            this.stop();
        }
    }
}

