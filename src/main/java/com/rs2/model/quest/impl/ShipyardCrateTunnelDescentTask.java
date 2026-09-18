package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class ShipyardCrateTunnelDescentTask
extends TickTask {
    private final Player player;

    public ShipyardCrateTunnelDescentTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(5);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.moveTo(new Position(2802, 9169, 0));
        Player player = this.player;
        player.packetSender.closeInterfaces();
        this.player.applyDirectHit(1 + GameUtil.randomInclusive(9), HitType.NORMAL);
        this.player.setActionLocked(false);
        this.stop();
    }
}

