package com.rs2.model.combat.effect;

import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.effect.CombatEffectTask;
import com.rs2.model.combat.effect.MovementLockEffect;

public final class WallBeastStunEffect
extends MovementLockEffect {
    private int graphicId = 80;

    public WallBeastStunEffect(int value2) {
        super(5);
    }

    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
        combatAction.getTarget().getStunTimer().setDelayTicks(this.getDurationTicks());
        combatAction.getTarget().getStunTimer().reset();
        combatAction.getTarget().getUpdateState().setGraphic(GraphicEffect.createHeight100(this.graphicId));
    }
}

