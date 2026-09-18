package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.attack.DefaultCombatAttackProvider;

public interface CombatAttackProvider {
    public static final CombatAttackProvider DEFAULT = new DefaultCombatAttackProvider();

    public CombatAttack[] createAttacks(Entity attacker, Entity target);
}
