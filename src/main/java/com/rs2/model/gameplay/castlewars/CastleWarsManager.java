package com.rs2.model.gameplay.castlewars;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemStack;
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

    public static final int SARADOMIN_HOOD_ID = 4513;
    public static final int SARADOMIN_CLOAK_ID = 4514;
    public static final int ZAMORAK_HOOD_ID = 4515;
    public static final int ZAMORAK_CLOAK_ID = 4516;
    public static final int CASTLE_WARS_TICKET_ID = 4067;

    public static final int MINIMUM_PLAYERS_PER_TEAM = 1;
    public static final int WAITING_DURATION_SECONDS = 5 * 60;
    public static final int GAME_DURATION_SECONDS = 20 * 60;

    private static final int WAITING_INTERFACE_ID = 6673;
    private static final int WAITING_TIMER_TEXT_ID = 6570;
    private static final int WAITING_ZAMORAK_TEXT_ID = 6572;
    private static final int WAITING_SARADOMIN_TEXT_ID = 6664;
    private static final int GAME_INTERFACE_ID = 11344;
    private static final int GAME_TIMER_TEXT_ID = 11353;

    private static final Position CASTLE_WARS_LOBBY = new Position(2441, 3090, 0);
    private static final Position SARADOMIN_WAITING_ROOM = new Position(2377, 9485, 0);
    private static final Position ZAMORAK_WAITING_ROOM = new Position(2421, 9524, 0);

    private static final Map<Player, Team> waitingPlayers = new IdentityHashMap<Player, Team>();
    private static final Map<Player, Team> gamePlayers = new IdentityHashMap<Player, Team>();

    private static boolean initialized;
    private static boolean gameInProgress;
    private static long nextGameStartMillis = -1L;
    private static long gameEndMillis = -1L;
    private static long lastProcessedSecond = -1L;
    private static int saradominScore;
    private static int zamorakScore;

    private CastleWarsManager() {
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        World.scheduleTickTask(new CastleWarsTickTask());
    }

    public static void process() {
        long now = System.currentTimeMillis();
        long second = now / 1000L;
        if (second == lastProcessedSecond) {
            return;
        }
        lastProcessedSecond = second;

        cleanupWaitingPlayers();
        cleanupGamePlayers();

        if (gameInProgress) {
            if (now >= gameEndMillis) {
                endGame(now);
            }
        } else {
            if (hasMinimumPlayersToStartInternal()) {
                if (nextGameStartMillis < 0L) {
                    nextGameStartMillis = now + WAITING_DURATION_SECONDS * 1000L;
                }
                if (now >= nextGameStartMillis) {
                    startGame(now);
                }
            } else {
                nextGameStartMillis = -1L;
            }
        }

        updateWaitingRoomInterfaces(now);
        updateGameInterfaces(now);
    }

    public static boolean handlePortal(Player player, int objectId) {
        initialize();
        if (objectId == SARADOMIN_EXIT_PORTAL_ID || objectId == ZAMORAK_EXIT_PORTAL_ID) {
            leaveWaitingRoom(player);
            clearCastleWarsInterface(player);
            moveToLobby(player);
            player.getPacketSender().sendGameMessage("You leave the Castle Wars waiting room.");
            return true;
        }
        return handleLobbyPortal(player, objectId);
    }

    public static boolean handleLobbyPortal(Player player, int objectId) {
        initialize();

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

        int saradominPlayers = getWaitingPlayerCountInternal(Team.SARADOMIN);
        int zamorakPlayers = getWaitingPlayerCountInternal(Team.ZAMORAK);

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

        gamePlayers.remove(player);
        waitingPlayers.put(player, team);
        Position destination = team == Team.SARADOMIN ? SARADOMIN_WAITING_ROOM : ZAMORAK_WAITING_ROOM;
        player.moveTo(new Position(destination.getX(), destination.getY(), destination.getPlane()));
        player.getPacketSender().sendGameMessage("You join the " + getTeamName(team) + " team.");
        updateWaitingRoomInterface(player, System.currentTimeMillis());
        return true;
    }

    public static Team getWaitingTeam(Player player) {
        cleanupWaitingPlayers();
        return waitingPlayers.get(player);
    }

    public static Team getGameTeam(Player player) {
        cleanupGamePlayers();
        return gamePlayers.get(player);
    }

    public static Team getTeam(Player player) {
        Team team = gamePlayers.get(player);
        if (team != null) {
            return team;
        }
        return waitingPlayers.get(player);
    }

    public static boolean isWaitingPlayer(Player player) {
        return getWaitingTeam(player) != null;
    }

    public static boolean isInGame(Player player) {
        return getGameTeam(player) != null;
    }

    public static boolean areOpponents(Player first, Player second) {
        Team firstTeam = gamePlayers.get(first);
        Team secondTeam = gamePlayers.get(second);
        return firstTeam != null && secondTeam != null && firstTeam != secondTeam;
    }

    public static boolean areTeamMates(Player first, Player second) {
        Team firstTeam = gamePlayers.get(first);
        Team secondTeam = gamePlayers.get(second);
        return firstTeam != null && firstTeam == secondTeam;
    }

    public static void leaveWaitingRoom(Player player) {
        waitingPlayers.remove(player);
    }

    public static boolean isTeamColourEquipmentSlot(int slot) {
        return slot == 0 || slot == 1;
    }

    public static boolean hasMinimumPlayersToStart() {
        cleanupWaitingPlayers();
        return hasMinimumPlayersToStartInternal();
    }

    public static int getWaitingPlayerCount(Team team) {
        cleanupWaitingPlayers();
        return getWaitingPlayerCountInternal(team);
    }

    public static int getGamePlayerCount(Team team) {
        cleanupGamePlayers();
        int count = 0;
        for (Team gameTeam : gamePlayers.values()) {
            if (gameTeam == team) {
                ++count;
            }
        }
        return count;
    }

    public static boolean isGameInProgress() {
        return gameInProgress;
    }

    public static int getSaradominScore() {
        return saradominScore;
    }

    public static int getZamorakScore() {
        return zamorakScore;
    }

    public static void scorePoint(Team team) {
        if (!gameInProgress) {
            return;
        }
        if (team == Team.SARADOMIN) {
            ++saradominScore;
        } else {
            ++zamorakScore;
        }
    }

    public static void respawnPlayer(Player player) {
        Team team = gamePlayers.get(player);
        if (team == null) {
            return;
        }
        moveToTeamSpawn(player, team);
        player.getPacketSender().sendGameMessage("You respawn in your team's castle.");
    }

    public static void moveToLobby(Player player) {
        player.moveTo(new Position(
            CASTLE_WARS_LOBBY.getX() - 2 + GameUtil.randomInt(5),
            CASTLE_WARS_LOBBY.getY() - 2 + GameUtil.randomInt(5),
            CASTLE_WARS_LOBBY.getPlane()
        ));
    }

    private static void startGame(long now) {
        cleanupWaitingPlayers();
        if (!hasMinimumPlayersToStartInternal()) {
            nextGameStartMillis = -1L;
            return;
        }

        IdentityHashMap<Player, Team> starters = new IdentityHashMap<Player, Team>(waitingPlayers);
        waitingPlayers.clear();

        saradominScore = 0;
        zamorakScore = 0;
        gameInProgress = true;
        gameEndMillis = now + GAME_DURATION_SECONDS * 1000L;
        nextGameStartMillis = -1L;

        for (Map.Entry<Player, Team> entry : starters.entrySet()) {
            Player player = entry.getKey();
            Team team = entry.getValue();
            if (!isOnline(player)) {
                continue;
            }
            gamePlayers.put(player, team);
            equipTeamColours(player, team);
            moveToTeamSpawn(player, team);
            player.getPacketSender().sendGameMessage("The Castle Wars game has begun!");
            updateGameInterface(player, now);
        }
    }

    private static void endGame(long now) {
        int saradominReward = saradominScore > zamorakScore ? 2 : saradominScore == zamorakScore ? 1 : 0;
        int zamorakReward = zamorakScore > saradominScore ? 2 : saradominScore == zamorakScore ? 1 : 0;

        IdentityHashMap<Player, Team> finishers = new IdentityHashMap<Player, Team>(gamePlayers);
        gamePlayers.clear();
        gameInProgress = false;
        gameEndMillis = -1L;

        for (Map.Entry<Player, Team> entry : finishers.entrySet()) {
            Player player = entry.getKey();
            Team team = entry.getValue();
            if (!isOnline(player)) {
                continue;
            }

            removeTeamColours(player);
            clearCastleWarsInterface(player);

            int reward = team == Team.SARADOMIN ? saradominReward : zamorakReward;
            if (reward > 0) {
                player.getInventoryManager().addItem(new ItemStack(CASTLE_WARS_TICKET_ID, reward));
            }

            if (saradominScore == zamorakScore) {
                player.getPacketSender().sendGameMessage("The Castle Wars game ended in a draw.");
            } else if ((team == Team.SARADOMIN && saradominScore > zamorakScore)
                    || (team == Team.ZAMORAK && zamorakScore > saradominScore)) {
                player.getPacketSender().sendGameMessage("Your team won the Castle Wars game.");
            } else {
                player.getPacketSender().sendGameMessage("Your team lost the Castle Wars game.");
            }
            if (reward > 0) {
                player.getPacketSender().sendGameMessage("You receive " + reward + " Castle Wars ticket" + (reward == 1 ? "." : "s."));
            }
            moveToLobby(player);
        }

        nextGameStartMillis = hasMinimumPlayersToStartInternal()
            ? now + WAITING_DURATION_SECONDS * 1000L
            : -1L;
    }

    private static void equipTeamColours(Player player, Team team) {
        int hoodId = team == Team.SARADOMIN ? SARADOMIN_HOOD_ID : ZAMORAK_HOOD_ID;
        int cloakId = team == Team.SARADOMIN ? SARADOMIN_CLOAK_ID : ZAMORAK_CLOAK_ID;
        player.getEquipmentManager().getContainer().setItem(0, new ItemStack(hoodId));
        player.getEquipmentManager().getContainer().setItem(1, new ItemStack(cloakId));
        player.getEquipmentManager().refresh();
        player.setAppearanceUpdateRequired(true);
    }

    private static void removeTeamColours(Player player) {
        ItemStack head = player.getEquipmentManager().getContainer().getItemAt(0);
        ItemStack cape = player.getEquipmentManager().getContainer().getItemAt(1);
        if (head != null && (head.getId() == SARADOMIN_HOOD_ID || head.getId() == ZAMORAK_HOOD_ID)) {
            player.getEquipmentManager().getContainer().setItem(0, null);
        }
        if (cape != null && (cape.getId() == SARADOMIN_CLOAK_ID || cape.getId() == ZAMORAK_CLOAK_ID)) {
            player.getEquipmentManager().getContainer().setItem(1, null);
        }
        player.getEquipmentManager().refresh();
        player.setAppearanceUpdateRequired(true);
    }

    private static void moveToTeamSpawn(Player player, Team team) {
        if (team == Team.SARADOMIN) {
            player.moveTo(new Position(2424 + GameUtil.randomInt(5), 3075 + GameUtil.randomInt(4), 1));
        } else {
            player.moveTo(new Position(2370 + GameUtil.randomInt(5), 3128 + GameUtil.randomInt(4), 1));
        }
    }

    private static boolean hasRestrictedTeamColourEquipment(Player player) {
        return player.getEquipmentManager().getItemIdAtSlot(0) > 0
                || player.getEquipmentManager().getItemIdAtSlot(1) > 0;
    }

    private static boolean hasMinimumPlayersToStartInternal() {
        return getWaitingPlayerCountInternal(Team.SARADOMIN) >= MINIMUM_PLAYERS_PER_TEAM
                && getWaitingPlayerCountInternal(Team.ZAMORAK) >= MINIMUM_PLAYERS_PER_TEAM;
    }

    private static int getWaitingPlayerCountInternal(Team team) {
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

    private static void cleanupGamePlayers() {
        Iterator<Map.Entry<Player, Team>> iterator = gamePlayers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, Team> entry = iterator.next();
            if (!isOnline(entry.getKey())) {
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

    private static void updateWaitingRoomInterfaces(long now) {
        for (Map.Entry<Player, Team> entry : waitingPlayers.entrySet()) {
            updateWaitingRoomInterface(entry.getKey(), now);
        }
    }

    private static void updateWaitingRoomInterface(Player player, long now) {
        String timerText;
        if (gameInProgress) {
            long untilNextGame = Math.max(0L, gameEndMillis - now) + WAITING_DURATION_SECONDS * 1000L;
            timerText = "Next Game Begins In: " + formatTime(untilNextGame);
        } else if (nextGameStartMillis >= 0L && hasMinimumPlayersToStartInternal()) {
            timerText = "Next Game Begins In: " + formatTime(Math.max(0L, nextGameStartMillis - now));
        } else {
            timerText = "Waiting for players to join the other team.";
        }

        player.getPacketSender().showWalkableInterface(WAITING_INTERFACE_ID);
        player.getPacketSender().sendInterfaceText(timerText, WAITING_TIMER_TEXT_ID);
        player.getPacketSender().sendInterfaceText("Zamorak Players: " + getWaitingPlayerCountInternal(Team.ZAMORAK), WAITING_ZAMORAK_TEXT_ID);
        player.getPacketSender().sendInterfaceText("Saradomin Players: " + getWaitingPlayerCountInternal(Team.SARADOMIN), WAITING_SARADOMIN_TEXT_ID);
    }

    private static void updateGameInterfaces(long now) {
        if (!gameInProgress) {
            return;
        }
        for (Player player : gamePlayers.keySet()) {
            updateGameInterface(player, now);
        }
    }

    private static void updateGameInterface(Player player, long now) {
        player.getPacketSender().showWalkableInterface(GAME_INTERFACE_ID);
        player.getPacketSender().sendInterfaceText(formatTime(Math.max(0L, gameEndMillis - now)), GAME_TIMER_TEXT_ID);
    }

    private static void clearCastleWarsInterface(Player player) {
        player.getPacketSender().showWalkableInterface(-1);
    }

    private static String formatTime(long millis) {
        long totalSeconds = (millis + 999L) / 1000L;
        long minutes = totalSeconds / 60L;
        long seconds = totalSeconds % 60L;
        return minutes + ":" + (seconds < 10L ? "0" : "") + seconds;
    }

    private static String getTeamName(Team team) {
        return team == Team.SARADOMIN ? "Saradomin" : "Zamorak";
    }

    public enum Team {
        SARADOMIN,
        ZAMORAK
    }
}
