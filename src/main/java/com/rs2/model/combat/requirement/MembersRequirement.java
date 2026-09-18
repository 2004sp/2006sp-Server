package com.rs2.model.combat.requirement;

import com.rs2.model.Entity;
import com.rs2.model.combat.requirement.CombatRequirement;
import com.rs2.model.player.Player;

public abstract class MembersRequirement
extends CombatRequirement {
    @Override
    final boolean isSatisfiedBy(Entity entity) {
        if (!entity.isPlayer()) {
            return true;
        }
        entity = (Player)entity;
        return ((Player)entity).isMember();
    }
}

