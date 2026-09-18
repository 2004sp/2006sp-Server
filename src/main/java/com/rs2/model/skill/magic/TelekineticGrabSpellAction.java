package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.path.PathResult;
import com.rs2.model.path.PathStep;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.util.GameUtil;

public final class TelekineticGrabSpellAction
extends MagicSpellAction {
    private final GroundItem groundItem;
    private final SpellDefinition telegrabSpell;
    private final Player caster;
    private final Position targetPosition;
    private final int itemId;

    public TelekineticGrabSpellAction(Player player, SpellDefinition spellDefinition, GroundItem groundItem, SpellDefinition spellDefinition2, Player player2, Position position, int itemId) {
        super(player, spellDefinition, (byte)0);
        this.groundItem = groundItem;
        this.telegrabSpell = spellDefinition2;
        this.caster = player2;
        this.targetPosition = position;
        this.itemId = itemId;
    }

    @Override
    public final boolean prepareCast() {
        if (this.groundItem == null) {
            return false;
        }
        switch (this.telegrabSpell) {
            case TELEKINETIC_GRAB: {
                this.caster.getUpdateState().setFacePosition(this.targetPosition);
                this.caster.getMovementQueue().clear();
                this.scheduleDelayedImpact(null, this.targetPosition);
            }
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void applyImpact(HitDefinition hitDefinition) {
        switch (this.telegrabSpell) {
            case TELEKINETIC_GRAB: {
                Position position;
                GroundItemManager.getInstance();
                if (!GroundItemManager.isVisible(this.caster, this.groundItem)) {
                    hitDefinition.setGraphic(null);
                    return;
                }
                if (!GroundItemManager.getInstance().removeForPickup(this.groundItem, this.caster)) {
                    this.caster.packetSender.sendGameMessage("That item does not seem to exist anymore.");
                    this.caster.packetSender.sendGroundItemRemove(this.groundItem);
                    if (!this.caster.botEnabled) return;
                    TelekineticGrabSpellAction.continueBotGroundItemLoot(this.caster, this.groundItem, false);
                    return;
                }
                if (!this.caster.getTelekineticTheatreController().isInsideTheatre()) {
                    this.caster.getInventoryManager().addItem(new ItemStack(this.groundItem.getItem().getId(), this.groundItem.getItem().getAmount(), this.groundItem.getItem().getMetadata()));
                    if (!this.caster.botEnabled) return;
                    TelekineticGrabSpellAction.continueBotGroundItemLoot(this.caster, this.groundItem, true);
                    return;
                }
                if (this.itemId != 6888) return;
                String mazeSide = this.caster.getTelekineticTheatreController().getPlayerMazeSide(this.caster.getTelekineticTheatreController().mazeIndex);
                int index = 0;
                int index2 = 0;
                if (mazeSide == "right") {
                    index += 20;
                } else if (mazeSide == "left") {
                    index -= 20;
                } else if (mazeSide == "bottom") {
                    index2 -= 20;
                } else if (mazeSide == "upper") {
                    index2 += 20;
                }
                boolean enabled = true;
                Position position2 = new Position(this.targetPosition.getX() + index, this.targetPosition.getY() + index2, this.targetPosition.getPlane());
                Object value = this.targetPosition;
                PathResult pathResult = new PathResult();
                Position position3 = ((Position)value).copy();
                Position position4 = position3.copy();
                int x = position2.getX() - position3.getX();
                int y = position2.getY() - position3.getY();
                int index3 = 0;
                int index4 = 0;
                int index5 = 0;
                int index6 = 0;
                if (x < 0) {
                    index3 = -1;
                } else if (x > 0) {
                    index3 = 1;
                }
                if (y < 0) {
                    index4 = -1;
                } else if (y > 0) {
                    index4 = 1;
                }
                if (x < 0) {
                    index5 = -1;
                } else if (x > 0) {
                    index5 = 1;
                }
                int value2 = Math.abs(x);
                int value3 = Math.abs(y);
                if (value2 <= value3) {
                    value2 = Math.abs(y);
                    value3 = Math.abs(x);
                    if (y < 0) {
                        index6 = -1;
                    } else if (y > 0) {
                        index6 = 1;
                    }
                    index5 = 0;
                }
                y = value2 >> 1;
                x = 0;
                while (true) {
                    if (x > value2) {
                        x = ((PathStep)pathResult.getSteps().getLast()).getX();
                        y = ((PathStep)pathResult.getSteps().getLast()).getY();
                        position = new Position(x, y, ((Position)value).getPlane());
                        break;
                    }
                    y += value3;
                    if (!position4.equals(position3) && !GameUtil.hasClearPath(position3, position4, true)) {
                        y = ((PathStep)pathResult.getSteps().getLast()).getX();
                        int steps = ((PathStep)pathResult.getSteps().getLast()).getY();
                        position = new Position(y, steps, ((Position)value).getPlane());
                        break;
                    }
                    position4 = position3.copy();
                    pathResult.getSteps().add(new PathStep(position3.getX(), position3.getY()));
                    if (y >= value2) {
                        y -= value2;
                        position3.setX(position3.getX() + index3);
                        position3.setY(position3.getY() + index4);
                    } else {
                        position3.setX(position3.getX() + index5);
                        position3.setY(position3.getY() + index6);
                    }
                    ++x;
                }
                value = position;
                if (!this.caster.getTelekineticTheatreController().isMazeTargetPosition((Position)value)) {
                    value = new GroundItem(new ItemStack(6888, 1), this.caster, (Position)value);
                    GroundItemManager.getInstance().spawn((GroundItem)value);
                    return;
                }
                this.caster.getTelekineticTheatreController().completeMaze();
                return;
            }
        }
    }
}

