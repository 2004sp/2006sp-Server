package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class MonkeyDenturesCrateSearchTask
extends TickTask {
    private final Player player;

    public MonkeyDenturesCrateSearchTask(MonkeyMadnessQuest monkeyMadnessQuest, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.player.getDialogueManager().showThreeLineItemMessage("", "This crate is full of ... magical monkey talking", "dentures!", new ItemStack(4006, 1));
        this.stop();
    }
}

