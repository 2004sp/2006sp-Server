package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.PiratesTreasureQuest;
import com.rs2.model.quest.impl.RumBananaCratePromptTask;
import com.rs2.model.task.TickTask;

public final class RumBananaCrateSearchTask
extends TickTask {
    private final TickTask cratePromptTask;
    private final Player player;

    public RumBananaCrateSearchTask(PiratesTreasureQuest piratesTreasureQuest, int value2, Player player) {
        super(3);
        this.player = player;
        this.cratePromptTask = new RumBananaCratePromptTask(this, 2, player);
    }

    @Override
    public final void execute() {
        this.player.getInventoryManager().addOrDropItem(new ItemStack(431, 1));
        this.player.getUpdateState().setAnimation(832);
        Player player = this.player;
        player.packetSender.sendGameMessage("You find your bottle of rum in amongst the bananas.");
        World.getTaskScheduler().schedule(this.cratePromptTask);
        this.stop();
    }
}
