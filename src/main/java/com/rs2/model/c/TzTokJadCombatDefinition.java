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

public final class TzTokJadCombatDefinition
extends NpcCombatDefinition {
    public TzTokJadCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createForcedMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.STAB, 97, 8, 2655, true), BaseCombatAttack.createFlaggedProjectileAttack(entity, entity2, CombatType.RANGED, AttackXpMode.LONGRANGE, 97, 8, 2652, new GraphicEffect(-1, 0), new GraphicEffect(451, 0), 411, ProjectileTiming.DRAGONFIRE_AND_JAD_RANGED, false, true), BaseCombatAttack.createFlaggedProjectileAttack(entity, entity2, CombatType.MAGIC, AttackXpMode.MAGIC, 97, 8, 2656, new GraphicEffect(447, 250), new GraphicEffect(157, 0), 448, ProjectileTiming.JAD_MAGIC, false, true)};
    }
}

