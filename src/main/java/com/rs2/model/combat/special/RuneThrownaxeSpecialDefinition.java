package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.special.RuneThrownaxeSpecialAttack;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.player.Player;

public final class RuneThrownaxeSpecialDefinition
extends SpecialAttackDefinition {
    public RuneThrownaxeSpecialDefinition(int value2, String ... stringValues2) {
        super(value2, stringValues2);
    }

    @Override
    public final WeaponCombatAttack createAttack(Player player, Entity entity, WeaponProfile weaponProfile) {
        return new RuneThrownaxeSpecialAttack(this, player, entity, weaponProfile, player, entity, weaponProfile);
    }
}

