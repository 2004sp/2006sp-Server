package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelSession;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class DuelCountdownTask
extends CycleEvent {
    private int countdownValue = 3;
    private DuelSession session;

    public DuelCountdownTask(DuelSession duelSession) {
        this.session = duelSession;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        DuelSession.getPlayer(this.session).getUpdateState().setForcedTextAndMarkUpdated("" + this.countdownValue);
        --this.countdownValue;
        if (this.countdownValue < 0) {
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        DuelSession.getPlayer(this.session).getUpdateState().setForcedTextAndMarkUpdated("FIGHT!");
        Player player = DuelSession.getPlayer(this.session);
        player.packetSender.sendMusicJingle(204, 300);
        this.session.setStarted(true);
    }
}

