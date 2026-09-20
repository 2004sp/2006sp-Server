package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.player.Player;

public final class DragonClawsSpecialDefinition
extends SpecialAttackDefinition {
    public DragonClawsSpecialDefinition(int energyCost, String ... weaponNamePatterns) {
        super(energyCost, weaponNamePatterns);
    }

    @Override
    public final WeaponCombatAttack createAttack(Player player, Entity target,
                                                  WeaponProfile weaponProfile) {
        return new DragonClawsSpecialAttack(this, player, target, weaponProfile);
    }
}
