package com.rs2.model.interaction;

import com.rs2.model.Position;
import com.rs2.model.interaction.InteractionDispatcher;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

public final class SpellOnObjectTask
extends TickTask {
    private final Player player;
    private final int actionSequence;
    private final int objectId;
    private final int objectX;
    private final int objectY;
    private final int objectPlane;
    private final SpellDefinition spell;

    public SpellOnObjectTask(int value7, boolean enabled2, Player player, int actionSequence, int objectId, int objectX, int objectY, int objectPlane, SpellDefinition spellDefinition) {
        super(1, true);
        this.player = player;
        this.actionSequence = actionSequence;
        this.objectId = objectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.objectPlane = objectPlane;
        this.spell = spellDefinition;
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
        if (MagicSpellAction.castObjectSpell(this.player, this.spell, this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane())) {
            this.stop();
            return;
        }
        this.stop();
    }
}

