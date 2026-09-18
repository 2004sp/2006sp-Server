package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class ShipyardGateLockpickTask
extends TickTask {
    private final Player player;
    private final boolean successful;

    public ShipyardGateLockpickTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player, boolean successful) {
        super(3);
        this.player = player;
        this.successful = successful;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        if (this.successful) {
            Player player = this.player;
            player.packetSender.openSingleDoor(4799, 2771, 2795, this.player.getPosition().getPlane());
            player = this.player;
            player.packetSender.queueRelativeMovementStep(0, 1, true);
            player = this.player;
            player.packetSender.sendGameMessage("You manage to pick the lock.");
        } else {
            Player player = this.player;
            player.packetSender.sendGameMessage("You fail to pick the lock.");
        }
        this.stop();
    }
}
