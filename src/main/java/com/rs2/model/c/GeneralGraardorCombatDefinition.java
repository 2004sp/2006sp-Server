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

public final class GeneralGraardorCombatDefinition
extends NpcCombatDefinition {
    public GeneralGraardorCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.RANGED, AttackXpMode.LONGRANGE, 35, 6, 7060, new GraphicEffect(-1, 0), new GraphicEffect(-1, 0), 1200, ProjectileTiming.STANDARD), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.CRUSH, 60, 6, 7060)};
    }
}

