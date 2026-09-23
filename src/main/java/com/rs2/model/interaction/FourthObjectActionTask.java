package com.rs2.model.interaction;

import com.rs2.model.Position;
import com.rs2.model.interaction.InteractionDispatcher;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class FourthObjectActionTask
extends TickTask {
    private final Player player;
    private final int actionSequence;
    private final int objectId;
    private final int objectX;
    private final int objectY;
    private final int objectPlane;

    public FourthObjectActionTask(int value7, boolean enabled2, Player player, int actionSequence, int objectId, int objectX, int objectY, int objectPlane) {
        super(1, true);
        this.player = player;
        this.actionSequence = actionSequence;
        this.objectId = objectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.objectPlane = objectPlane;
    }

    @Override
    public final void execute() {
        if (this.player == null || !this.player.isCurrentActionSequence(this.actionSequence)) {
            this.stop();
            return;
        }
        if (this.player.isMoving()
                || this.player.hasMovedWithinTicks(1)
                || this.player.isStunned()) {
            return;
        }
        Object worldObjectById = SkillActionHelper.findWorldObjectById(this.objectId, this.objectX, this.objectY, this.objectPlane);
        if (worldObjectById == null) {
            return;
        }
        ObjectDefinition objectDefinition = ObjectDefinition.forId(this.player.getInteractionTargetId());
        if (!InteractionDispatcher.canReachObjectInteraction(
                this.player, (WorldObject)worldObjectById)) {
            this.stop();
            return;
        }
        worldObjectById = new Position(this.player.getInteractionTargetX(), this.player.getInteractionTargetY(), this.objectPlane);
        if (objectDefinition != null) {
            this.player.getUpdateState().setFacePosition(((Position)worldObjectById).centerForSize(objectDefinition.getMaxDimension()));
        }
        int value = this.objectY;
        int value2 = this.objectX;
        worldObjectById = this.player;
        if (((Player)worldObjectById).getAllotmentPatchManager().openSkillGuide(value2, value) ? true : (((Player)worldObjectById).getFlowerPatchManager().openSkillGuide(value2, value) ? true : (((Player)worldObjectById).getHerbPatchManager().openSkillGuide(value2, value) ? true : (((Player)worldObjectById).getHopsPatchManager().openSkillGuide(value2, value) ? true : (((Player)worldObjectById).getBushPatchManager().openSkillGuide(value2, value) ? true : (((Player)worldObjectById).getTreePatchManager().openSkillGuide(value2, value) ? true : (((Player)worldObjectById).getFruitTreePatchManager().openSkillGuide(value2, value) ? true : (((Player)worldObjectById).getSpecialTreePatchManager().openSkillGuide(value2, value) ? true : ((Player)worldObjectById).getSpecialCropPatchManager().openSkillGuide(value2, value))))))))) {
            this.stop();
            return;
        }
        this.player.getInteractionTargetId();
        worldObjectById = this.player;
        ((Player)worldObjectById).packetSender.sendGameMessage("Nothing interesting happens.");
        this.stop();
    }
}

