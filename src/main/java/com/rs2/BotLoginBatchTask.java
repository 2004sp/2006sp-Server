package com.rs2;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.model.task.TickTask;

public final class BotLoginBatchTask
extends TickTask {
    private final int finalBatchIndex;

    public BotLoginBatchTask(int value3, int finalBatchIndex) {
        super(value3);
        this.finalBatchIndex = finalBatchIndex;
    }

    @Override
    public final void execute() {
        int botLoginBatchIndex = Server.getBotLoginBatchIndex();
        int value = 1 + Server.botLoginBatchSize * botLoginBatchIndex;
        botLoginBatchIndex = Server.botLoginBatchSize + Server.botLoginBatchSize * botLoginBatchIndex;
        if (Server.getConfiguredBotCount() < botLoginBatchIndex) {
            botLoginBatchIndex = Server.getConfiguredBotCount();
        }
        while (value <= botLoginBatchIndex) {
            if (value > 0 && value < ServerSettings.botLoginIdLimit) {
                BotPlayer.createBotFromPool(value, "zxcvbn", Server.selectNextBotType());
                if (value == Server.getConfiguredBotCount()) {
                    this.stop();
                    return;
                }
            }
            ++value;
        }
        if (this.finalBatchIndex == Server.getBotLoginBatchIndex()) {
            this.stop();
            return;
        }
        Server.setBotLoginBatchIndex(Server.getBotLoginBatchIndex() + 1);
    }
}

