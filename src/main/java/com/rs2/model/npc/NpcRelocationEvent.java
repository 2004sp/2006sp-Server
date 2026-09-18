package com.rs2.model.npc;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class NpcRelocationEvent
extends CycleEvent {
    private final Player player;
    private final int destinationX;
    private final int destinationY;
    private final int destinationPlane;
    private final boolean unregisterSourceNpc;
    private final Npc sourceNpc;

    public NpcRelocationEvent(Npc npc, Player player, int destinationX, int destinationY, int destinationPlane, boolean unregisterSourceNpc, Npc npc2) {
        this.player = player;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationPlane = destinationPlane;
        this.unregisterSourceNpc = unregisterSourceNpc;
        this.sourceNpc = npc2;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.moveTo(new Position(this.destinationX, this.destinationY, this.destinationPlane));
        Player player = this.player;
        player.packetSender.closeInterfaces();
        this.player.getUpdateState().setAnimation(65535, 0);
        if (this.unregisterSourceNpc) {
            Player player2 = this.player;
            player = player2;
            player = this.player;
            player2.packetSender.sendStillGraphic(86, player.ownedNpc.getPosition(), 0x640000);
            GameplayHelper.unregisterTemporaryNpc(this.sourceNpc);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

