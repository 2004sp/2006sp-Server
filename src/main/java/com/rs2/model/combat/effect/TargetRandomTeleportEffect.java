package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.npc.Npc;
import com.rs2.util.GameUtil;
import com.rs2.util.path.WalkingCollisionMap;

public final class TargetRandomTeleportEffect extends CombatEffect {
    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return true;
    }

    @Override
    public final void onApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
        Entity attacker = combatAction.getAttacker();
        Entity target = combatAction.getTarget();
        int x;
        int y;
        if (attacker.isNpc()) {
            x = ((Npc)attacker).getSpawnPosition().getX() - 10 + GameUtil.randomInclusive(20);
            y = ((Npc)attacker).getSpawnPosition().getY() - 10 + GameUtil.randomInclusive(20);
        } else {
            x = attacker.getPosition().getX() - 10 + GameUtil.randomInclusive(20);
            y = attacker.getPosition().getY() - 10 + GameUtil.randomInclusive(20);
        }
        boolean blocked = WalkingCollisionMap.getTileFlags(x, y, target.getPosition().getPlane()) != 0;
        while (blocked) {
            x = attacker.getPosition().getX() - 10 + GameUtil.randomInclusive(20);
            y = attacker.getPosition().getY() - 10 + GameUtil.randomInclusive(20);
            blocked = WalkingCollisionMap.getTileFlags(x, y, target.getPosition().getPlane()) != 0;
        }
        if (x > 0) {
            target.moveTo(new Position(x, y));
        }
        CombatManager.stopCombat(target);
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
