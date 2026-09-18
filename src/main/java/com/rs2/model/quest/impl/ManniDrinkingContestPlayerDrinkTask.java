package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.quest.impl.ManniDrinkingContestResultTask;
import com.rs2.model.task.TickTask;

public final class ManniDrinkingContestPlayerDrinkTask
extends TickTask {
    private final TickTask resultTask;
    private final Player player;

    public ManniDrinkingContestPlayerDrinkTask(FremennikTrialsQuest fremennikTrialsQuest, int value3, Player player, int value22) {
        super(6);
        this.player = player;
        this.resultTask = new ManniDrinkingContestResultTask(this, 7, player, value22);
    }

    @Override
    public final void execute() {
        if (this.player.pendingGameMode == 3711) {
            this.player.getInventoryManager().removeItem(new ItemStack(3711, 1));
            Player player = this.player;
            player.packetSender.sendGameMessage("You drink from your keg. You don't feel at all drunk.");
        } else {
            this.player.getInventoryManager().removeItem(new ItemStack(3801, 1));
            Player player = this.player;
            player.packetSender.sendGameMessage("You drink from your keg. You feel extremely drunk...");
        }
        this.player.getUpdateState().setAnimation(1330);
        World.getTaskScheduler().schedule(this.resultTask);
        this.stop();
    }
}
