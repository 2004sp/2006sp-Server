package com.rs2.model.player;

import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

public final class RetryMissingObjectSearchTask
extends TickTask {
    private final Player player;
    private final Player lifecyclePlayer;
    private final ArrayList targetIds;

    public RetryMissingObjectSearchTask(Player player, int value2, Player player2, ArrayList arrayList) {
        super(10);
        this.player = player;
        this.lifecyclePlayer = player2;
        this.targetIds = arrayList;
    }

    @Override
    public final void execute() {
        if (this.lifecyclePlayer.isDead() || !this.lifecyclePlayer.isRegistered()) {
            this.stop();
            return;
        }
        this.player.interactWithBotObjectTargets(this.targetIds);
        this.stop();
    }
}
