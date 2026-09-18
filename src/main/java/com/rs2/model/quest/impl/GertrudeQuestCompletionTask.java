package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GertrudeRewardFoodTask;
import com.rs2.model.task.TickTask;

public final class GertrudeQuestCompletionTask
extends TickTask {
    private final GertrudeRewardFoodTask rewardFoodTask;
    private final Player player;

    public GertrudeQuestCompletionTask(GertrudeRewardFoodTask gertrudeRewardFoodTask, int value2, Player player) {
        super(4);
        this.rewardFoodTask = gertrudeRewardFoodTask;
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.rewardFoodTask.quest.awardCompletionRewards(this.player);
        this.stop();
    }
}
