package com.rs2.bot;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class DropPartyGroundItemPickupTask
extends TickTask {
    private final Player participant;
    private final GroundItem groundItem;

    public DropPartyGroundItemPickupTask(int value2, Player player, GroundItem groundItem) {
        super(value2);
        this.participant = player;
        this.groundItem = groundItem;
    }

    @Override
    public final void execute() {
        if (this.participant.isDead() || !this.participant.isRegistered()) {
            this.stop();
            return;
        }
        BotCombatHelper.pickupVisibleGroundItem(this.participant, this.groundItem.getItem().getId(), this.groundItem.getPosition());
        this.stop();
    }
}

