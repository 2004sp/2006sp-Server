package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatType;
import com.rs2.model.player.Player;

public final class TeleblockEffect extends CombatEffect {
    private int durationTicks = 500;

    public TeleblockEffect(int durationTicks) {
    }

    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return !entity.isTeleblocked();
    }

    @Override
    public final void onApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
        Entity target = combatAction.getTarget();
        combatAction.getAttacker();
        if (target.isPlayer()) {
            ((Player)target).packetSender.sendGameMessage("You have been teleblocked!");
        }
        if (target.isProtectedFrom(CombatType.MAGIC)) {
            target.getTeleblockTimer().setDelayTicks(this.durationTicks / 2);
        } else {
            target.getTeleblockTimer().setDelayTicks(this.durationTicks);
        }
        target.getTeleblockTimer().reset();
    }

    @Override
    public final CombatEffectTask createTask(Entity entity, Entity target) {
        return null;
    }

    @Override
    public final boolean equals(Object value2) {
        return value2 instanceof TeleblockEffect;
    }
}
