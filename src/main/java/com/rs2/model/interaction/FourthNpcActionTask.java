package com.rs2.model.interaction;

import com.rs2.model.EntityTargetMovement;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.BankManager;
import com.rs2.model.player.Player;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class FourthNpcActionTask
extends TickTask {
    private final Player player;
    private final int actionSequence;
    private final Npc npc;

    public FourthNpcActionTask(int value3, boolean enabled2, Player player, int actionSequence, Npc npc) {
        super(1, true);
        this.player = player;
        this.actionSequence = actionSequence;
        this.npc = npc;
    }

    @Override
    public final void execute() {
        if (this.player == null || !this.player.isCurrentActionSequence(this.actionSequence) || this.npc.isDead()) {
            this.stop();
            return;
        }
        if (!this.player.isWithinReach(this.npc, 1) || this.player.isOverlapping(this.npc)) {
            return;
        }
        // Raw NPC cache action slot 4 is packet 18 in the matching 2006 client.
        if (this.npc.getNpcId() == CastleWarsManager.LANTHUS_NPC_ID
                && this.npc.getDefinition().actionStartsWith(4, "trade")) {
            this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
            this.player.setInteractionTarget(this.npc);
            this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
            ShopManager.openCastleWarsRewardShop(this.player);
            EntityTargetMovement.clearMovementTarget(this.player);
            this.stop();
            return;
        }
        if (!GameUtil.hasClearPath(this.player.getPosition(), this.npc.getPosition(), true)) {
            return;
        }
        EntityTargetMovement.clearMovementTarget(this.player);
        this.player.getUpdateState().setFaceEntity(this.npc.getEncodedIndex());
        this.npc.getUpdateState().setFaceEntity(this.player.getEncodedIndex());
        switch (this.player.getInteractionTargetId()) {
            case 494: {
                BankManager.openBank(this.player);
            }
        }
        this.stop();
    }
}

