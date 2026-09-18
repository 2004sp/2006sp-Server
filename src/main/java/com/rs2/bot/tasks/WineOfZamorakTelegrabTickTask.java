package com.rs2.bot.tasks;

import com.rs2.bot.tasks.FaladorWineOfZamorakTelegrabBotTask;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.task.TickTask;

public final class WineOfZamorakTelegrabTickTask
extends TickTask {
    private FaladorWineOfZamorakTelegrabBotTask task;
    private final Player player;

    public WineOfZamorakTelegrabTickTask(FaladorWineOfZamorakTelegrabBotTask faladorWineOfZamorakTelegrabBotTask, int value2, Player player) {
        super(2);
        this.task = faladorWineOfZamorakTelegrabBotTask;
        this.player = player;
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.isRegistered() || !this.player.currentBotTask.usesCustomTaskAction || !this.player.botTaskState.equals("do task")) {
            this.stop();
            return;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            this.player.currentBotTask.startWalkToBank(this.player);
            this.stop();
            return;
        }
        GroundItemManager.getInstance();
        GroundItem groundItem = GroundItemManager.findVisibleItemAt(this.player, this.task.wineOfZamorakPosition);
        if (groundItem != null && groundItem.getItem().getId() == 245) {
            MagicSpellAction.scheduleTelekineticGrab(this.player, SpellDefinition.TELEKINETIC_GRAB, 245, this.task.wineOfZamorakPosition);
        }
    }
}

