/*
 * Source recovery overlay for CFR control-flow damage.
 */
package com.rs2.model.combat;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.objects.functions.DelayedObjectMoveEvent;
import com.rs2.model.objects.functions.ObjectToggleEvent;
import com.rs2.model.objects.functions.WebSlashEvent;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;
import java.util.ArrayList;

public class AttackStyleDefinition {
    private AttackXpMode xpMode;
    private AttackBonusType attackBonusType;
    private int buttonId;
    private CombatType combatType;

    public static void startDelayedObjectMove(Player player, Position position) {
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("object travel delayed-move scheduled player=" + GameplayTrace.describe(player) + " destination=" + formatPosition(position) + " targetObjectId=" + player.getInteractionTargetId() + " targetX=" + player.getInteractionTargetX() + " targetY=" + player.getInteractionTargetY());
        }
        player.setActionLocked(true);
        player.getUpdateState().setAnimation(828);
        player.packetSender.closeInterfaces();
        CycleEventHandler.getInstance().schedule(player, new DelayedObjectMoveEvent(player, position), 2);
    }

    public static void climbOneFloorAtCurrentTile(Player player, String text2) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int plane = player.getPosition().getPlane();
        int targetPlane = text2.equalsIgnoreCase("up") ? plane + 1 : (plane - 1 <= 0 ? 0 : plane - 1);
        AttackStyleDefinition.startDelayedObjectMove(player, new Position(player.getPosition().getX(), player.getPosition().getY(), targetPlane));
    }

    public static void climbTwoFloorsAtCurrentTile(Player player, String text2, int value2) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int plane = player.getPosition().getPlane();
        int targetPlane = text2.equalsIgnoreCase("up") ? plane + 2 : (plane - 2 < 2 ? 0 : plane - 2);
        AttackStyleDefinition.startDelayedObjectMove(player, new Position(player.getPosition().getX(), player.getPosition().getY(), targetPlane));
    }

    public static void climbDirectionalStairs(Player player, int xOffset, int yOffset, String direction) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int orientation = worldObject.getOrientation();
        int plane = player.getPosition().getPlane();
        int targetPlane = direction.equalsIgnoreCase("up") ? plane + 1 : (plane - 1 <= 0 ? 0 : plane - 1);
        int xDelta = orientation == 1 ? (direction.equalsIgnoreCase("up") ? xOffset : -xOffset) : (orientation == 3 ? (direction.equalsIgnoreCase("up") ? -xOffset : xOffset) : 0);
        int yDelta = orientation == 0 ? (direction.equalsIgnoreCase("up") ? yOffset : -yOffset) : (orientation == 2 ? (direction.equalsIgnoreCase("up") ? -yOffset : yOffset) : 0);
        Position destination = new Position(player.getPosition().getX() + xDelta, player.getPosition().getY() + yDelta, targetPlane);
        traceObjectTravel(player, "directional-stairs", direction, worldObject, destination);
        player.moveTo(destination);
    }

    public static void climbFourTileDirectionalStairs(Player player, int direction, int value22, String direction2) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int orientation = worldObject.getOrientation();
        int plane = player.getPosition().getPlane();
        int targetPlane = direction2.equalsIgnoreCase("up") ? plane + 1 : (plane - 1 <= 0 ? 0 : plane - 1);
        int xDelta = orientation == 1 ? (direction2.equalsIgnoreCase("up") ? -4 : 4) : (orientation == 3 ? (direction2.equalsIgnoreCase("up") ? 4 : -4) : 0);
        int yDelta = orientation == 0 ? (direction2.equalsIgnoreCase("up") ? -4 : 4) : (orientation == 2 ? (direction2.equalsIgnoreCase("up") ? 4 : -4) : 0);
        Position destination = new Position(player.getPosition().getX() + xDelta, player.getPosition().getY() + yDelta, targetPlane);
        traceObjectTravel(player, "four-tile-stairs", direction2, worldObject, destination);
        player.moveTo(destination);
    }

    public static void climbOffsetLadder(Player player, String direction) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int orientation = worldObject.getOrientation();
        int plane = player.getPosition().getPlane();
        int targetPlane = direction.equalsIgnoreCase("up") ? plane + 1 : (plane - 1 <= 0 ? 0 : plane - 1);
        int directionSign = direction.equalsIgnoreCase("up") ? 1 : -1;
        int xDelta = (orientation == 0 ? 1 : (orientation == 1 ? 1 : -1)) * directionSign;
        int yDelta = (orientation == 0 ? 1 : (orientation == 1 ? -1 : (orientation == 2 ? -1 : 1))) * directionSign;
        if (worldObject.getObjectId() == 1738 || worldObject.getObjectId() == 1739 || worldObject.getObjectId() == 1740) {
            xDelta = 0;
            yDelta = 0;
        }
        if (player.getInteractionTargetId() == 1738 && direction.equals("up") && player.getInteractionTargetX() == 2673 && player.getInteractionTargetY() == 3300) {
            Position destination = new Position(2675, 3300, targetPlane);
            traceObjectTravel(player, "offset-ladder", direction, worldObject, destination);
            player.moveTo(destination);
            return;
        }
        Position destination = new Position(player.getPosition().getX() + xDelta, player.getPosition().getY() + yDelta, targetPlane);
        traceObjectTravel(player, "offset-ladder", direction, worldObject, destination);
        player.moveTo(destination);
    }

    public static void climbDungeonLadder(Player player, int value3, int value22, String direction) {
        WorldObject worldObject = SkillActionHelper.findWorldObjectById(player.getInteractionTargetId(), player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
        if (worldObject == null) {
            return;
        }
        int orientation = worldObject.getOrientation();
        int xDelta = orientation == 1 ? (direction.equalsIgnoreCase("up") ? 4 : -4) : (orientation == 3 ? (direction.equalsIgnoreCase("up") ? -4 : 4) : 0);
        int yDelta = orientation == 0 ? (direction.equalsIgnoreCase("up") ? 4 : -4) : (orientation == 2 ? (direction.equalsIgnoreCase("up") ? -4 : 4) : 0);
        Position destination = new Position(player.getPosition().getX() + xDelta, player.getPosition().getY() + yDelta + (direction.equalsIgnoreCase("up") ? -6400 : 6400), 0);
        traceObjectTravel(player, "dungeon-ladder", direction, worldObject, destination);
        player.moveTo(destination);
    }

    private static void traceObjectTravel(Player player, String kind, String direction, WorldObject worldObject, Position destination) {
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("object travel " + kind + " player=" + GameplayTrace.describe(player) + " direction=" + direction + " objectId=" + worldObject.getObjectId() + " objectPosition=" + formatPosition(worldObject.getPosition()) + " orientation=" + worldObject.getOrientation() + " destination=" + formatPosition(destination));
        }
    }

    private static String formatPosition(Position position) {
        return position.getX() + "," + position.getY() + "," + position.getPlane();
    }

    public AttackStyleDefinition(CombatType combatType, AttackXpMode attackXpMode, AttackBonusType attackBonusType) {
        this(combatType, -1, attackXpMode, attackBonusType);
    }

    public AttackStyleDefinition(CombatType combatType, int buttonId, AttackXpMode attackXpMode, AttackBonusType attackBonusType) {
        this.combatType = combatType;
        this.buttonId = buttonId;
        this.xpMode = attackXpMode;
        this.attackBonusType = attackBonusType;
    }

    public CombatType getCombatType() {
        return this.combatType;
    }

    public int getButtonId() {
        return this.buttonId;
    }

    public AttackXpMode getXpMode() {
        return this.xpMode;
    }

    public AttackBonusType getAttackBonusType() {
        return this.attackBonusType;
    }

    public int getAttackBonusIndex() {
        return this.getAttackBonusType().getIndex();
    }

    public int getDefenseBonusIndex() {
        return this.getAttackBonusType().getIndex() + AttackBonusType.values().length;
    }

    public static void toggleObjectAfterAnimation(Player player, int objectId, int value2, WorldObject worldObject) {
        int orientation = worldObject.getOrientation();
        int type = worldObject.getType();
        player.setActionLocked(true);
        player.getUpdateState().setAnimation(827);
        CycleEventHandler.getInstance().schedule(player, new ObjectToggleEvent(objectId, worldObject, type, value2, orientation, player), 2);
    }

    public static void slashWeb(Player player, int value4, int value22, int value32) {
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectByIdAt(733, value4, value22, 0);
        if (dynamicObject != null && dynamicObject.getWorldObject().getObjectId() != 733) {
            return;
        }
        ItemStack itemStack = new ItemStack(value32);
        String itemName = itemStack.getDefinition().getName().toLowerCase();
        int slashBonus = itemStack.getDefinition().getBonus(1);
        if (!itemName.contains("knife") && slashBonus <= 0) {
            player.packetSender.sendGameMessage("You need a sharp weapon to slash through this.");
            if (player.botEnabled && player.currentBotTask != null) {
                player.botTaskReturnToBankRequested = true;
                player.moveTo(player.currentBotTask.getStartPosition());
                player.botTaskState = "empty inventory";
                player.botInteractionOption = 2;
                ArrayList botTargets = new ArrayList();
                if (player.currentBotTask.usesDepositBox) {
                    botTargets.add(Integer.valueOf(2619));
                    player.interactWithBotNpcTargets(botTargets);
                    return;
                }
                botTargets.add(Integer.valueOf(2213));
                botTargets.add(Integer.valueOf(11758));
                player.interactWithBotObjectTargets(botTargets);
            }
            return;
        }
        player.nextActionSequence();
        player.setActionLocked(true);
        player.getUpdateState().setAnimation(451);
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(733, value4, value22, 0);
        int orientation = loadedWorldObject.getOrientation();
        double chance = 1.0;
        if (value32 == 946) {
            chance = 0.5;
        } else if (slashBonus >= 100) {
            chance = 1.0;
        } else if (slashBonus < 20) {
            chance = 0.2;
        } else if (slashBonus >= 20) {
            WeaponProfile weaponProfile = WeaponProfile.forItem(itemStack);
            double baseChance = weaponProfile == WeaponProfile.LONGSWORD || weaponProfile == WeaponProfile.SCIMITAR ? 0.5 : 0.2;
            double remainingChance = 1.0 - baseChance;
            double bonusOverBaseline = slashBonus - 20;
            double scaledChance = remainingChance / 80.0 * bonusOverBaseline;
            chance = baseChance + scaledChance;
        }
        Boolean successful = Boolean.valueOf(GameUtil.rollChance(chance));
        CycleEventHandler.getInstance().schedule(player, new WebSlashEvent(successful, player, value4, value22, orientation), 2);
    }
}
