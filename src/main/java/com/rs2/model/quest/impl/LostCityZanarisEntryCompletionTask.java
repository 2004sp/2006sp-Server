package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.LostCityQuest;
import com.rs2.model.task.TickTask;

public final class LostCityZanarisEntryCompletionTask
extends TickTask {
    private final LostCityQuest quest;
    private final Player player;

    public LostCityZanarisEntryCompletionTask(LostCityQuest lostCityQuest, int value2, Player player) {
        super(4);
        this.quest = lostCityQuest;
        this.player = player;
    }

    @Override
    public final void execute() {
        this.quest.awardCompletionRewards(this.player);
        this.stop();
    }
}
