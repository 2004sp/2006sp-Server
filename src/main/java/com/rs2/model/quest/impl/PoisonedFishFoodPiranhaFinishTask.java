package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.PoisonedFishFoodPiranhaTask;
import com.rs2.model.task.TickTask;

public final class PoisonedFishFoodPiranhaFinishTask
extends TickTask {
    private final Player player;

    public PoisonedFishFoodPiranhaFinishTask(PoisonedFishFoodPiranhaTask poisonedFishFoodPiranhaTask, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        Player player = this.player;
        player.packetSender.sendGameMessage("...Then die and float to the surface.");
        this.stop();
    }
}

