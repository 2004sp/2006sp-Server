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
    public static final int SARADOMIN_EXIT_PORTAL_ID = 4389;
    public static final int ZAMORAK_EXIT_PORTAL_ID = 4390;
    public static final int MINIMUM_PLAYERS_PER_TEAM = 1;

    private static final Position CASTLE_WARS_LOBBY = new Position(2441, 3090, 0);
    private static final Position SARADOMIN_WAITING_ROOM = new Position(2377, 9485, 0);
    private static final Position ZAMORAK_WAITING_ROOM = new Position(2421, 9524, 0);

    private static final Map<Player, Team> waitingPlayers = new IdentityHashMap<Player, Team>();

    private CastleWarsManager() {
    }

    public static boolean handlePortal(Player player, int objectId) {
        if (objectId == SARADOMIN_EXIT_PORTAL_ID || objectId == ZAMORAK_EXIT_PORTAL_ID) {
            leaveWaitingRoom(player);
            player.moveTo(new Position(CASTLE_WARS_LOBBY.getX(), CASTLE_WARS_LOBBY.getY(), CASTLE_WARS_LOBBY.getPlane()));
            player.getPacketSender().sendGameMessage("You leave the Castle Wars waiting room.");
            return true;
        }
        return handleLobbyPortal(player, objectId);
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

        if (hasRestrictedTeamColourEquipment(player)) {
            player.getPacketSender().sendGameMessage("You can't wear anything in the head or cape slots in Castle Wars.");
            player.getPacketSender().sendGameMessage("Remove your headgear and cape before entering the portal.");
            return true;
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

    public static boolean isWaitingPlayer(Player player) {
        return getWaitingTeam(player) != null;
    }

    public static boolean isTeamColourEquipmentSlot(int slot) {
        return slot == 0 || slot == 1;
    }

    private static boolean hasRestrictedTeamColourEquipment(Player player) {
        return player.getEquipmentManager().getItemIdAtSlot(0) > 0
                || player.getEquipmentManager().getItemIdAtSlot(1) > 0;
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

    public static boolean hasMinimumPlayersToStart() {
        return getWaitingPlayerCount(Team.SARADOMIN) >= MINIMUM_PLAYERS_PER_TEAM
                && getWaitingPlayerCount(Team.ZAMORAK) >= MINIMUM_PLAYERS_PER_TEAM;
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
