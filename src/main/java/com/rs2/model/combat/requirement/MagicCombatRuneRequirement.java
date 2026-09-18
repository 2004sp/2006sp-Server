package com.rs2.model.combat.requirement;

import com.rs2.model.combat.attack.MagicCombatAttack;
import com.rs2.model.combat.requirement.SpellRuneCostRequirement;
import com.rs2.model.skill.magic.SpellDefinition;

public final class MagicCombatRuneRequirement
extends SpellRuneCostRequirement {
    public MagicCombatRuneRequirement(MagicCombatAttack magicCombatAttack, SpellDefinition spellDefinition) {
        super(spellDefinition);
    }

    @Override
    public final String getFailureMessage() {
        return "You do not have the runes required!";
    }
}

