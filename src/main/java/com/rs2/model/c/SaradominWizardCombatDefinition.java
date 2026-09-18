package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;
import com.rs2.model.skill.magic.SpellDefinition;

public final class SaradominWizardCombatDefinition
extends NpcCombatDefinition {
    public SaradominWizardCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.SARADOMIN_STRIKE), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.STAB, 14, 4, 402)};
    }
}

