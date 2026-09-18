package com.rs2.bot.tasks;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.tasks.WildernessRuniteMineBotTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class WildernessRuniteMineEscapeMonitorTickTask
extends TickTask {
    private final Player player;

    public WildernessRuniteMineEscapeMonitorTickTask(WildernessRuniteMineBotTask wildernessRuniteMineBotTask, int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.isRegistered() || !this.player.currentBotTask.usesEscapeMonitor) {
            this.stop();
            return;
        }
        if (this.player.getInventoryManager().getItemAmount(451) >= 8 && this.player.botTaskState.equals("do task") && GameUtil.randomInt(3) == 0) {
            this.player.currentBotTask.startWalkToBank(this.player);
            return;
        }
        if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0 && this.player.botTaskState.equals("do task")) {
            this.player.currentBotTask.startWalkToBank(this.player);
            return;
        }
        if (!this.player.getRecentCombatTimer().hasElapsed()) {
            BotCombatEscapeHandler.tryStartBotCombatEscape(this.player);
            this.player.botTaskState = "escape";
        }
    }
}

