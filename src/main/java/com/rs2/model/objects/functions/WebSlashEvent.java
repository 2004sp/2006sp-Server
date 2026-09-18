/*
 * Source recovery overlay: make remapped event accessible to recovered callers.
 */
package com.rs2.model.objects.functions;

import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class WebSlashEvent
extends CycleEvent {
    private final Boolean successful;
    private final Player player;
    private final int objectX;
    private final int objectY;
    private final int orientation;

    public WebSlashEvent(Boolean successful, Player player, int objectX, int objectY, int orientation) {
        this.successful = successful;
        this.player = player;
        this.objectX = objectX;
        this.objectY = objectY;
        this.orientation = orientation;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.successful.booleanValue()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You fail to slash through the web.");
            player = this.player;
            player.packetSender.sendSoundEffect(237, 1, 0);
        } else {
            new DynamicObject(734, this.objectX, this.objectY, 0, this.orientation, 10, 733, 100);
            ObjectManager.getInstance();
            ObjectManager.removeObjectCollision(733, this.objectX, this.objectY, 0, 10, this.orientation);
            Player player = this.player;
            player.packetSender.sendGameMessage("You successfully slash open the web.");
            player = this.player;
            player.packetSender.sendSoundEffect(237, 1, 0);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}
