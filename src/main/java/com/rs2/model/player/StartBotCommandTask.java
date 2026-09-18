package com.rs2.model.player;

import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class StartBotCommandTask
extends TickTask {
    private final Player player;
    private final Player botModePlayer;

    public StartBotCommandTask(Player player, int value2, Player player2) {
        super(2);
        this.player = player;
        this.botModePlayer = player2;
    }

    @Override
    public final void execute() {
        this.player.completeQuestJournal();
        this.botModePlayer.botMode = 4;
        this.player.resumeBotTaskState();
        this.stop();
    }
}
