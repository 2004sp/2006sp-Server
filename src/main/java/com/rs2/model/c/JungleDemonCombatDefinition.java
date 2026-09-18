package com.rs2.model.c;

import com.rs2.model.Entity;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;
import com.rs2.model.skill.magic.SpellDefinition;

public final class JungleDemonCombatDefinition
extends NpcCombatDefinition {
    public JungleDemonCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH, 32, 6, 64), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH, 32, 6, 64), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH, 32, 6, 64), BaseCombatAttack.createMeleeAttack(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH, 32, 6, 64), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.JUNGLE_DEMON_WIND_WAVE), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.JUNGLE_DEMON_WATER_WAVE), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.JUNGLE_DEMON_EARTH_WAVE), BaseCombatAttack.createMagicAttack(entity, entity2, SpellDefinition.JUNGLE_DEMON_FIRE_WAVE)};
    }
}

