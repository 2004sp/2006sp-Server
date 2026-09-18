package com.rs2.model.combat.requirement;

import com.rs2.model.Entity;
import com.rs2.model.combat.requirement.CombatRequirement;

public abstract class CombatCostRequirement
extends CombatRequirement {
    public abstract void consume(Entity entity);
}
