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

public final class ElvargCombatDefinition
extends NpcCombatDefinition {
    public ElvargCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.MAGIC, AttackXpMode.DRAGONFIRE_FAR, 69, 4, 81, new GraphicEffect(-1, 0), new GraphicEffect(-1, 0), 438, ProjectileTiming.STANDARD), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH, 8, 4, 91)};
    }
}

