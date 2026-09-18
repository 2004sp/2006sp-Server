package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;
import com.rs2.model.skill.magic.SpellDefinition;

public final class MelzarTheMadCombatDefinition
extends NpcCombatDefinition {
    public MelzarTheMadCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.CRUSH, 5, 4, 422), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH, 5, 4, 422), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.CURSE), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.FIRE_STRIKE), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.MELZAR_CABBAGE_SPELL), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.WEAKEN)};
    }
}

