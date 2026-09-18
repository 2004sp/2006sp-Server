package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.effect.CombatEffectTask;

public abstract class CombatEffect {
    private boolean applied;
    private CombatEffectTask activeTask;

    public final void apply(CombatAction combatAction) {
        this.activeTask = this.createTask(combatAction.getAttacker(), combatAction.getTarget());
        if (this.activeTask != null) {
            combatAction.getTarget().addCombatEffectTask(this.activeTask);
            World.getTaskScheduler().schedule(this.activeTask);
        }
        this.onApply(combatAction, this.activeTask);
        this.applied = true;
    }

    public final void afterApply(CombatAction combatAction) {
        if (!this.applied) {
            return;
        }
        this.onAfterApply(combatAction, this.activeTask);
    }

    public abstract void onAfterApply(CombatAction action, CombatEffectTask task);

    public abstract boolean canApplyTo(Entity target);

    public abstract void onApply(CombatAction action, CombatEffectTask task);

    public abstract CombatEffectTask createTask(Entity source, Entity target);

    public abstract boolean equals(Object value2);
}
