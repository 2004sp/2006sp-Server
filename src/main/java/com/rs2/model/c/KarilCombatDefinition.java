package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;

public final class KarilCombatDefinition
extends NpcCombatDefinition {
    public KarilCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createProjectileAttack(entity, entity2, CombatType.RANGED, AttackXpMode.LONGRANGE, 20, 4, 2075, new GraphicEffect(-1, 0), new GraphicEffect(-1, 0), 27, ProjectileTiming.STANDARD)};
    }
}

