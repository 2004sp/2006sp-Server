package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class MonkeyAmuletMouldCrateSearchTask
extends TickTask {
    private final Player player;

    public MonkeyAmuletMouldCrateSearchTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.player.getDialogueManager().showItemMessage("This crate is full of ... monkey amulet moulds!", new ItemStack(4020, 1));
        this.stop();
    }
}

