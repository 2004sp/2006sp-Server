package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GertrudesCatQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class KittenCrateSearchTask
extends TickTask {
    private final Player player;

    public KittenCrateSearchTask(GertrudesCatQuest gertrudesCatQuest, int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (GameUtil.randomInt(5) == 0) {
            this.player.getDialogueManager().showOneLineStatement("You find a kitten! You carefully place it in your backpack.");
            this.player.getInventoryManager().addOrDropItem(new ItemStack(1554, 1));
        } else {
            Player player = this.player;
            player.packetSender.sendGameMessage("You find nothing.");
        }
        this.player.setActionLocked(false);
        this.stop();
    }
}

