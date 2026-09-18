package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.PiratesTreasureQuest;
import com.rs2.model.task.TickTask;

public final class HectorsChestMessageTask
extends TickTask {
    private final Player player;

    public HectorsChestMessageTask(PiratesTreasureQuest piratesTreasureQuest, int value2, Player player) {
        super(4);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.getInventoryManager().removeItem(new ItemStack(432, 1));
        this.player.getInventoryManager().addOrDropItem(new ItemStack(433, 1));
        Player player = this.player;
        player.packetSender.sendGameMessage("You take the message from the chest.");
        this.stop();
    }
}

