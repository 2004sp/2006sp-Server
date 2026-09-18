package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class DeathAnimationTask
extends TickTask {
    private final int deathAnimationId;
    private final Entity defeatedEntity;
    private final Entity killer;

    public DeathAnimationTask(int value3, int deathAnimationId, Entity entity, Entity entity2) {
        super(2);
        this.deathAnimationId = deathAnimationId;
        this.defeatedEntity = entity;
        this.killer = entity2;
    }

    @Override
    public final void execute() {
        Player player;
        int value = this.deathAnimationId;
        if (this.defeatedEntity.isPlayer()) {
            player = (Player)this.defeatedEntity;
            player.setHideHeldItemsInAppearance(true);
            this.defeatedEntity.getUpdateState().setFaceEntity(-1);
            player.setAppearanceUpdateRequired(true);
            if (player.npcTransformationId > 0) {
                value = new Npc(player.npcTransformationId).getDefinition().getDeathAnimationId();
            }
        }
        this.defeatedEntity.getUpdateState().setAnimation(value);
        if (this.defeatedEntity != null && this.killer != null && this.defeatedEntity.isNpc() && this.killer.isPlayer()) {
            player = (Player)this.killer;
            Npc npc = (Npc)this.defeatedEntity;
            this.defeatedEntity.getUpdateState().setFaceEntity(-1);
            player.packetSender.sendSoundEffect(npc.getDeathSoundId(), 1, 20);
        }
        this.stop();
    }
}

