package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class ScriptedTeleportTask
extends CycleEvent {
    private int ticksRemaining;
    private TeleportManager teleportManager;
    private final int departureAnimationId;
    private final int departureGraphicId;
    private final int arrivalAnimationId;
    private final int destinationX;
    private final int destinationY;
    private final int destinationPlane;
    private final String arrivalMessage;

    public ScriptedTeleportTask(TeleportManager teleportManager, int ticksRemaining, int departureAnimationId, int departureGraphicId, int arrivalAnimationId, int destinationX, int destinationY, int destinationPlane, String arrivalMessage) {
        this.teleportManager = teleportManager;
        this.departureAnimationId = departureAnimationId;
        this.departureGraphicId = departureGraphicId;
        this.arrivalAnimationId = arrivalAnimationId;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationPlane = destinationPlane;
        this.arrivalMessage = arrivalMessage;
        this.ticksRemaining = ticksRemaining;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        --this.ticksRemaining;
        if (!TeleportManager.getPlayer(this.teleportManager).isDead()) {
            if (this.ticksRemaining == 4) {
                TeleportManager.getPlayer(this.teleportManager).getUpdateState().setAnimation(this.departureAnimationId);
                TeleportManager.getPlayer(this.teleportManager).getUpdateState().setGraphicHeight100(this.departureGraphicId);
            }
            if (this.ticksRemaining == 2) {
                TeleportManager.getPlayer(this.teleportManager).setActionLocked(false);
                TeleportManager.getPlayer(this.teleportManager).getUpdateState().setAnimation(this.arrivalAnimationId);
                TeleportManager.getPlayer(this.teleportManager).moveTo(new Position(this.destinationX, this.destinationY, this.destinationPlane));
                if (this.arrivalMessage != null) {
                    Player player = TeleportManager.getPlayer(this.teleportManager);
                    player.packetSender.sendGameMessage(this.arrivalMessage);
                }
            }
        } else {
            this.ticksRemaining = 0;
        }
        if (this.ticksRemaining <= 0) {
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        TeleportManager.getPlayer(this.teleportManager).setActionLocked(false);
        TeleportManager.getPlayer(this.teleportManager).getAttributes().put("canTakeDamage", Boolean.TRUE);
    }
}

