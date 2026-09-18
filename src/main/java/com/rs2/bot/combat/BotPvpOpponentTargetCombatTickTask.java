package com.rs2.bot.combat;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotPvpCombatHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class BotPvpOpponentTargetCombatTickTask
extends TickTask {
    private final Player target;
    private final Player bot;

    public BotPvpOpponentTargetCombatTickTask(int value2, Player player, Player player2) {
        super(1);
        this.target = player;
        this.bot = player2;
    }

    @Override
    public final void execute() {
        if (this.target.isDead() || !this.target.isRegistered()) {
            BotCombatHelper.stopBotCombatTick(this.bot);
            this.stop();
        }
        if (this.bot.isDead() || !this.bot.isRegistered()) {
            BotCombatHelper.stopBotCombatTick(this.bot);
            this.stop();
        }
        if (this.target.getCombatTarget() == null) {
            BotCombatHelper.stopBotCombatTick(this.bot);
            this.stop();
        }
        BotPvpCombatHandler.processBotPvpCombatTick(this.bot, this.target);
    }
}

