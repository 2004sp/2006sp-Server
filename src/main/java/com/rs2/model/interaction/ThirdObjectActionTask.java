package com.rs2.model.interaction;

import com.rs2.model.Position;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.interaction.InteractionDispatcher;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class ThirdObjectActionTask
extends TickTask {
    private final Player player;
    private final int actionSequence;
    private final int objectId;
    private final int objectX;
    private final int objectY;
    private final int objectPlane;

    public ThirdObjectActionTask(int value7, boolean enabled2, Player player, int actionSequence, int objectId, int objectX, int objectY, int objectPlane) {
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
        if (this.player.isMoving() || this.player.isStunned()) {
            return;
        }
        Object worldObjectById = SkillActionHelper.findWorldObjectById(this.objectId, this.objectX, this.objectY, this.objectPlane);
        if (worldObjectById == null) {
            return;
        }
        Object interactionTargetId = ObjectDefinition.forId(this.player.getInteractionTargetId());
        Position position = GameUtil.findReachableInteractionPosition(((WorldObject)worldObjectById).getPosition().getX(), ((WorldObject)worldObjectById).getPosition().getY(), this.player.getPosition().getX(), this.player.getPosition().getY(), ((ObjectDefinition)interactionTargetId).getWidthForOrientation(((WorldObject)worldObjectById).getOrientation()), ((ObjectDefinition)interactionTargetId).getLengthForOrientation(((WorldObject)worldObjectById).getOrientation()), this.objectPlane);
        if (position == null) {
            return;
        }
        if (!InteractionDispatcher.canReachObjectInteraction(this.player, position, (WorldObject)worldObjectById)) {
            this.stop();
            return;
        }
        position = new Position(this.player.getInteractionTargetX(), this.player.getInteractionTargetY(), this.objectPlane);
        if (interactionTargetId != null) {
            this.player.getUpdateState().setFacePosition(position.centerForSize(((ObjectDefinition)interactionTargetId).getMaxDimension()));
        }
        switch (this.player.getInteractionTargetId()) {
            case 2213:
            case 5276:
            case 6084:
            case 10517:
            case 11338:
            case 11758:
            case 12121:
            case 12759:
            case 14367: {
                GrandExchangeManager.openGrandExchangeCollection(this.player);
                break;
            }
            case 3194: {
                ObjectManager.getInstance();
                interactionTargetId = ObjectManager.findDynamicObjectByIdAt(this.objectId, this.objectX, this.objectY, this.objectPlane);
                if (interactionTargetId == null) break;
                this.player.getUpdateState().setAnimation(832);
                ObjectManager.getInstance().removeDynamicObjectAt(this.objectX, this.objectY, this.objectPlane, ((WorldObject)worldObjectById).getType());
                break;
            }
            case 10177: {
                AttackStyleDefinition.startDelayedObjectMove(this.player, new Position(1798, 4407, 3));
                break;
            }
            case 1739: 
            case 4569: 
            case 12537: {
                AttackStyleDefinition.climbOffsetLadder(this.player, "down");
                break;
            }
            case 1748: 
            case 2884: 
            case 8745: 
            case 12965: {
                AttackStyleDefinition.climbOneFloorAtCurrentTile(this.player, "down");
                break;
            }
            case 4187: {
                AttackStyleDefinition.climbOneFloorAtCurrentTile(this.player, "up");
                break;
            }
            default: {
                worldObjectById = this.player;
                ((Player)worldObjectById).packetSender.sendGameMessage("Nothing interesting happens.");
            }
        }
        this.stop();
    }
}

