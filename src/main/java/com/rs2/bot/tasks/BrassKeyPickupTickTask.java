package com.rs2.bot.tasks;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.tasks.EdgevilleDungeonBrassKeyBotTask;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class BrassKeyPickupTickTask
extends TickTask {
    private EdgevilleDungeonBrassKeyBotTask task;
    private final Player player;

    public BrassKeyPickupTickTask(EdgevilleDungeonBrassKeyBotTask edgevilleDungeonBrassKeyBotTask, int value2, Player player) {
        super(2);
        this.task = edgevilleDungeonBrassKeyBotTask;
        this.player = player;
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.isRegistered() || !this.player.currentBotTask.usesCustomTaskAction || !this.player.botTaskState.equals("do task")) {
            this.stop();
            return;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0 || this.player.ownsItem(983)) {
            this.player.botTaskReturnToBankRequested = true;
            this.player.currentBotTask.startWalkToBank(this.player);
            this.stop();
            return;
        }
        GroundItemManager.getInstance();
        GroundItem groundItem = GroundItemManager.findVisibleItemAt(this.player, this.task.brassKeySpawnPosition);
        if (groundItem != null && groundItem.getItem().getId() == 983) {
            this.player.botLootPickupTargets.clear();
            this.player.botLootPickupTargets.add(groundItem);
            BotCombatHelper.pickupBotCombatGroundItem(this.player, 983, this.task.brassKeySpawnPosition);
        }
    }
}

