package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;
import com.rs2.model.skill.magic.SpellDefinition;

public final class AberrantSpecterCombatDefinition
extends NpcCombatDefinition {
    public AberrantSpecterCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.ABERRANT_SPECTER_MAGIC_ATTACK)};
    }
}

