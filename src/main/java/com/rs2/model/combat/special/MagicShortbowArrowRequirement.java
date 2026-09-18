package com.rs2.model.combat.special;

import com.rs2.model.combat.requirement.EquipmentItemRequirement;
import com.rs2.model.combat.special.MagicShortbowSpecialAttack;

public final class MagicShortbowArrowRequirement
extends EquipmentItemRequirement {
    public MagicShortbowArrowRequirement(MagicShortbowSpecialAttack magicShortbowSpecialAttack, int value4, int value22, int value32, boolean enabled2) {
        super(value4, value22, 2, true);
    }

    @Override
    public final String getFailureMessage() {
        return "You do not have enough arrows!";
    }
}

