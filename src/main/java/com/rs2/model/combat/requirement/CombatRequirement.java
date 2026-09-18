package com.rs2.model.combat.requirement;

import com.rs2.model.Entity;
import com.rs2.model.player.Player;

public abstract class CombatRequirement {
    public abstract String getFailureMessage();

    abstract boolean isSatisfiedBy(Entity entity);

    public final boolean validate(Entity entity) {
        boolean enabled = this.isSatisfiedBy(entity);
        String failureMessage = this.getFailureMessage();
        if (!enabled && entity.isPlayer()) {
            entity = (Player)entity;
            ((Player)entity).packetSender.sendGameMessage(failureMessage);
        }
        return enabled;
    }
}
