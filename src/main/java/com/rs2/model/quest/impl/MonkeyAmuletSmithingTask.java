package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class MonkeyAmuletSmithingTask
extends TickTask {
    private final Player player;

    public MonkeyAmuletSmithingTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(4);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.player.getInventoryManager().removeItem(new ItemStack(4007, 1));
        this.player.getInventoryManager().addOrDropItem(new ItemStack(4022, 1));
        Player player = this.player;
        player.packetSender.sendGameMessage("You smith the enchanted gold into an amulet.");
        this.stop();
    }
}

