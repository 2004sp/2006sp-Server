package com.rs2.model.combat.requirement;

import com.rs2.model.combat.attack.MagicCombatAttack;
import com.rs2.model.combat.requirement.EquipmentItemRequirement;

public final class GodStaffRequirement
extends EquipmentItemRequirement {
    public GodStaffRequirement(MagicCombatAttack magicCombatAttack, int value4, int value22, int value32, boolean enabled2) {
        super(3, value22, 1, false);
    }

    @Override
    public final String getFailureMessage() {
        return "You must equip the proper god staff to use this spell!";
    }
}

