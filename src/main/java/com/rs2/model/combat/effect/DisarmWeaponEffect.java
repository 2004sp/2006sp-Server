package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.player.Player;

public final class DisarmWeaponEffect extends CombatEffect {
    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return true;
    }

    @Override
    public final void onApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
        Entity target = combatAction.getTarget();
        if (target.isPlayer()) {
            ((Player)target).getEquipmentManager().unequipSlot(3);
        }
    }

    @Override
    public final CombatEffectTask createTask(Entity entity, Entity target) {
        return null;
    }

    @Override
    public final boolean equals(Object value2) {
        return false;
    }
}
