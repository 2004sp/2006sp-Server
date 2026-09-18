package com.rs2.model.interaction;

import com.rs2.model.interaction.FirstObjectActionTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class SearchCrateTask
extends CycleEvent {
    private final Player player;

    public SearchCrateTask(FirstObjectActionTask firstObjectActionTask, Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (GameUtil.randomInclusive(99) == 0) {
            ItemStack[] crateRewards = new ItemStack[]{new ItemStack(995, 10), new ItemStack(686), new ItemStack(687), new ItemStack(689), new ItemStack(690), new ItemStack(697), new ItemStack(1059), new ItemStack(1061)};
            ItemStack itemStack = crateRewards[GameUtil.randomExclusive(8)];
            this.player.getInventoryManager().addItem((ItemStack)itemStack);
            Player player = this.player;
            player.packetSender.sendGameMessage("You find some " + ((ItemStack)itemStack).getDefinition().getName().toLowerCase() + "!");
        } else {
            Player player = this.player;
            player.packetSender.sendGameMessage("You find nothing of interest.");
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

