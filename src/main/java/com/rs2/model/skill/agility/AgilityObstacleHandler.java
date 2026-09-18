package com.rs2.model.skill.agility;

import com.rs2.ServerSettings;
import com.rs2.model.objects.ObjectRegionKey;
import com.rs2.model.player.Player;
import com.rs2.model.skill.agility.AgilityMovementFinishTask;
import com.rs2.model.skill.agility.AgilityMovementStepTask;
import com.rs2.model.skill.agility.AgilityObstacleCompletionTask;
import com.rs2.model.skill.agility.AgilityPositionOffsetTask;
import com.rs2.model.skill.agility.AgilityQueuedMovementTask;
import com.rs2.model.skill.agility.AgilityShortcutStartTask;
import com.rs2.model.task.CycleEventHandler;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class AgilityObstacleHandler {
    private List loadedObjects = new LinkedList();

    public static void startDelayedShortcutMovement(Player player, int delayTicks, int value22, int value32, int value42, int value52, int value62) {
        if (!ServerSettings.agilityEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        player.getUpdateState().setAnimation(751);
        CycleEventHandler.getInstance().schedule(player, new AgilityShortcutStartTask(player, value22, 0, 30, 2, 50), 2);
    }
    public static void startForcedMovement(Player player, int value9, int value22, int value32, int value42, int value52, boolean enabled2, int value62, int value72) {
        int value8 = value72;
        player.setActionLocked(true);
        player.getMovementQueue().clear();
        if (value8 > 0) {
            player.setRunAnimationOverride(value8);
            player.setWalkAnimationOverride(value8);
            player.setAppearanceUpdateRequired(true);
        }
        value32 = 2;
        if (value9 > 0) {
            value32 = 1;
        } else if (value9 < 0) {
            value32 = 3;
        } else if (value22 > 0) {
            value32 = 0;
        }
        int position = player.getPosition().getX() + value9;
        value8 = player.getPosition().getY() + value22;
        int position2 = player.getPosition().getPlane();
        player.forcedMovementActive = true;
        CycleEventHandler.getInstance().schedule(player, new AgilityMovementStepTask(player, value9, value22, 1, value42, value32), 1);
        CycleEventHandler.getInstance().schedule(player, new AgilityMovementFinishTask(player, value62, true, position, value8, position2), value52 + 1);
    }

    public static void startQueuedObstacleMovement(Player player, int value8, int value22, int value32, int value42, int value52, int value62, int value72) {
        if (!ServerSettings.agilityEnabled) {
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        CycleEventHandler.getInstance().schedule(player, new AgilityQueuedMovementTask(player, 0, value32, 60, 1, value8, 751), 0);
    }

    public static void startAgilityMovement(Player player, double value7, int value8, int value23, int value32, int value42, int value52, int value62, String text3, String text22) {
        if (!ServerSettings.agilityEnabled) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        if (value32 > 0) {
            player.getUpdateState().setAnimation(value32);
        }
        player.setActionLocked(true);
        Player player3 = player;
        player3.packetSender.sendGameMessage(text3);
        CycleEventHandler.getInstance().schedule(player, new AgilityObstacleCompletionTask(player, value8, value23, value42, value52, value62, value7, text22), value32 > 0 ? 2 : 0);
    }

    public static void startPositionOffsetObstacle(Player player, double value6, int value7, int value23, int value32, int value42, int value52, String text3, String text22) {
        if (!ServerSettings.agilityEnabled) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        player.getUpdateState().setAnimation(828);
        player.setActionLocked(true);
        Player player3 = player;
        player3.packetSender.sendGameMessage(text3);
        CycleEventHandler.getInstance().schedule(player, new AgilityPositionOffsetTask(player, value7, value23, value32, value6, text22), value52);
    }

    public AgilityObstacleHandler(ObjectRegionKey objectRegionKey) {
    }

    public Collection getLoadedObjects() {
        return this.loadedObjects;
    }
}

