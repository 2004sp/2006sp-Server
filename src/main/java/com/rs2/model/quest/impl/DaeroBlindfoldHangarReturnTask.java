package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

public final class DaeroBlindfoldHangarReturnTask
extends TickTask {
    private final Player player;
    private final int questState;

    public DaeroBlindfoldHangarReturnTask(MonkeyMadnessQuest monkeyMadnessQuest, int value3, Player player, int questState) {
        super(5);
        this.player = player;
        this.questState = questState;
    }

    @Override
    public final void execute() {
        this.player.setActionLocked(false);
        if (this.questState >= 8) {
            this.player.moveTo(new Position(2649, 4516, 0));
        } else {
            this.player.moveTo(new Position(2585, 4516, 0));
        }
        Player player = this.player;
        player.packetSender.closeInterfaces();
        this.stop();
    }
}
