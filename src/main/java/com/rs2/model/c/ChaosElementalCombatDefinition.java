package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;
import com.rs2.model.skill.magic.SpellDefinition;

public final class ChaosElementalCombatDefinition
extends NpcCombatDefinition {
    public ChaosElementalCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.CHAOS_ELEMENTAL_DISARM), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.CHAOS_ELEMENTAL_RANDOM_TELEPORT), BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.RANGED, AttackXpMode.LONGRANGE, 28, 4, 3146, new GraphicEffect(556, 0), new GraphicEffect(558, 0), 557, ProjectileTiming.MAGIC), BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.MELEE, AttackXpMode.MELEE_FAR, 28, 4, 3146, new GraphicEffect(556, 0), new GraphicEffect(558, 0), 557, ProjectileTiming.MAGIC), BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.MAGIC, AttackXpMode.MAGIC, 28, 4, 3146, new GraphicEffect(556, 0), new GraphicEffect(558, 0), 557, ProjectileTiming.MAGIC), BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.MAGIC, AttackXpMode.MAGIC, 28, 4, 3146, new GraphicEffect(556, 0), new GraphicEffect(558, 0), 557, ProjectileTiming.MAGIC), BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.MAGIC, AttackXpMode.MAGIC, 28, 4, 3146, new GraphicEffect(556, 0), new GraphicEffect(558, 0), 557, ProjectileTiming.MAGIC)};
    }
}

