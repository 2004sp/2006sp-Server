package com.rs2.model.skill.magic;

import com.rs2.model.combat.requirement.SkillLevelRequirement;
import com.rs2.model.skill.magic.MagicSpellAction;

public final class MagicLevelRequirement
extends SkillLevelRequirement {
    public MagicLevelRequirement(MagicSpellAction magicSpellAction, int value3, int value22) {
        super(6, value22);
    }

    @Override
    public final String getFailureMessage() {
        return "Your level in magic is not high enough!";
    }
}

