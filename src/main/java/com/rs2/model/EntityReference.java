package com.rs2.model;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.player.Player;

public class EntityReference {
    private int referenceId;
    private boolean playerReference;
    private Entity cachedEntity;
    private String playerUsername;

    public EntityReference(Entity entity) {
        this.cachedEntity = entity;
        this.referenceId = entity.getReferenceId();
        this.playerReference = entity.isPlayer();
        if (entity.isPlayer()) {
            entity = (Player)entity;
            this.playerUsername = ((Player)entity).getUsername();
        }
    }

    public final Entity resolve() {
        if (this.cachedEntity == null || this.cachedEntity.getIndex() == -1) {
            this.cachedEntity = null;
            Entity[] entities = this.playerReference ? World.getPlayers() : World.getNpcs();
            int length = entities.length;
            int index = 0;
            while (index < length) {
                Entity entity = entities[index];
                if (entity != null) {
                    Player player;
                    if (entity.isPlayer() && this.playerReference && (player = (Player)entity).getUsername().equals(this.playerUsername)) {
                        return entity;
                    }
                    if (!this.playerReference && entity.getReferenceId() == this.referenceId) {
                        this.cachedEntity = entity;
                        break;
                    }
                }
                ++index;
            }
        }
        return this.cachedEntity;
    }

    public boolean equals(Object cachedEntity) {
        if (cachedEntity == null || !(cachedEntity instanceof EntityReference) && !(cachedEntity instanceof Entity)) {
            return false;
        }
        if (cachedEntity instanceof Entity) {
            if (((Entity)(cachedEntity = (Entity)cachedEntity)).isPlayer() && this.playerReference) {
                return ((Player)(cachedEntity = (Player)cachedEntity)).getUsername().equals(this.playerUsername);
            }
            if (this.cachedEntity == cachedEntity) {
                return true;
            }
            if (((Entity)cachedEntity).isPlayer() == this.playerReference && ((Entity)cachedEntity).getReferenceId() == this.referenceId) {
                this.cachedEntity = (Entity)cachedEntity;
                return true;
            }
            return false;
        }
        cachedEntity = (EntityReference)cachedEntity;
        if (((EntityReference)cachedEntity).playerReference && this.playerReference) {
            return ((EntityReference)cachedEntity).playerUsername.equals(this.playerUsername);
        }
        return ((EntityReference)cachedEntity).referenceId == this.referenceId && ((EntityReference)cachedEntity).playerReference == this.playerReference;
    }
}

