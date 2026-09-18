package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;

public final class YtMejKotCombatDefinition
extends NpcCombatDefinition {
    public YtMejKotCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.CRUSH, 25, 4, 2637)};
    }
}

