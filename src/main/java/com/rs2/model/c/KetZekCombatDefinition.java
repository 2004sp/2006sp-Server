package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;

public final class KetZekCombatDefinition
extends NpcCombatDefinition {
    public KetZekCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.STAB, 54, 4, 2644), BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.MAGIC, AttackXpMode.MAGIC, 49, 4, 2647, new GraphicEffect(-1, 0), new GraphicEffect(446, 0), 445, ProjectileTiming.MAGIC)};
    }
}

