package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;
import com.rs2.model.skill.magic.SpellDefinition;

public final class AhrimCombatDefinition
extends NpcCombatDefinition {
    public AhrimCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.FIRE_WAVE), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.CONFUSE), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.CURSE)};
    }
}

