package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ElementalWorkshopQuest;
import com.rs2.model.task.TickTask;

public final class ElementalShieldSmithingTask
extends TickTask {
    private ElementalWorkshopQuest quest;
    private final Player player;
    private final int questStateAtStart;

    public ElementalShieldSmithingTask(ElementalWorkshopQuest elementalWorkshopQuest, int value3, Player player, int questStateAtStart) {
        super(3);
        this.quest = elementalWorkshopQuest;
        this.player = player;
        this.questStateAtStart = questStateAtStart;
    }

    @Override
    public final void execute() {
        this.player.getInventoryManager().replaceItem(new ItemStack(2893, 1), new ItemStack(2890, 1));
        if (this.questStateAtStart != 1) {
            this.quest.awardCompletionRewards(this.player);
        }
        this.stop();
    }
}

