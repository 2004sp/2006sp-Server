package com.rs2.model.interaction;

import com.rs2.ServerSettings;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.GatheringToolDefinition;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class MiningShortcutTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final int objectX;
    private final int objectY;
    private final int objectId;
    private final GatheringToolDefinition gatheringTool;

    public MiningShortcutTask(Player player, int actionSequence, int objectX, int objectY, int objectId, GatheringToolDefinition gatheringToolDefinition) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.objectX = objectX;
        this.objectY = objectY;
        this.objectId = objectId;
        this.gatheringTool = gatheringToolDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        ObjectManager.getInstance();
        Object dynamicObjectAt = ObjectManager.findDynamicObjectAt(this.objectX, this.objectY, this.player.getPosition().getPlane());
        if (dynamicObjectAt != null && ((DynamicObject)dynamicObjectAt).getWorldObject().getObjectId() != this.objectId) {
            cycleEventContainer.stop();
            return;
        }
        cycleEventContainer.setTickDelay(3);
        this.player.getUpdateState().setAnimation(this.gatheringTool.getGatherAnimationId());
        dynamicObjectAt = this.player;
        ((Player)dynamicObjectAt).packetSender.sendSoundEffect(432, 1, 0);
        if (this.player.temporaryActionValue == 1) {
            int objectOrientation = SkillActionHelper.getObjectOrientation(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane());
            int objectType = SkillActionHelper.getObjectType(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane());
            new DynamicObject(ServerSettings.placeholderObjectId, this.objectX, this.objectY, this.player.getPosition().getPlane(), objectOrientation, objectType == 11 ? 11 : 10, this.objectId, 1);
            Player player = this.player;
            player.packetSender.queueRelativeMovementStep(this.player.getPosition().getX() < 2840 ? 3 : -3, 0, true);
            cycleEventContainer.stop();
        }
        ++this.player.temporaryActionValue;
    }

    @Override
    public final void onStop() {
        this.player.getUpdateState().setAnimation(-1);
    }
}

