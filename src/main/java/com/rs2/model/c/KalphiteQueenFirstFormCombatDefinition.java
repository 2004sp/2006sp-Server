package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.effect.StatDrainEffect;
import com.rs2.model.npc.combat.NpcCombatDefinition;

public final class KalphiteQueenFirstFormCombatDefinition
extends NpcCombatDefinition {
    public KalphiteQueenFirstFormCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createKalphiteQueenRangedAttackWithEffect(entity, entity2, CombatType.RANGED, AttackXpMode.KQ_RANGED, 31, 4, 1184, new GraphicEffect(-1, 0), new GraphicEffect(-1, 0), 289, ProjectileTiming.STANDARD, new StatDrainEffect(5, 1), true), BaseCombatAttack.createKalphiteQueenMagicAttack(entity, entity2, CombatType.MAGIC, AttackXpMode.KQ_MAGIC, 31, 4, 1184, new GraphicEffect(278, 0), new GraphicEffect(281, 0), 280, ProjectileTiming.MAGIC, true), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.STAB, 31, 4, 1184), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.STAB, 31, 4, 1185)};
    }
}

