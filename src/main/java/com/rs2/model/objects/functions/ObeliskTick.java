package com.rs2.model.objects.functions;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import java.awt.Point;

public class ObeliskTick
extends TickTask {
    private static final int activationDelayTicks = (int)GameUtil.secondsToTicks(5L);
    private WildernessObelisk obelisk;

    private ObeliskTick(WildernessObelisk wildernessObelisk) {
        super(activationDelayTicks, true);
        this.obelisk = wildernessObelisk;
    }

    @Override
    public final void execute() {
        if (!this.obelisk.active) {
            this.obelisk.active = true;
            Position[] cornerPositions = this.obelisk.cornerPositions;
            int length = cornerPositions.length;
            int index = 0;
            while (index < length) {
                Position position = cornerPositions[index];
                new DynamicObject(14825, position.getX(), position.getY(), position.getPlane(), 0, 10, this.obelisk.objectId, activationDelayTicks + 3);
                ++index;
            }
            return;
        }
        this.stop();
        this.obelisk.active = false;
        int randomIndex;
        do {
            randomIndex = GameUtil.getRandom().nextInt(WildernessObelisk.values().length);
        } while (WildernessObelisk.values()[randomIndex] == this.obelisk);
        WildernessObelisk destinationObelisk = WildernessObelisk.values()[randomIndex];
        Player[] players = World.getPlayers();
        int playerCount = players.length;
        int playerIndex = 0;
        while (playerIndex < playerCount) {
            Player player = players[playerIndex];
            if (player != null && this.obelisk.teleportBounds.contains(new Point(player.getPosition().getX(), player.getPosition().getY()))) {
                int offsetX = player.getPosition().getX() - this.obelisk.basePosition.getX();
                int offsetY = player.getPosition().getY() - this.obelisk.basePosition.getY();
                player.getTeleportManager().startDelayedTeleport(destinationObelisk.basePosition.getX() + offsetX, destinationObelisk.basePosition.getY() + offsetY, destinationObelisk.basePosition.getPlane(), false, "Ancient magic teleports you somewhere in the wilderness");
            }
            ++playerIndex;
        }
        Position[] effectPositions = this.obelisk.effectArea.getPositions();
        int effectCount = effectPositions.length;
        int effectIndex = 0;
        while (effectIndex < effectCount) {
            Position position = effectPositions[effectIndex];
            World.sendStillGraphicToNearbyPlayers(GraphicEffect.createHeight0(342), position);
            ++effectIndex;
        }
    }

    private static Position[] buildCornerPositions(WildernessObelisk wildernessObelisk) {
        Position basePosition = wildernessObelisk.basePosition;
        int index = 0;
        Position[] positions = new Position[4];
        int offsetX = 0;
        while (offsetX <= 4) {
            int offsetY = 0;
            while (offsetY <= 4) {
                positions[index++] = new Position(basePosition.getX() + offsetX, basePosition.getY() + offsetY, basePosition.getPlane());
                offsetY += 4;
            }
            offsetX += 4;
        }
        return positions;
    }

    public static void main(String[] stringValues2) {
        ObeliskTick.buildCornerPositions(WildernessObelisk.LEVEL_13);
    }

    public static boolean activateForPlayer(Player player, int objectId) {
        WildernessObelisk[] obelisks = WildernessObelisk.values();
        int length = obelisks.length;
        int index = 0;
        while (index < length) {
            WildernessObelisk wildernessObelisk = obelisks[index];
            if (wildernessObelisk.objectId == objectId) {
                if (player.isMember()) {
                    if (ServerSettings.freeToPlayWorld) {
                        player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    } else {
                        if (wildernessObelisk.active) {
                            return true;
                        }
                        World.scheduleTickTask(new ObeliskTick(wildernessObelisk));
                    }
                } else {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                }
                return true;
            }
            ++index;
        }
        return false;
    }

    static Position[] getCornerPositions(WildernessObelisk wildernessObelisk) {
        return ObeliskTick.buildCornerPositions(wildernessObelisk);
    }
}
