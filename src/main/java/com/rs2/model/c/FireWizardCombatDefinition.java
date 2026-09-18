package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;
import com.rs2.model.skill.magic.SpellDefinition;

public final class FireWizardCombatDefinition
extends NpcCombatDefinition {
    public FireWizardCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.FIRE_WIZARD_FIRE_STRIKE), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.WEAKEN)};
    }
}

