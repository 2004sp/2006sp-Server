package com.rs2.model.combat.requirement;

import com.rs2.model.combat.attack.MagicCombatAttack;
import com.rs2.model.combat.requirement.MembersRequirement;

public final class MagicCombatMembersRequirement
extends MembersRequirement {
    public MagicCombatMembersRequirement(MagicCombatAttack magicCombatAttack) {
    }

    @Override
    public final String getFailureMessage() {
        return "You need a members account to access members content.";
    }
}

