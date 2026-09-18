package com.rs2.model.player;

import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class DelayedBotLevelReplyTask
extends TickTask {
    private final Player bot;
    private final String replyMessage;

    public DelayedBotLevelReplyTask(Player player, int value2, Player player2, String replyMessage) {
        super(value2);
        this.bot = player2;
        this.replyMessage = replyMessage;
    }

    @Override
    public final void execute() {
        if (this.bot.isDead() || !this.bot.isRegistered()) {
            this.stop();
            return;
        }
        this.bot.queuePublicChatMessage(this.replyMessage);
        this.stop();
    }
}

