package com.rs2.model.interaction;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.interaction.FirstObjectActionTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class SearchHayTask
extends CycleEvent {
    private final Player player;

    public SearchHayTask(FirstObjectActionTask firstObjectActionTask, Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (GameUtil.randomInclusive(99) == 0) {
            if (GameUtil.randomInclusive(1) == 0) {
                this.player.getDialogueManager().showPlayerTwoLineDialogue("Wow! A needle!", "Now what are the chances of finding that?", 588);
                this.player.getDialogueManager().finishDialogue();
                this.player.getInventoryManager().addItem(new ItemStack(1733));
            } else {
                this.player.applyDirectHit(1, HitType.NORMAL);
                this.player.getDialogueManager().showPlayerOneLineDialogue("Ow! There's something sharp in there!", 599);
                this.player.getDialogueManager().finishDialogue();
            }
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

