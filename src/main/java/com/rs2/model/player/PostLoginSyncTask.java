package com.rs2.model.player;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class PostLoginSyncTask
extends TickTask {
    private final Player player;
    private final Player areaOverlayPlayer;

    public PostLoginSyncTask(Player player, int value2, Player player2) {
        super(3);
        this.player = player;
        this.areaOverlayPlayer = player2;
    }

    @Override
    public final void execute() {
        this.stop();
        this.player.getAttributes().put("canPickup", Boolean.TRUE);
        this.player.getSocialManager().refreshFriendStatuses(false);
        GameplayHelper.refreshPlayerAreaOverlay(this.areaOverlayPlayer);
        Player player = this.player;
        player.packetSender.sendRunEnergy();
    }
}
