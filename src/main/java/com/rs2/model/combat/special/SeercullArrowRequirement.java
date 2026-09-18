package com.rs2.model.combat.special;

import com.rs2.model.combat.requirement.EquipmentItemRequirement;
import com.rs2.model.combat.special.SeercullSpecialAttack;

public final class SeercullArrowRequirement
extends EquipmentItemRequirement {
    public SeercullArrowRequirement(SeercullSpecialAttack seercullSpecialAttack, int value4, int value22, int value32, boolean enabled2) {
        super(value4, value22, 2, true);
    }

    @Override
    public final String getFailureMessage() {
        return "You do not have enough arrows!";
    }
}

