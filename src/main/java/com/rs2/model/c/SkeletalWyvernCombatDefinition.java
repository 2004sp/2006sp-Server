package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.effect.MovementLockEffect;
import com.rs2.model.npc.combat.NpcCombatDefinition;

public final class SkeletalWyvernCombatDefinition
extends NpcCombatDefinition {
    public SkeletalWyvernCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createProjectileAttackWithEffect(entity, entity2, CombatType.MAGIC, AttackXpMode.ICY_BREATH, 55, 6, 2988, new GraphicEffect(1, 100), new GraphicEffect(-1, 0), -1, ProjectileTiming.DRAGONFIRE_AND_JAD_RANGED, new MovementLockEffect(8)), BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.RANGED, AttackXpMode.LONGRANGE, 13, 6, 2989, new GraphicEffect(-1, 0), new GraphicEffect(-1, 0), -1, ProjectileTiming.STANDARD), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH, 13, 6, 2986)};
    }
}

