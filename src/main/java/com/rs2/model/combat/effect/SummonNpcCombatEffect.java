package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;

public final class SummonNpcCombatEffect extends CombatEffect {
    private int npcId = 77;

    public SummonNpcCombatEffect(int npcId) {
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
        Entity target = combatAction.getTarget();
        if (target.isPlayer()) {
            GameplayHelper.spawnOwnedNpcAdjacentToPlayer((Player)target, new Npc(this.npcId), true, false);
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
