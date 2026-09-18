package com.rs2.model.gameplay.castlewars;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public final class CastleWarsManager {
    public static final int SARADOMIN_PORTAL_ID = 4387;
    public static final int ZAMORAK_PORTAL_ID = 4388;
    public static final int GUTHIX_PORTAL_ID = 4408;

    private static final Position SARADOMIN_WAITING_ROOM = new Position(2377, 9485, 0);
    private static final Position ZAMORAK_WAITING_ROOM = new Position(2421, 9524, 0);

    private static final Map<Player, Team> waitingPlayers = new IdentityHashMap<Player, Team>();

    private CastleWarsManager() {
    }

    public static boolean handleLobbyPortal(Player player, int objectId) {
        Team requestedTeam;
        if (objectId == SARADOMIN_PORTAL_ID) {
            requestedTeam = Team.SARADOMIN;
        } else if (objectId == ZAMORAK_PORTAL_ID) {
            requestedTeam = Team.ZAMORAK;
        } else if (objectId == GUTHIX_PORTAL_ID) {
            requestedTeam = null;
        } else {
            return false;
        }

        cleanupWaitingPlayers();

        int saradominPlayers = getWaitingPlayerCount(Team.SARADOMIN);
        int zamorakPlayers = getWaitingPlayerCount(Team.ZAMORAK);

        Team team;
        if (requestedTeam == null) {
            if (saradominPlayers < zamorakPlayers) {
                team = Team.SARADOMIN;
            } else if (zamorakPlayers < saradominPlayers) {
                team = Team.ZAMORAK;
            } else {
                team = GameUtil.randomInt(2) == 0 ? Team.SARADOMIN : Team.ZAMORAK;
            }
        } else {
            team = requestedTeam;
            if (team == Team.SARADOMIN && saradominPlayers > zamorakPlayers) {
                player.getPacketSender().sendGameMessage("The Saradomin team has too many players. Try the other team.");
                return true;
            }
            if (team == Team.ZAMORAK && zamorakPlayers > saradominPlayers) {
                player.getPacketSender().sendGameMessage("The Zamorak team has too many players. Try the other team.");
                return true;
            }
        }

        waitingPlayers.put(player, team);
        Position destination = team == Team.SARADOMIN ? SARADOMIN_WAITING_ROOM : ZAMORAK_WAITING_ROOM;
        player.moveTo(new Position(destination.getX(), destination.getY(), destination.getPlane()));
        player.getPacketSender().sendGameMessage("You join the " + (team == Team.SARADOMIN ? "Saradomin" : "Zamorak") + " team.");
        return true;
    }

    public static Team getWaitingTeam(Player player) {
        cleanupWaitingPlayers();
        return waitingPlayers.get(player);
    }

    public static void leaveWaitingRoom(Player player) {
        waitingPlayers.remove(player);
    }

    public static int getWaitingPlayerCount(Team team) {
        cleanupWaitingPlayers();
        int count = 0;
        for (Team waitingTeam : waitingPlayers.values()) {
            if (waitingTeam == team) {
                ++count;
            }
        }
        return count;
    }

    private static void cleanupWaitingPlayers() {
        Iterator<Map.Entry<Player, Team>> iterator = waitingPlayers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, Team> entry = iterator.next();
            Player player = entry.getKey();
            if (!isOnline(player) || !isInWaitingRoom(player, entry.getValue())) {
                iterator.remove();
            }
        }
    }

    private static boolean isOnline(Player player) {
        Player[] players = World.getPlayers();
        for (Player onlinePlayer : players) {
            if (onlinePlayer == player) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInWaitingRoom(Player player, Team team) {
        Position position = player.getPosition();
        Position center = team == Team.SARADOMIN ? SARADOMIN_WAITING_ROOM : ZAMORAK_WAITING_ROOM;
        return position.getPlane() == center.getPlane()
                && Math.abs(position.getX() - center.getX()) <= 16
                && Math.abs(position.getY() - center.getY()) <= 16;
    }

    public enum Team {
        SARADOMIN,
        ZAMORAK
    }
}
