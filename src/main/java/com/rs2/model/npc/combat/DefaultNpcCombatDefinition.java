package com.rs2.model.npc.combat;

import com.rs2.model.Entity;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.combat.NpcCombatDefinition;

public final class DefaultNpcCombatDefinition
extends NpcCombatDefinition {
    public DefaultNpcCombatDefinition() {
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.createWeaponProfileMeleeAttack(entity, entity2, AttackXpMode.AGGRESSIVE, 0, WeaponProfile.FISTS)};
    }
}

