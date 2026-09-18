package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.DragonSlayerQuest;
import com.rs2.model.task.TickTask;

public final class DragonSlayerShipHoleRepairTask
extends TickTask {
    private final int repairProgressFlags;
    private final Player player;
    private final int questId;

    public DragonSlayerShipHoleRepairTask(DragonSlayerQuest dragonSlayerQuest, int value4, int repairProgressFlags, Player player, int questId) {
        super(2);
        this.repairProgressFlags = repairProgressFlags;
        this.player = player;
        this.questId = questId;
    }

    @Override
    public final void execute() {
        int value = 2;
        if ((this.repairProgressFlags & 8) == 0) {
            value = 8;
        }
        if ((this.repairProgressFlags & 4) == 0) {
            value = 4;
        }
        if ((this.repairProgressFlags & 2) == 0) {
            value = 2;
        }
        this.player.setActionLocked(false);
        this.player.getInventoryManager().removeItem(new ItemStack(960, 1));
        this.player.getInventoryManager().removeItem(new ItemStack(1539, 30));
        if (value == 2) {
            this.player.getDialogueManager().showTwoLineStatement("You nail a plank over the hole, but you still need more planks to", "close the hole completely.");
            this.player.addQuestState(this.questId, value);
        }
        if (value == 4) {
            this.player.getDialogueManager().showTwoLineStatement("You nail a plank over the hole, but you still need one more plank to", "close the hole completely.");
            this.player.addQuestState(this.questId, value);
        }
        if (value == 8) {
            this.player.moveTo(new Position(this.player.getPosition().getX(), this.player.getPosition().getY(), 2));
            this.player.getDialogueManager().showTwoLineStatement("You nail a final plank over the hole. You have successfully patched", "the hole in the ship.");
            this.player.addQuestState(this.questId, value);
        }
        this.stop();
    }
}
