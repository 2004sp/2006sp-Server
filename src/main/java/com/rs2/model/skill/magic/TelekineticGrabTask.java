package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class TelekineticGrabTask
extends CycleEvent {
    private final Player caster;
    private final int actionSequence;
    private final int itemId;
    private final Position targetPosition;
    private final SpellDefinition telegrabSpell;

    public TelekineticGrabTask(Player player, int actionSequence, int itemId, Position position, SpellDefinition spellDefinition) {
        this.caster = player;
        this.actionSequence = actionSequence;
        this.itemId = itemId;
        this.targetPosition = position;
        this.telegrabSpell = spellDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.caster.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        GroundItemManager.getInstance();
        Object visibleItem = GroundItemManager.findVisibleItem(this.caster, this.itemId, this.targetPosition);
        if (visibleItem == null) {
            return;
        }
        if (this.caster.gameMode != 0) {
            if (!((GroundItem)visibleItem).isRespawning() && ((GroundItem)visibleItem).getOwner() == null) {
                visibleItem = this.caster;
                ((Player)visibleItem).packetSender.sendGameMessage("You are not playing on normal gamemode and cant pick that up.");
                cycleEventContainer.stop();
                return;
            }
            if (!((GroundItem)visibleItem).isRespawning() && ((GroundItem)visibleItem).getOwner().resolve() != this.caster) {
                visibleItem = this.caster;
                ((Player)visibleItem).packetSender.sendGameMessage("You are not playing on normal gamemode and cant pick that up.");
                cycleEventContainer.stop();
                return;
            }
        }
        switch (this.telegrabSpell) {
            case TELEKINETIC_GRAB: {
                if (this.itemId == 1583) {
                    visibleItem = this.caster;
                    ((Player)visibleItem).packetSender.sendGameMessage("I can't use Telekinetic Grab on this object.");
                    cycleEventContainer.stop();
                    return;
                }
                if (this.itemId == 1419) {
                    if (this.caster.ownsItem(this.itemId)) {
                        visibleItem = this.caster;
                        ((Player)visibleItem).packetSender.sendGameMessage("You already have a scythe, you don't need another one.");
                        cycleEventContainer.stop();
                        return;
                    }
                    this.caster.questHookStates[3] = 1;
                }
                if (this.itemId >= 5509 && this.itemId <= 5515 && this.caster.ownsItem(this.itemId)) {
                    visibleItem = this.caster;
                    ((Player)visibleItem).packetSender.sendGameMessage("I already have that pouch!");
                    cycleEventContainer.stop();
                    return;
                }
                if (!(this.caster.getTelekineticTheatreController().isInsideTheatre() || GameUtil.hasClearPath(this.caster.getPosition(), this.targetPosition, false) && GameUtil.isWithinDistance(this.caster.getPosition(), this.targetPosition, 10))) {
                    return;
                }
                if (!this.caster.getPosition().equals(this.targetPosition)) break;
                this.caster.getTargetMovement().moveAwayFromOverlap();
                return;
            }
            default: {
                return;
            }
        }
        MagicSpellAction.castTelekineticGrab(this.caster, this.telegrabSpell, this.itemId, this.targetPosition);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

