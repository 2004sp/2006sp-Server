package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

public final class GloughChestScrollFindTask
extends TickTask {
    private final Player player;

    public GloughChestScrollFindTask(GrandTreeQuest grandTreeQuest, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.player.getDialogueManager().showItemMessage("You have found a scroll!", new ItemStack(794, 1));
        this.player.getInventoryManager().addOrDropItem(new ItemStack(794, 1));
        this.stop();
    }
}

