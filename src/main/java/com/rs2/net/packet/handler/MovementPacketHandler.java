package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.player.Player;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.GameplayTrace;
import com.rs2.util.GameUtil;
import com.rs2.util.path.PathFinder;
import com.rs2.util.plugin.PluginManager;

public final class MovementPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        int opcode = incomingPacket.getOpcode();
        boolean revision443Movement = ServerSettings.clientBuild == 443
                && (opcode == 99 || opcode == 80 || opcode == 81);
        int packetLength = incomingPacket.getLength();
        if (opcode == 248 || (revision443Movement && opcode == 80)) {
            packetLength -= 14;
        }
        if (player.isDead() || player.isActionLocked()) {
            return;
        }
        if (player.isMovementLocked()) {
            player.packetSender.sendGameMessage("A magical force stops you from moving.");
            return;
        }
        if (player.isStunned()) {
            player.packetSender.sendGameMessage("You are stunned.");
            return;
        }
        if (DuelRule.NO_MOVEMENT.isEnabledFor(player)) {
            player.packetSender.sendGameMessage("Movements have been disabled during this fight!");
            return;
        }
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("movement packet opcode=" + incomingPacket.getOpcode() + " rawLength=" + incomingPacket.getLength() + " adjustedLength=" + packetLength + " player=" + GameplayTrace.describe(player));
        }
        if (opcode != 98 && (!revision443Movement || opcode != 81)) {
            player.resetInteractionState();
            if (player.getQuestState(0) != 1) {
                player.packetSender.closeInterfaces();
            }
            if (player.interfaceAction == "duel") {
                if (player.getDuelSession().getOpponent() != null && !player.isInDuelArena()) {
                    player.getDuelSession().getOpponent().getDuelController().resetDuel(true);
                    player.getDuelSession().getOpponent().packetSender.sendGameMessage("Other played declined the duel.");
                }
                player.getDuelController().resetDuel(true);
            }
            PluginManager.handleMovementPacketPlugins();
        }
        if (player.getEquipmentManager().getContainer().containsItem(6583) || player.getEquipmentManager().getContainer().containsItem(7927)) {
            player.getEquipmentManager().unequipSlot(12);
        }
        player.getMovementQueue().clearMovementActions();
        if (player.getQuestState(0) == 0) {
            player.setQuestState(0, 2);
        }
        if (player.getQuestState(0) != 1) {
            player.getQuestManager().refreshQuestJournal();
        }
        if (player.getQuestState(0) == 1) {
            player.packetSender.setSidebarInterface(3, 3213);
        }
        int pathLength = (packetLength - 5) / 2;
        if (pathLength < 0) {
            return;
        }
        int[][] pathSteps = new int[pathLength][2];
        int baseX = incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        int index = 0;
        int baseY;
        boolean runPath;
        if (revision443Movement) {
            runPath = incomingPacket.getReader().readSignedByte(ByteTransform.NEGATE) == 1;
            baseY = incomingPacket.getReader().readSignedShort(ByteOrder.LITTLE);
            while (index < pathLength) {
                pathSteps[index][0] = incomingPacket.getReader().readSignedByte();
                pathSteps[index][1] = (byte) incomingPacket.getReader().readByte(false, ByteTransform.SUBTRACT);
                ++index;
            }
        } else {
            while (index < pathLength) {
                pathSteps[index][0] = incomingPacket.getReader().readSignedByte();
                pathSteps[index][1] = incomingPacket.getReader().readSignedByte();
                ++index;
            }
            baseY = incomingPacket.getReader().readSignedShort(ByteOrder.LITTLE);
            runPath = incomingPacket.getReader().readSignedByte(ByteTransform.NEGATE) == 1;
        }
        player.getMovementQueue().clear();
        player.getMovementQueue().setRunPath(runPath);
        if (GameplayTrace.enabled()) {
            int finalX = baseX;
            int finalY = baseY;
            if (pathLength > 0) {
                finalX = baseX + pathSteps[pathLength - 1][0];
                finalY = baseY + pathSteps[pathLength - 1][1];
            }
            GameplayTrace.log("movement decoded opcode=" + incomingPacket.getOpcode() + " base=" + baseX + "," + baseY + " final=" + finalX + "," + finalY + " pathLength=" + pathLength + " runPath=" + runPath + " player=" + GameplayTrace.describe(player));
        }
        int regionId = GameUtil.getRegionId(player.getPosition().getX(), player.getPosition().getY());
        if (player.movementSystemMode == 0 || regionId == 9886 || regionId == 10142) {
            player.getMovementQueue().addStep(new Position(baseX, baseY));
            index = 0;
            while (index < pathLength) {
                pathSteps[index][0] += baseX;
                pathSteps[index][1] += baseY;
                player.getMovementQueue().addStep(new Position(pathSteps[index][0], pathSteps[index][1]));
                ++index;
            }
            player.getMovementQueue().removeFirstStep();
            return;
        }
        index = 0;
        while (index < pathLength) {
            pathSteps[index][0] += baseX;
            pathSteps[index][1] += baseY;
            ++index;
        }
        if (pathLength > 0) {
            if (Math.abs(pathSteps[pathLength - 1][0] - player.getPosition().getX()) > 21 || Math.abs(pathSteps[pathLength - 1][1] - player.getPosition().getY()) > 21) {
                player.getMovementQueue().clear();
            }
        } else if (Math.abs(baseX - player.getPosition().getX()) > 21 || Math.abs(baseY - player.getPosition().getY()) > 21) {
            player.getMovementQueue().clear();
            return;
        }
        if (pathLength > 0) {
            PathFinder.getInstance();
            PathFinder.findPath(player, pathSteps[pathLength - 1][0], pathSteps[pathLength - 1][1], true, 1, 1);
            return;
        }
        PathFinder.getInstance();
        PathFinder.findPath(player, baseX, baseY, true, 1, 1);
    }
}
