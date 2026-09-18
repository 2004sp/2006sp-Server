package com.rs2.bot.tasks;

import com.rs2.bot.tasks.EdgevilleDungeonSkeletonCombatBotTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

public final class SkeletonDungeonEntryTickTask
extends TickTask {
    private final Player player;

    public SkeletonDungeonEntryTickTask(EdgevilleDungeonSkeletonCombatBotTask edgevilleDungeonSkeletonCombatBotTask, int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.isRegistered()) {
            this.stop();
            return;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(1570);
        this.player.interactWithBotObjectTargetsNoRetry(arrayList, false);
        this.stop();
    }
}

