package com.rs2.model.gameplay;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class SmokeDungeonDamageTask
extends TickTask {
    private final Player player;

    public SmokeDungeonDamageTask(int value2, Player player) {
        super(20);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        if (this.player.isInSmokeDungeon()) {
            if (this.player.getEquipmentManager().getItemIdAtSlot(0) != 4164 && this.player.getCurrentHitpoints() > 1) {
                this.player.applyDirectHit(this.player.getCurrentHitpoints() - 20 <= 0 ? this.player.getCurrentHitpoints() - 1 : 20, HitType.NORMAL);
                Player player = this.player;
                player.packetSender.sendGameMessage("You should wear a facemask to protect yourself from the smoke.");
                return;
            }
        } else {
            this.player.activeRecurringEffectId = -1;
            this.stop();
        }
    }
}
