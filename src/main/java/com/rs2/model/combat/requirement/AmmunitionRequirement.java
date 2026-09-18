package com.rs2.model.combat.requirement;

import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.requirement.EquipmentItemRequirement;

public final class AmmunitionRequirement
extends EquipmentItemRequirement {
    public AmmunitionRequirement(WeaponCombatAttack weaponCombatAttack, int value4, int value22, int value32, boolean enabled2) {
        super(value4, value22, value32, true);
    }

    @Override
    public final String getFailureMessage() {
        return "You have no ammo left!";
    }
}

