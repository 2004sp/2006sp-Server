package com.rs2.model.skill.agility;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class AgilityPositionOffsetTask
extends CycleEvent {
    private final Player player;
    private final int deltaX;
    private final int deltaY;
    private final int deltaPlane;
    private final double experience;
    private final String completionMessage;

    public AgilityPositionOffsetTask(Player player, int deltaX, int deltaY, int deltaPlane, double experience, String completionMessage) {
        this.player = player;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaPlane = deltaPlane;
        this.experience = experience;
        this.completionMessage = completionMessage;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.moveTo(new Position(this.player.getPosition().getX() + this.deltaX, this.player.getPosition().getY() + this.deltaY, this.player.getPosition().getPlane() + this.deltaPlane));
        if (this.deltaPlane == 0) {
            this.player.getUpdateState().setFacePosition(new Position(this.player.getPosition().getX() - this.deltaX, this.player.getPosition().getY() - this.deltaY, this.player.getPosition().getPlane() - this.deltaPlane));
        }
        this.player.getSkillManager().addExperience(16, this.experience);
        Player player = this.player;
        player.packetSender.sendGameMessage(this.completionMessage);
        this.player.setActionLocked(false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

