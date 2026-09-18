package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.quest.impl.PoisonedFishFoodPiranhaFinishTask;
import com.rs2.model.task.TickTask;

public final class PoisonedFishFoodPiranhaTask
extends TickTask {
    private final TickTask finishTask;
    private final Player player;

    public PoisonedFishFoodPiranhaTask(ErnestTheChickenQuest ernestTheChickenQuest, int value2, Player player) {
        super(1);
        this.player = player;
        this.finishTask = new PoisonedFishFoodPiranhaFinishTask(this, 2, player);
    }

    @Override
    public final void execute() {
        Player player = this.player;
        player.packetSender.sendGameMessage("The piranhas start eating the food...");
        World.getTaskScheduler().schedule(this.finishTask);
        this.stop();
    }
}
