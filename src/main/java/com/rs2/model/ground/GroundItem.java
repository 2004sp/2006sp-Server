package com.rs2.model.ground;

import com.rs2.model.Entity;
import com.rs2.model.EntityReference;
import com.rs2.model.Position;
import com.rs2.model.ground.GroundItemVisibility;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.ElapsedTicks;

public final class GroundItem {
    private ItemStack item;
    private EntityReference source = null;
    private EntityReference owner = null;
    private ElapsedTicks timer;
    private GroundItemVisibility visibility = null;
    private Position position;
    private boolean respawning;
    private int respawnDelayTicks;
    private int privateRevealDelayTicks;
    private boolean restrictedModePickupAllowed = false;

    public GroundItem(ItemStack itemStack, Position position, int respawnDelayTicks, boolean enabled2) {
        this.item = itemStack;
        position = position.copy();
        this.position = position;
        this.timer = new ElapsedTicks();
        this.visibility = GroundItemVisibility.PUBLIC;
        this.respawnDelayTicks = respawnDelayTicks;
        this.respawning = true;
    }

    public GroundItem(ItemStack itemStack, Position position, boolean enabled2) {
        this.item = itemStack;
        position = position.copy();
        this.position = position;
        this.timer = new ElapsedTicks();
        this.visibility = GroundItemVisibility.PUBLIC;
        this.respawning = false;
    }

    public GroundItem(ItemStack itemStack, Position position, boolean enabled3, boolean enabled22) {
        this.item = itemStack;
        position = position.copy();
        this.position = position;
        this.timer = new ElapsedTicks();
        this.visibility = GroundItemVisibility.PUBLIC;
        this.respawning = false;
        this.restrictedModePickupAllowed = true;
    }

    public GroundItem(ItemStack itemStack, Position position, boolean enabled2, Entity entity) {
        this.item = itemStack;
        position = position.copy();
        this.position = position;
        this.timer = new ElapsedTicks();
        if (entity != null) {
            this.owner = this.source = new EntityReference(entity);
            this.visibility = GroundItemVisibility.PRIVATE;
        } else {
            this.visibility = GroundItemVisibility.PUBLIC;
        }
        this.respawning = false;
    }

    public GroundItem(ItemStack itemStack, Entity entity, Position position) {
        this(itemStack, position, false);
        this.source = new EntityReference(entity);
        this.visibility = GroundItemVisibility.PRIVATE;
        this.owner = this.source;
    }

    public GroundItem(ItemStack itemStack, Entity entity) {
        this(itemStack, entity, entity.getPosition());
    }

    public GroundItem(ItemStack itemStack, Entity entity, Entity entity2, Position position) {
        this(itemStack, entity, position);
        this.owner = new EntityReference(entity2);
    }

    public final EntityReference getSource() {
        if (this.source == null) {
            return null;
        }
        return this.source;
    }

    public final EntityReference getOwner() {
        if (this.owner == null) {
            return null;
        }
        return this.owner;
    }

    public final ItemStack getItem() {
        return this.item;
    }

    public final ElapsedTicks getTimer() {
        return this.timer;
    }

    public final int getRespawnDelayTicks() {
        return this.respawnDelayTicks;
    }

    public final int getPrivateRevealDelayTicks() {
        return this.privateRevealDelayTicks;
    }

    public final void setPrivateRevealDelayTicks(int ticks) {
        this.privateRevealDelayTicks = ticks;
    }

    public final GroundItemVisibility getVisibility() {
        return this.visibility;
    }

    public final void setVisibility(GroundItemVisibility groundItemVisibility) {
        this.visibility = groundItemVisibility;
    }

    public final Position getPosition() {
        return this.position;
    }

    public final boolean isRespawning() {
        return this.respawning;
    }

    public final boolean allowsRestrictedModePickup() {
        return this.restrictedModePickupAllowed;
    }

    public final boolean canStackWith(GroundItem groundItem) {
        if (groundItem == this) {
            return false;
        }
        if (groundItem.respawning || this.respawning) {
            return false;
        }
        if (groundItem.item.getId() != this.item.getId() || groundItem.visibility != this.visibility) {
            return false;
        }
        if (!groundItem.position.equals(this.position)) {
            return false;
        }
        if ((long)this.item.getAmount() + (long)groundItem.item.getAmount() > Integer.MAX_VALUE) {
            return false;
        }
        GroundItem groundItem2 = groundItem;
        switch (groundItem2.visibility) {
            case PUBLIC: {
                return true;
            }
            case PRIVATE: {
                boolean enabled = false;
                if (this.owner != null && this.owner.equals(groundItem.owner)) {
                    enabled = true;
                }
                return enabled;
            }
        }
        return false;
    }

    public final boolean isVisibleTo(Player player) {
        boolean enabled = this.position.getPlane() == player.getPosition().getPlane()
                && player.getLocalViewArea().containsExclusive(this.position);
        if (this.visibility == GroundItemVisibility.HIDDEN
                || this.visibility == GroundItemVisibility.PRIVATE
                && (this.owner == null || !this.owner.equals(player))) {
            return false;
        }
        if (player.gameMode == 0) {
            return enabled;
        }
        if (!enabled) {
            return false;
        }
        if (!this.respawning && this.getOwner() == null && !this.restrictedModePickupAllowed) {
            return false;
        }
        GroundItem groundItem = this;
        return groundItem.respawning || this.getOwner() == null || this.getOwner().resolve() == player || this.restrictedModePickupAllowed;
    }
}

