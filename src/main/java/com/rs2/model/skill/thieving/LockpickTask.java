package com.rs2.model.skill.thieving;

import com.rs2.model.Position;
import com.rs2.model.objects.functions.DoorHandler;
import com.rs2.model.player.Player;
import com.rs2.model.skill.thieving.ThievingObjectHandler;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class LockpickTask
extends CycleEvent {
    private final Player player;
    private final double experience;
    private final int objectId;
    private final Position objectPosition;
    private final int moveDeltaX;
    private final int moveDeltaY;

    public LockpickTask(Player player, double experience, int objectId, Position position, int moveDeltaX, int moveDeltaY) {
        this.player = player;
        this.experience = experience;
        this.objectId = objectId;
        this.objectPosition = position;
        this.moveDeltaX = moveDeltaX;
        this.moveDeltaY = moveDeltaY;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (ThievingObjectHandler.getRandom().nextInt(30) < 5) {
            Player player = this.player;
            player.packetSender.sendGameMessage("But fail to pick it.");
            this.player.setActionLocked(false);
            cycleEventContainer.stop();
            return;
        }
        Player player = this.player;
        player.packetSender.sendGameMessage("And manage to pass through it.");
        player = this.player;
        player.packetSender.sendSoundEffect(1502, 1, 0);
        this.player.getSkillManager().addExperience(17, this.experience);
        DoorHandler.handleDoorMovement(this.player, this.objectId, this.objectPosition.getX(), this.objectPosition.getY(), this.player.getPosition().getPlane(), this.moveDeltaX, this.moveDeltaY);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
        this.player.resetAnimation();
    }
}

