package com.rs2.model.skill.magic;

import com.rs2.model.combat.requirement.MembersRequirement;
import com.rs2.model.skill.magic.MagicSpellAction;

public final class MagicMembersAccountRequirement
extends MembersRequirement {
    public MagicMembersAccountRequirement(MagicSpellAction magicSpellAction) {
    }

    @Override
    public final String getFailureMessage() {
        return "You need a members account to access members content.";
    }
}

