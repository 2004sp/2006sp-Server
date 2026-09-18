package com.rs2.model.skill.magic;

import com.rs2.model.combat.requirement.SpellRuneCostRequirement;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;

public final class RuneRequirement
extends SpellRuneCostRequirement {
    public RuneRequirement(MagicSpellAction magicSpellAction, SpellDefinition spellDefinition) {
        super(spellDefinition);
    }

    @Override
    public final String getFailureMessage() {
        return "You do not have the runes required!";
    }
}

