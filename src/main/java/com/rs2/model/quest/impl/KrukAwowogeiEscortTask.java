package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class KrukAwowogeiEscortTask
extends TickTask {
    private final Player player;
    private final int questId;

    public KrukAwowogeiEscortTask(MonkeyMadnessQuest monkeyMadnessQuest, int value3, Player player, int questId) {
        super(5);
        this.player = player;
        this.questId = questId;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        this.player.moveTo(new Position(2802, 2762, 0));
        Player player = this.player;
        player.packetSender.sendGameMessage("Kruk escorts you to Awowogei.");
        this.player.setQuestState(this.questId, 16);
        player = this.player;
        player.packetSender.closeInterfaces();
        this.stop();
    }
}
