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

public final class GreenDragonCombatDefinition
extends NpcCombatDefinition {
    public GreenDragonCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.MAGIC, AttackXpMode.DRAGONFIRE, 50, 4, 81, new GraphicEffect(1, 100), new GraphicEffect(-1, 0), -1, ProjectileTiming.DRAGONFIRE_AND_JAD_RANGED), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH, 8, 4, 91)};
    }
}

