package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.VampireSlayerQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class DraynorManorCandlesBurnTask
extends TickTask {
    private final VampireSlayerQuest quest;
    private final Player player;

    public DraynorManorCandlesBurnTask(VampireSlayerQuest vampireSlayerQuest, int value2, Player player) {
        super(5);
        this.quest = vampireSlayerQuest;
        this.player = player;
    }

    @Override
    public final void execute() {
        if (!this.player.isRegistered()) {
            this.stop();
            return;
        }
        if (this.quest.candleHazardArea.containsExclusive(this.player.getPosition())) {
            Player player = this.player;
            player.packetSender.sendGameMessage("The candles burn your feet!");
            this.player.getUpdateState().setForcedText(this.quest.candleBurnReactions[GameUtil.randomInclusive(this.quest.candleBurnReactions.length - 1)]);
            return;
        }
        this.player.activeRecurringEffectId = -1;
        this.stop();
    }
}
