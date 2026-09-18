package com.rs2.model.skill.prayer;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.combat.AttackValidationResult;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatCycleEvent;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class RetributionPrayerTask
extends TickTask {
    private final Player player;
    private final boolean multiCombat;
    private final Entity source;
    private final Entity primaryTarget;
    private final HitDefinition hitDefinition;

    public RetributionPrayerTask(int value2, Player player, boolean multiCombat, Entity entity, Entity entity2, HitDefinition hitDefinition) {
        super(3);
        this.player = player;
        this.multiCombat = multiCombat;
        this.source = entity;
        this.primaryTarget = entity2;
        this.hitDefinition = hitDefinition;
    }

    @Override
    public final void execute() {
        AttackValidationResult attackValidationResult;
        Entity entity;
        this.player.getUpdateState().setGraphic(437, 0);
        if (!this.multiCombat) {
            new CombatAction(this.source, this.primaryTarget, this.hitDefinition).queue();
            this.stop();
            return;
        }
        Entity[] entityArray = World.getPlayers();
        int length = entityArray.length;
        int index = 0;
        while (index < length) {
            entity = entityArray[index];
            if (entity != null && entity != this.source) {
                attackValidationResult = CombatCycleEvent.validateAttack(this.source, entity);
                if (GameUtil.getDistance(this.source.getPosition(), entity.getPosition()) <= 1 && attackValidationResult == AttackValidationResult.VALID) {
                    new CombatAction(this.source, entity, this.hitDefinition).queue();
                    if (!this.multiCombat) {
                        this.stop();
                        return;
                    }
                }
            }
            ++index;
        }
        entityArray = World.getNpcs();
        length = entityArray.length;
        index = 0;
        while (index < length) {
            entity = entityArray[index];
            if (entity != null) {
                attackValidationResult = CombatCycleEvent.validateAttack(this.source, entity);
                if (this.source.isWithinReach(entity, 1) && attackValidationResult == AttackValidationResult.VALID) {
                    new CombatAction(this.source, entity, this.hitDefinition).queue();
                    if (!this.multiCombat) {
                        this.stop();
                        return;
                    }
                }
            }
            ++index;
        }
        this.stop();
    }
}

