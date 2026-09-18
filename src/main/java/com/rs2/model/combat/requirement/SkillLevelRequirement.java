package com.rs2.model.combat.requirement;

import com.rs2.model.Entity;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.player.Player;

public abstract class SkillLevelRequirement
extends CombatRequirement {
    private int skillId;
    private int requiredLevel;

    public SkillLevelRequirement(int skillId, int requiredLevel) {
        this.skillId = skillId;
        this.requiredLevel = requiredLevel;
    }

    @Override
    final boolean isSatisfiedBy(Entity entity) {
        if (!entity.isPlayer()) {
            return true;
        }
        return ((Player)(entity = (Player)entity)).getSkillManager().getCurrentLevels()[this.skillId] >= this.requiredLevel;
    }
}

