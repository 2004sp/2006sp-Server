package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;
import com.rs2.model.skill.magic.SpellDefinition;

public final class KrilTsutsarothCombatDefinition
extends NpcCombatDefinition {
    public KrilTsutsarothCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH, 46, 6, 6945), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.KRIL_TSUTSAROTH_MAGIC_ATTACK)};
    }
}

