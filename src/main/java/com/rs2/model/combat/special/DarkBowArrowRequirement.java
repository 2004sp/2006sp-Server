package com.rs2.model.combat.special;

import com.rs2.model.combat.requirement.EquipmentItemRequirement;
import com.rs2.model.combat.special.DarkBowSpecialAttack;

public final class DarkBowArrowRequirement
extends EquipmentItemRequirement {
    public DarkBowArrowRequirement(DarkBowSpecialAttack darkBowSpecialAttack, int value4, int value22, int value32, boolean enabled2) {
        super(value4, value22, 2, true);
    }

    @Override
    public final String getFailureMessage() {
        return "You do not have enough arrows!";
    }
}

