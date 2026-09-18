package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.task.TickTask;

public final class CompostHeapKeyFindTask
extends TickTask {
    private final Player player;

    public CompostHeapKeyFindTask(ErnestTheChickenQuest ernestTheChickenQuest, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.getInventoryManager().addOrDropItem(new ItemStack(275, 1));
        Player player = this.player;
        player.packetSender.sendGameMessage("...and find a small key.");
        this.stop();
    }
}

