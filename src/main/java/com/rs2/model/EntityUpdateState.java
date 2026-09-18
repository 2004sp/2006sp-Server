package com.rs2.model;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.OrbChargeTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.model.update.EntityUpdateOverrideDefinition;
import com.rs2.model.update.EntityUpdateOverrideType;

public class EntityUpdateState {
    private boolean updateRequired;
    private boolean appearanceUpdateRequired;
    private boolean forcedTextUpdateRequired;
    private String forcedText;
    private boolean graphicUpdateRequired;
    private int graphicId;
    private int graphicDelay;
    private boolean animationUpdateRequired;
    private int animationId;
    private int animationDelay;
    private boolean faceEntityUpdateRequired;
    private int faceEntityId = -1;
    private boolean facePositionUpdateRequired;
    private Position facePosition;
    private boolean primaryHitUpdateRequired;
    private boolean secondaryHitUpdateRequired;
    private int queuedPrimaryHitDamage = -1;
    private int queuedPrimaryHitType;
    private int queuedSecondaryHitDamage = -1;
    private int queuedSecondaryHitType;
    private boolean forcedMovementUpdateRequired;
    private int primaryHitDamage;
    private int secondaryHitDamage;
    private int primaryHitType;
    private int secondaryHitType;
    private int forcedMovementStartDelay;
    private int forcedMovementEndDelay;
    private int forcedMovementDirection;
    private boolean primaryHitDamageOverridden;
    private boolean secondaryHitDamageOverridden;
    private Entity entity;
    private int forcedMovementEndXOffset;
    private int forcedMovementEndYOffset;

