package com.rs2.bot.tasks;

import com.rs2.bot.tasks.VarrockSewerGiantRatCombatBotTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

public final class VarrockSewerGiantRatDungeonEntryTickTask
extends TickTask {
    private final Player player;

    public VarrockSewerGiantRatDungeonEntryTickTask(VarrockSewerGiantRatCombatBotTask varrockSewerGiantRatCombatBotTask, int value2, Player player) {
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
        arrayList.add(882);
        this.player.interactWithBotObjectTargetsNoRetry(arrayList, false);
        this.stop();
    }
}

