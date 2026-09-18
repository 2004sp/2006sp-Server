package com.rs2.model.combat.requirement;

import com.rs2.model.combat.attack.MagicCombatAttack;
import com.rs2.model.combat.requirement.SkillLevelRequirement;

public final class MagicCombatLevelRequirement
extends SkillLevelRequirement {
    public MagicCombatLevelRequirement(MagicCombatAttack magicCombatAttack, int value3, int value22) {
        super(6, value22);
    }

    @Override
    public final String getFailureMessage() {
        return "Your level in magic is not high enough!";
    }
}