    public static boolean handleOrbChargeButton(Player player, int buttonId, int value2) {
        if (buttonId == 2799) {
            value2 = 1;
        }
        if (buttonId == 2798) {
            value2 = 5;
        }
        if (buttonId == 1747) {
            value2 = player.getInventoryManager().getItemAmount(567);
        }
        if (value2 <= 0) {
            return false;
        }
        if (value2 >= 28) {
            value2 = 28;
        }
        buttonId = value2 - 1;
        Player player2 = player;
        if (player2.interfaceAction == "orb charge") {
            player2 = player;
            player2.packetSender.closeInterfaces();
            player.activeMagicSpellAction.tryStartCast();
            player.setActiveCycleEvent(new OrbChargeTask(buttonId, player));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 6);
            return true;
        }
        return false;
    }

    public EntityUpdateState(Entity entity) {
        this.entity = entity;
    }

    public void setForcedMovement(Player player, int forcedMovementEndXOffset, int forcedMovementEndYOffset, int forcedMovementStartDelay, int forcedMovementEndDelay, int forcedMovementDirection) {
        player.getPosition();
        Position.updateLocalX(player);
        player.getPosition();
        Position.updateLocalY(player);
        player.getPosition();
        Position.updateLocalX(player);
        player.getPosition();
        Position.updateLocalY(player);
        this.forcedMovementEndXOffset = forcedMovementEndXOffset;
        this.forcedMovementEndYOffset = forcedMovementEndYOffset;
        this.forcedMovementStartDelay = forcedMovementStartDelay;
        this.forcedMovementEndDelay = forcedMovementEndDelay;
        this.forcedMovementDirection = forcedMovementDirection;
        this.forcedMovementUpdateRequired = true;
        this.updateRequired = true;
    }

    public void clearForcedMovement() {
        this.forcedMovementDirection = 0;
        this.forcedMovementEndYOffset = 0;
        this.forcedMovementEndXOffset = 0;
        this.forcedMovementEndDelay = 0;
        this.forcedMovementStartDelay = 0;
    }

    public void setGraphic(GraphicEffect graphicEffect) {
        this.setGraphic(graphicEffect.getId(), graphicEffect.getPackedDelay());
    }

    public void setGraphic(int graphicId) {
        this.graphicId = graphicId;
        this.graphicDelay = 0;
        this.graphicUpdateRequired = true;
        this.updateRequired = true;
    }

    public void setGraphic(int graphicId, int graphicDelay) {
        EntityUpdateOverrideDefinition entityUpdateOverrideDefinition;
        if (this.entity.isPlayer() && (entityUpdateOverrideDefinition = EntityUpdateOverrideDefinition.forOriginalIdAndType(graphicId, EntityUpdateOverrideType.b)) != null) {
            Player player = (Player)this.entity;
            player.packetSender.sendSoundEffect(entityUpdateOverrideDefinition.getReplacementId(), 1, 0);
        }
        this.graphicId = graphicId;
        this.graphicDelay = graphicDelay;
        this.graphicUpdateRequired = true;
        this.updateRequired = true;
    }

    public void setGraphicHeight100(int graphicId) {
        EntityUpdateOverrideDefinition entityUpdateOverrideDefinition;
        if (this.entity.isPlayer() && (entityUpdateOverrideDefinition = EntityUpdateOverrideDefinition.forOriginalIdAndType(graphicId, EntityUpdateOverrideType.b)) != null) {
            Player player = (Player)this.entity;
            player.packetSender.sendSoundEffect(entityUpdateOverrideDefinition.getReplacementId(), 1, 0);
        }
        this.graphicId = graphicId;
        this.graphicDelay = 0x640000;
        this.graphicUpdateRequired = true;
        this.updateRequired = true;
    }

    public void setAnimation(int animationId) {
        this.animationId = animationId;
        this.animationDelay = 0;
        this.animationUpdateRequired = true;
        this.updateRequired = true;
    }

    public void setAnimation(int animationId, int value2) {
        this.animationId = animationId;
        this.animationDelay = 0;
        this.animationUpdateRequired = true;
        this.updateRequired = true;
    }

    public void setFaceEntity(int faceEntity) {
        this.faceEntityId = faceEntity;
        this.faceEntityUpdateRequired = true;
        this.updateRequired = true;
    }

    public void setFacePosition(Position position) {
        this.facePosition = position;
        this.facePositionUpdateRequired = true;
        this.updateRequired = true;
    }

    public void setForcedTextAndMarkUpdated(String forcedTextAndMarkUpdated) {
        this.forcedText = forcedTextAndMarkUpdated;
        this.forcedTextUpdateRequired = true;
        this.updateRequired = true;
    }

    public void reset() {
        this.forcedTextUpdateRequired = false;
        this.appearanceUpdateRequired = false;
        this.graphicUpdateRequired = false;
        this.animationUpdateRequired = false;
        this.faceEntityUpdateRequired = false;
        this.facePositionUpdateRequired = false;
        this.primaryHitDamage = this.queuedPrimaryHitDamage;
        this.secondaryHitDamage = this.queuedSecondaryHitDamage;
        this.primaryHitType = this.queuedPrimaryHitType;
        this.secondaryHitType = this.queuedSecondaryHitType;
        this.queuedPrimaryHitDamage = -1;
        this.queuedSecondaryHitDamage = -1;
        this.primaryHitUpdateRequired = this.primaryHitDamage != -1;
        this.secondaryHitUpdateRequired = this.secondaryHitDamage != -1;
        this.primaryHitDamageOverridden = false;
        this.secondaryHitDamageOverridden = false;
        this.forcedMovementUpdateRequired = false;
    }

    public void setUpdateRequired(boolean updateRequired) {
        this.updateRequired = true;
    }

    public boolean isUpdateRequired() {
        return this.updateRequired;
    }

    public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
        this.appearanceUpdateRequired = appearanceUpdateRequired;
    }

    public boolean isAppearanceUpdateRequired() {
        return this.appearanceUpdateRequired;
    }

    public void setForcedTextUpdateRequired(boolean forcedTextUpdateRequired) {
        this.forcedTextUpdateRequired = true;
    }

    public boolean isForcedTextUpdateRequired() {
        return this.forcedTextUpdateRequired;
    }

    public void setForcedText(String forcedText) {
        this.forcedText = forcedText;
        this.setForcedTextUpdateRequired(true);
    }

    public String getForcedText() {
        return this.forcedText;
    }

    public boolean isGraphicUpdateRequired() {
        return this.graphicUpdateRequired;
    }

    public int getGraphicId() {
        return this.graphicId;
    }

    public int getGraphicDelay() {
        return this.graphicDelay;
    }

    public boolean isAnimationUpdateRequired() {
        return this.animationUpdateRequired;
    }

    public int getAnimationId() {
        return this.animationId;
    }

    public int getAnimationDelay() {
        return this.animationDelay;
    }

    public boolean isFaceEntityUpdateRequired() {
        return this.faceEntityUpdateRequired;
    }

    public void setFaceEntityId(int faceEntityId) {
        this.faceEntityId = faceEntityId;
    }

    public int getFaceEntityId() {
        return this.faceEntityId;
    }

    public boolean isFacePositionUpdateRequired() {
        return this.facePositionUpdateRequired;
    }

    public void setFacePositionValue(Position position) {
        this.facePosition = position;
    }

    public Position getFacePosition() {
        return this.facePosition;
    }

    public void setPrimaryHitUpdateRequired(boolean primaryHitUpdateRequired) {
        this.primaryHitUpdateRequired = true;
    }

    public boolean isPrimaryHitUpdateRequired() {
        return this.primaryHitUpdateRequired;
    }

    public void setSecondaryHitUpdateRequired(boolean secondaryHitUpdateRequired) {
        this.secondaryHitUpdateRequired = true;
    }

    public boolean isSecondaryHitUpdateRequired() {
        return this.secondaryHitUpdateRequired;
    }

    public void setPrimaryHitDamage(int primaryHitDamage) {
        this.primaryHitDamage = primaryHitDamage;
        this.primaryHitDamageOverridden = true;
    }

    public int getPrimaryHitDamage() {
        return this.primaryHitDamage;
    }

    public void setSecondaryHitDamage(int secondaryHitDamage) {
        this.secondaryHitDamage = secondaryHitDamage;
        this.secondaryHitDamageOverridden = true;
    }

    public int getSecondaryHitDamage() {
        return this.secondaryHitDamage;
    }

    public void setPrimaryHitType(int type) {
        this.primaryHitType = type;
    }

    public int getPrimaryHitType() {
        return this.primaryHitType;
    }

    public void setSecondaryHitType(int type) {
        this.secondaryHitType = type;
    }

    public int getSecondaryHitType() {
        return this.secondaryHitType;
    }

    public boolean isForcedMovementUpdateRequired() {
        return this.forcedMovementUpdateRequired;
    }

    public void setForcedMovementUpdateRequired(boolean forcedMovementUpdateRequired) {
        this.forcedMovementUpdateRequired = false;
    }

    public static int getLocalXForUpdate(Player player) {
        player.getPosition();
        return Position.updateLocalX(player);
    }

    public static int getLocalYForUpdate(Player player) {
        player.getPosition();
        return Position.updateLocalY(player);
    }

    public int getForcedMovementEndXOffset() {
        return this.forcedMovementEndXOffset;
    }

    public int getForcedMovementEndYOffset() {
        return this.forcedMovementEndYOffset;
    }

    public int getForcedMovementStartDelay() {
        return this.forcedMovementStartDelay;
    }

    public int getForcedMovementEndDelay() {
        return this.forcedMovementEndDelay;
    }

    public int getForcedMovementDirection() {
        return this.forcedMovementDirection;
    }

    public boolean isPrimaryHitDamageOverridden() {
        return this.primaryHitDamageOverridden;
    }

    public boolean isSecondaryHitDamageOverridden() {
        return this.secondaryHitDamageOverridden;
    }

    public void queueHit(int queuedPrimaryHitDamage, int queuedPrimaryHitType) {
        if (this.queuedPrimaryHitDamage == -1) {
            this.queuedPrimaryHitDamage = queuedPrimaryHitDamage;
            this.queuedPrimaryHitType = queuedPrimaryHitType;
            return;
        }
        if (this.queuedSecondaryHitDamage == -1) {
            this.queuedSecondaryHitDamage = queuedPrimaryHitDamage;
            this.queuedSecondaryHitType = queuedPrimaryHitType;
        }
    }
}
