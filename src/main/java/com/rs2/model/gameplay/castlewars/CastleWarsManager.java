package com.rs2.model.gameplay.castlewars;

import com.rs2.model.Position;
import com.rs2.model.GameplayHelper;
import com.rs2.model.combat.CombatType;
import com.rs2.model.World;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.PotionHandler;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.path.WalkingCollisionMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public final class CastleWarsManager {
    public static final int SARADOMIN_PORTAL_ID = 4387;
    public static final int ZAMORAK_PORTAL_ID = 4388;
    public static final int GUTHIX_PORTAL_ID = 4408;
    public static final int SARADOMIN_EXIT_PORTAL_ID = 4389;
    public static final int ZAMORAK_EXIT_PORTAL_ID = 4390;
    public static final int SARADOMIN_GAME_EXIT_PORTAL_ID = 4406;
    public static final int ZAMORAK_GAME_EXIT_PORTAL_ID = 4407;
    public static final int BANDAGE_TABLE_ID = 4458;
    public static final int SARADOMIN_ENERGY_BARRIER_ID = 4469;
    public static final int ZAMORAK_ENERGY_BARRIER_ID = 4470;
    public static final int SARADOMIN_SPAWN_LADDER_ID = 6280;
    public static final int ZAMORAK_SPAWN_LADDER_ID = 6281;
    public static final int SARADOMIN_SPAWN_TRAPDOOR_ID = 4471;
    public static final int ZAMORAK_SPAWN_TRAPDOOR_ID = 4472;
    public static final int STEPPING_STONE_ID = 4411;

    public static final int SARADOMIN_HOOD_ID = 4513;
    public static final int SARADOMIN_CLOAK_ID = 4514;
    public static final int ZAMORAK_HOOD_ID = 4515;
    public static final int ZAMORAK_CLOAK_ID = 4516;
    public static final int SARADOMIN_FLAG_ID = 4037;
    public static final int ZAMORAK_FLAG_ID = 4039;
    public static final int CASTLE_WARS_TICKET_ID = 4067;
    public static final int LANTHUS_NPC_ID = 1526;

    private static final int GUTHIX_SHEEP_TRANSFORMATION_ID = 5726;
    private static final int SARADOMIN_RABBIT_TRANSFORMATION_ID = 5727;
    private static final int ZAMORAK_IMP_TRANSFORMATION_ID = 5728;

    public static final int MINIMUM_PLAYERS_PER_TEAM = 1;
    public static final int WAITING_DURATION_SECONDS = 5 * 60;
    public static final int GAME_DURATION_SECONDS = 20 * 60;

    private static final int WAITING_INTERFACE_ID = 6673;
    private static final int WAITING_TIMER_TEXT_ID = 6570;
    private static final int WAITING_ZAMORAK_TEXT_ID = 6572;
    private static final int WAITING_SARADOMIN_TEXT_ID = 6664;
    private static final int GAME_INTERFACE_ID = 11344;
    private static final int GAME_ZAMORAK_SCORE_TEXT_ID = 11345;
    private static final int GAME_SARADOMIN_SCORE_TEXT_ID = 11346;
    private static final int GAME_TIMER_TEXT_ID = 11353;
    private static final int GAME_ZAMORAK_FLAG_TEXT_ID = 11349;
    private static final int GAME_SARADOMIN_FLAG_TEXT_ID = 11350;
    private static final int GAME_MAIN_GATE_TEXT_ID = 11352;
    private static final int GAME_SIDE_DOOR_TEXT_ID = 11356;
    private static final int GAME_TUNNEL_ONE_TEXT_ID = 11358;
    private static final int GAME_TUNNEL_TWO_TEXT_ID = 11360;
    private static final int GAME_CATAPULT_TEXT_ID = 11362;

    private static final int SCOREBOARD_OBJECT_ID = 4484;
    private static final int SCOREBOARD_INTERFACE_ID = 11333;
    private static final int SCOREBOARD_TITLE_TEXT_ID = 11334;
    private static final int SCOREBOARD_SARADOMIN_TEXT_ID = 11335;
    private static final int SCOREBOARD_ZAMORAK_TEXT_ID = 11336;
    private static final int SCOREBOARD_STATE_VERSION = 1;
    private static final File SCOREBOARD_STATE_FILE = new File("./data/castle_wars_scoreboard.dat");

    private static final Position CASTLE_WARS_LOBBY = new Position(2441, 3090, 0);
    private static final Position SARADOMIN_WAITING_ROOM = new Position(2377, 9485, 0);
    private static final Position ZAMORAK_WAITING_ROOM = new Position(2421, 9524, 0);
    private static final int STEPPING_STONE_JUMP_ANIMATION = 741;
    private static final Position[] SOUTHWEST_STEPPING_STONE_ROUTE = new Position[]{
        new Position(2378, 3083, 0),
        new Position(2378, 3084, 0),
        new Position(2378, 3085, 0),
        new Position(2377, 3085, 0),
        new Position(2377, 3086, 0),
        new Position(2377, 3087, 0),
        new Position(2377, 3088, 0),
        new Position(2377, 3089, 0)
    };
    private static final Position[] NORTHEAST_STEPPING_STONE_ROUTE = new Position[]{
        new Position(2420, 3122, 0),
        new Position(2420, 3123, 0),
        new Position(2419, 3123, 0),
        new Position(2419, 3124, 0),
        new Position(2419, 3125, 0),
        new Position(2418, 3125, 0),
        new Position(2418, 3126, 0)
    };

    private static final Map<Player, Team> waitingPlayers = new IdentityHashMap<Player, Team>();
    private static final Map<Player, Team> gamePlayers = new IdentityHashMap<Player, Team>();
    private static final Map<Player, ReplacementOffer> replacementOffers = new IdentityHashMap<Player, ReplacementOffer>();
    private static final Map<Player, Long> replacementOfferCooldowns = new IdentityHashMap<Player, Long>();

    private static final int REPLACEMENT_OFFER_DURATION_SECONDS = 30;

    private static boolean initialized;
    private static boolean gameInProgress;
    private static long nextGameStartMillis = -1L;
    private static long gameEndMillis = -1L;
    private static long lastProcessedSecond = -1L;
    private static int gameTeamCapacity;
    private static int saradominScore;
    private static int zamorakScore;
    private static int scoreboardSeasonKey;
    private static int saradominSeasonVictories;
    private static int zamorakSeasonVictories;
    private static boolean saradominFlagAtBase = true;
    private static boolean zamorakFlagAtBase = true;
    private static Player saradominFlagHolder;
    private static Player zamorakFlagHolder;
    private static GroundItem saradominDroppedFlag;
    private static GroundItem zamorakDroppedFlag;

    private CastleWarsManager() {
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        loadScoreboardState();
        spawnLanthus();
        World.scheduleTickTask(new CastleWarsTickTask());
    }

    private static void spawnLanthus() {
        if (Npc.findByDefinitionId(LANTHUS_NPC_ID) != null) {
            return;
        }
        // Keep Lanthus beside the lobby return tile so players never spawn on top of him.
        GameplayHelper.spawnNpc(LANTHUS_NPC_ID, 2440, 3089, 0, 2);
    }

    public static boolean relocatePlayerOnLogin(Player player) {
        if (player == null || !isCastleWarsMinigamePosition(player.getPosition())) {
            return false;
        }

        // Purge any stale session entry left by the character that logged out.
        cleanupWaitingPlayers();
        cleanupGamePlayers();
        waitingPlayers.remove(player);
        gamePlayers.remove(player);
        replacementOffers.remove(player);
        replacementOfferCooldowns.remove(player);
        clearWaitingRoomGodTransformation(player);

        returnCarriedFlagToBase(player);
        clearFlagWeapon(player);
        removeTeamColours(player);
        removeBandages(player);
        removeTemporaryCastleWarsInventoryItems(player);
        CastleWarsEngineeringManager.cleanupPlayerSupplies(player);
        player.resetCombatState();

        // This runs before World.registerPlayer(), so update the saved position
        // directly instead of using moveTo/applyTeleportPosition (which assume a
        // registered player index).
        Position position = player.getPosition();
        position.set(CASTLE_WARS_LOBBY);
        position.setPreviousX(CASTLE_WARS_LOBBY.getX());
        position.setPreviousY(CASTLE_WARS_LOBBY.getY() + 1);
        return true;
    }

    public static boolean isCastleWarsMinigamePosition(Position position) {
        if (position == null) {
            return false;
        }

        int x = position.getX();
        int y = position.getY();
        int plane = position.getPlane();

        // Surface arena, both castles, spawn rooms and their upper floors.
        boolean surfaceArena = plane >= 0 && plane <= 3
                && x >= 2368 && x <= 2431
                && y >= 3072 && y <= 3135;

        // Waiting caves plus the complete underground tunnel network.
        boolean underground = plane == 0
                && x >= 2360 && x <= 2438
                && y >= 9468 && y <= 9540;

        return surfaceArena || underground;
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
        cleanupDroppedFlags();
        CastleWarsEngineeringManager.processBarricadeFires(now);
        CastleWarsEngineeringManager.processCatapultFires(now);

        if (gameInProgress) {
            if (now >= gameEndMillis) {
                endGame(now);
            } else {
                processReplacementOffers(now);
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

    public static boolean handleFirstObjectAction(Player player, int objectId, int objectX, int objectY) {
        if (objectId == SCOREBOARD_OBJECT_ID) {
            showScoreboard(player);
            return true;
        }
        if (handlePortal(player, objectId)) {
            return true;
        }
        if (objectId == STEPPING_STONE_ID && isInGame(player)) {
            useSteppingStone(player, objectX, objectY);
            return true;
        }
        if (objectId == BANDAGE_TABLE_ID) {
            takeBandages(player, 1);
            return true;
        }
        if (CastleWarsEngineeringManager.handleSupplyTable(player, objectId)) {
            return true;
        }
        if (CastleWarsEngineeringManager.handleClimbingRope(player, objectId, objectX, objectY)) {
            return true;
        }
        if (CastleWarsEngineeringManager.operateCatapult(player, objectId)) {
            return true;
        }
        if (CastleWarsEngineeringManager.handleSideDoor(player, objectId, objectX, objectY)) {
            return true;
        }
        if (CastleWarsEngineeringManager.handleMainDoor(player, objectId, objectX, objectY)) {
            return true;
        }
        if (objectId == SARADOMIN_ENERGY_BARRIER_ID) {
            passEnergyBarrier(player, Team.SARADOMIN, objectX, objectY);
            return true;
        }
        if (objectId == ZAMORAK_ENERGY_BARRIER_ID) {
            passEnergyBarrier(player, Team.ZAMORAK, objectX, objectY);
            return true;
        }
        if (objectId == SARADOMIN_SPAWN_LADDER_ID) {
            useSpawnRoomLadder(player, Team.SARADOMIN, true);
            return true;
        }
        if (objectId == ZAMORAK_SPAWN_LADDER_ID) {
            useSpawnRoomLadder(player, Team.ZAMORAK, true);
            return true;
        }
        if (objectId == SARADOMIN_SPAWN_TRAPDOOR_ID) {
            useSpawnRoomLadder(player, Team.SARADOMIN, false);
            return true;
        }
        if (objectId == ZAMORAK_SPAWN_TRAPDOOR_ID) {
            useSpawnRoomLadder(player, Team.ZAMORAK, false);
            return true;
        }
        if (objectId == 4900 || objectId == 4902 || objectId == 4377) {
            handleFlagObject(player, Team.SARADOMIN);
            return true;
        }
        if (objectId == 4901 || objectId == 4903 || objectId == 4378) {
            handleFlagObject(player, Team.ZAMORAK);
            return true;
        }
        if (handleCastleWarsTraversal(player, objectId, objectX, objectY)) {
            return true;
        }
        return false;
    }

    public static boolean handleSecondObjectAction(Player player, int objectId, int objectX, int objectY) {
        if (objectId == SCOREBOARD_OBJECT_ID) {
            showPlayerScoreboardStats(player);
            return true;
        }
        if (objectId == BANDAGE_TABLE_ID) {
            takeBandages(player, 5);
            return true;
        }
        if (CastleWarsEngineeringManager.attackMainDoor(player, objectId, objectX, objectY)) {
            return true;
        }
        return false;
    }

    public static boolean handlePortal(Player player, int objectId) {
        initialize();
        if (objectId == SARADOMIN_EXIT_PORTAL_ID || objectId == ZAMORAK_EXIT_PORTAL_ID) {
            leaveWaitingRoom(player);
            removeTeamColours(player);
            clearCastleWarsInterface(player);
            moveToLobby(player);
            player.getPacketSender().sendGameMessage("You leave the Castle Wars waiting room.");
            return true;
        }
        if (objectId == SARADOMIN_GAME_EXIT_PORTAL_ID || objectId == ZAMORAK_GAME_EXIT_PORTAL_ID) {
            Team requiredTeam = objectId == SARADOMIN_GAME_EXIT_PORTAL_ID ? Team.SARADOMIN : Team.ZAMORAK;
            Team playerTeam = gamePlayers.get(player);
            if (playerTeam != null && playerTeam != requiredTeam) {
                player.getPacketSender().sendGameMessage("You cannot use the other team's exit portal.");
                return true;
            }
            if (playerTeam != null) {
                leaveGame(player);
            } else {
                setCastleWarsAttackOption(player, false);
                removeTeamColours(player);
                removeBandages(player);
                CastleWarsEngineeringManager.cleanupPlayerSupplies(player);
                clearCastleWarsInterface(player);
                player.resetCombatState();
                moveToLobby(player);
                player.getPacketSender().sendGameMessage("You return to the Castle Wars lobby.");
            }
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
        if (hasRestrictedInventoryItems(player)) {
            player.getPacketSender().sendGameMessage("You can only bring potions, runes and equippable items into Castle Wars.");
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
        replacementOffers.remove(player);
        replacementOfferCooldowns.remove(player);
        waitingPlayers.put(player, team);
        clearWaitingRoomGodTransformation(player);
        applyWaitingRoomGodTransformation(player, objectId);
        equipTeamColours(player, team);
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

    public static void spreadWaitingPlayer(Player player) {
        Team team = waitingPlayers.get(player);
        if (team == null) {
            return;
        }

        Position center = team == Team.SARADOMIN ? SARADOMIN_WAITING_ROOM : ZAMORAK_WAITING_ROOM;
        for (int attempt = 0; attempt < 40; ++attempt) {
            int x = center.getX() - 9 + GameUtil.randomInt(19);
            int y = center.getY() - 9 + GameUtil.randomInt(19);
            if (WalkingCollisionMap.getTileFlags(x, y, center.getPlane()) != 0) {
                continue;
            }
            if (isOccupiedWaitingTile(x, y, center.getPlane(), player)) {
                continue;
            }
            player.moveTo(new Position(x, y, center.getPlane()));
            return;
        }
    }

    public static Position getWaitingRoomWanderTarget(Player player, int radius) {
        Team team = waitingPlayers.get(player);
        if (team == null) {
            return null;
        }

        Position center = team == Team.SARADOMIN ? SARADOMIN_WAITING_ROOM : ZAMORAK_WAITING_ROOM;
        Position current = player.getPosition();
        int wanderRadius = Math.max(1, Math.min(radius, 5));

        for (int attempt = 0; attempt < 30; ++attempt) {
            int x = current.getX() - wanderRadius + GameUtil.randomInt(wanderRadius * 2 + 1);
            int y = current.getY() - wanderRadius + GameUtil.randomInt(wanderRadius * 2 + 1);
            if (x == current.getX() && y == current.getY()) {
                continue;
            }
            if (Math.abs(x - center.getX()) > 9 || Math.abs(y - center.getY()) > 9) {
                continue;
            }
            if (WalkingCollisionMap.getTileFlags(x, y, center.getPlane()) != 0) {
                continue;
            }
            if (isOccupiedWaitingTile(x, y, center.getPlane(), player)) {
                continue;
            }
            return new Position(x, y, center.getPlane());
        }
        return null;
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
        replacementOffers.remove(player);
        replacementOfferCooldowns.remove(player);
        clearWaitingRoomGodTransformation(player);
    }

    public static void leaveGame(Player player) {
        if (gamePlayers.remove(player) == null) {
            return;
        }
        returnCarriedFlagToBase(player);
        setCastleWarsAttackOption(player, false);
        removeTeamColours(player);
        removeBandages(player);
        CastleWarsEngineeringManager.cleanupPlayerSupplies(player);
        clearCastleWarsInterface(player);
        clearFlagHint(player);
        player.resetCombatState();
        moveToLobby(player);
        player.getPacketSender().sendGameMessage("You leave Castle Wars and return to the lobby.");
    }

    public static boolean handleReplacementOfferButton(Player player, int buttonId) {
        if (buttonId != 2461 && buttonId != 2462) {
            return false;
        }

        ReplacementOffer offer = replacementOffers.remove(player);
        if (offer == null) {
            return false;
        }

        long now = System.currentTimeMillis();
        player.getPacketSender().closeInterfaces();

        if (buttonId == 2462) {
            replacementOfferCooldowns.put(
                    player,
                    now + REPLACEMENT_OFFER_DURATION_SECONDS * 1000L);
            player.getPacketSender().sendGameMessage("You remain in the waiting room for the next game.");
            if (waitingPlayers.containsKey(player)) {
                updateWaitingRoomInterface(player, now);
            }
            return true;
        }

        cleanupWaitingPlayers();
        cleanupGamePlayers();
        if (!gameInProgress
                || now >= gameEndMillis
                || now >= offer.expiresAtMillis
                || waitingPlayers.get(player) != offer.team
                || !hasReplacementVacancy(offer.team)) {
            player.getPacketSender().sendGameMessage("That place in the Castle Wars game is no longer available.");
            if (waitingPlayers.containsKey(player)) {
                updateWaitingRoomInterface(player, now);
            }
            return true;
        }

        joinReplacementPlayer(player, offer.team, now);
        return true;
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

    private static void showScoreboard(Player player) {
        ensureCurrentScoreboardSeason();
        player.getPacketSender().sendInterfaceText("Total Wins This Season!", SCOREBOARD_TITLE_TEXT_ID);
        player.getPacketSender().sendInterfaceText(
                "Saradomin: " + saradominSeasonVictories, SCOREBOARD_SARADOMIN_TEXT_ID);
        player.getPacketSender().sendInterfaceText(
                "Zamorak: " + zamorakSeasonVictories, SCOREBOARD_ZAMORAK_TEXT_ID);
        player.getPacketSender().showChatboxInterface(SCOREBOARD_INTERFACE_ID);
    }

    private static void showPlayerScoreboardStats(Player player) {
        int games = Math.max(0, player.reservedSaveInt1);
        int wins = Math.max(0, player.reservedSaveInt2);
        int losses = Math.max(0, player.reservedSaveInt3);
        player.getPacketSender().sendGameMessage(
                "You've played " + games + " games of Castle Wars. - Won: "
                        + wins + " Lost: " + losses + ".");
    }

    private static void recordPlayerScoreboardResult(Player player, Team team) {
        // These versioned reserved save slots are already persisted by CharacterFileManager.
        player.reservedSaveInt1 = incrementCounter(player.reservedSaveInt1);
        if (saradominScore == zamorakScore) {
            return;
        }

        boolean won = (team == Team.SARADOMIN && saradominScore > zamorakScore)
                || (team == Team.ZAMORAK && zamorakScore > saradominScore);
        if (won) {
            player.reservedSaveInt2 = incrementCounter(player.reservedSaveInt2);
        } else {
            player.reservedSaveInt3 = incrementCounter(player.reservedSaveInt3);
        }
    }

    private static int incrementCounter(int value) {
        if (value < 0) {
            return 1;
        }
        return value == Integer.MAX_VALUE ? value : value + 1;
    }

    private static void recordTeamScoreboardVictory(Team team) {
        ensureCurrentScoreboardSeason();
        if (team == Team.SARADOMIN) {
            saradominSeasonVictories = incrementCounter(saradominSeasonVictories);
        } else if (team == Team.ZAMORAK) {
            zamorakSeasonVictories = incrementCounter(zamorakSeasonVictories);
        }
        saveScoreboardState();
    }

    private static int currentScoreboardSeasonKey() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
    }

    private static void ensureCurrentScoreboardSeason() {
        int currentSeasonKey = currentScoreboardSeasonKey();
        if (scoreboardSeasonKey == currentSeasonKey) {
            return;
        }
        scoreboardSeasonKey = currentSeasonKey;
        saradominSeasonVictories = 0;
        zamorakSeasonVictories = 0;
        saveScoreboardState();
    }

    private static void loadScoreboardState() {
        scoreboardSeasonKey = currentScoreboardSeasonKey();
        saradominSeasonVictories = 0;
        zamorakSeasonVictories = 0;

        if (!SCOREBOARD_STATE_FILE.exists()) {
            return;
        }

        try (DataInputStream input = new DataInputStream(new FileInputStream(SCOREBOARD_STATE_FILE))) {
            int version = input.readInt();
            int savedSeasonKey = input.readInt();
            int savedSaradominVictories = input.readInt();
            int savedZamorakVictories = input.readInt();
            if (version == SCOREBOARD_STATE_VERSION && savedSeasonKey == scoreboardSeasonKey) {
                saradominSeasonVictories = Math.max(0, savedSaradominVictories);
                zamorakSeasonVictories = Math.max(0, savedZamorakVictories);
            }
        } catch (IOException exception) {
            System.err.println("Could not load Castle Wars scoreboard state: " + exception.getMessage());
        }
    }

    private static void saveScoreboardState() {
        File parent = SCOREBOARD_STATE_FILE.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            System.err.println("Could not create Castle Wars scoreboard data directory.");
            return;
        }

        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(SCOREBOARD_STATE_FILE))) {
            output.writeInt(SCOREBOARD_STATE_VERSION);
            output.writeInt(scoreboardSeasonKey);
            output.writeInt(saradominSeasonVictories);
            output.writeInt(zamorakSeasonVictories);
        } catch (IOException exception) {
            System.err.println("Could not save Castle Wars scoreboard state: " + exception.getMessage());
        }
    }

    public static boolean isFlagAtBase(Team team) {
        return team == Team.SARADOMIN ? saradominFlagAtBase : zamorakFlagAtBase;
    }

    public static Player getFlagHolder(Team team) {
        return team == Team.SARADOMIN ? saradominFlagHolder : zamorakFlagHolder;
    }

    public static boolean isCarryingEnemyFlag(Player player) {
        return saradominFlagHolder == player || zamorakFlagHolder == player;
    }

    public static boolean isInTeamSpawnArea(Player player, Team team) {
        Position position = player.getPosition();
        if (position.getPlane() != 1) {
            return false;
        }
        if (team == Team.SARADOMIN) {
            return position.getX() >= 2422 && position.getX() <= 2429
                    && position.getY() >= 3074 && position.getY() <= 3080;
        }
        return position.getX() >= 2369 && position.getX() <= 2378
                && position.getY() >= 3127 && position.getY() <= 3134;
    }

    public static int giveBandages(Player player, int requestedAmount) {
        if (!isInGame(player) || requestedAmount <= 0) {
            return 0;
        }
        int freeSlots = player.getInventoryManager().getContainer().getFreeSlots();
        if (freeSlots <= 0) {
            return 0;
        }
        int amount = Math.min(requestedAmount, freeSlots);
        return player.getInventoryManager().addItemPartial(new ItemStack(4049, amount));
    }

    public static boolean useBandage(Player player) {
        if (!isInGame(player) || player.getInventoryManager().getItemAmount(4049) <= 0) {
            return false;
        }
        int maxHitpoints = player.getMaxHitpoints();
        int healAmount = Math.max(1, (int)Math.ceil(maxHitpoints * 0.10));
        int healed = Math.min(maxHitpoints, player.getCurrentHitpoints() + healAmount);
        player.getInventoryManager().removeItem(new ItemStack(4049, 1));
        player.setCurrentHitpoints(healed);
        player.setPoisonDamage(0.0);
        player.addRunEnergyPercent(30);
        return true;
    }

    public static boolean takeEnemyFlag(Player player) {
        Team team = gamePlayers.get(player);
        if (team == null) {
            return false;
        }
        Team flagTeam = team == Team.SARADOMIN ? Team.ZAMORAK : Team.SARADOMIN;
        return takeFlag(player, flagTeam);
    }

    public static boolean tryCaptureFlag(Player player) {
        Team team = gamePlayers.get(player);
        if (team == null) {
            return false;
        }
        Team enemyFlagTeam = team == Team.SARADOMIN ? Team.ZAMORAK : Team.SARADOMIN;
        Player holder = getFlagHolder(enemyFlagTeam);
        if (holder != player) {
            return false;
        }
        clearFlagWeapon(player);
        setFlagAtBase(enemyFlagTeam, true, null);
        scorePoint(team);
        player.getPacketSender().sendGameMessage("You capture the " + getTeamName(enemyFlagTeam) + " flag!");
        return true;
    }

    private static void handleFlagObject(Player player, Team flagTeam) {
        Team team = gamePlayers.get(player);
        if (team == null) {
            player.getPacketSender().sendGameMessage("You can only use the standards during a Castle Wars game.");
            return;
        }
        if (team == flagTeam) {
            if (!tryCaptureFlag(player)) {
                player.getPacketSender().sendGameMessage(isFlagAtBase(flagTeam)
                        ? "Your team's flag is safe."
                        : "Your team's flag has been taken.");
            }
            return;
        }
        takeFlag(player, flagTeam);
    }

    private static boolean takeFlag(Player player, Team flagTeam) {
        if (!isFlagAtBase(flagTeam) || getFlagHolder(flagTeam) != null) {
            player.getPacketSender().sendGameMessage("That flag has already been taken.");
            return false;
        }
        if (isCarryingEnemyFlag(player)) {
            player.getPacketSender().sendGameMessage("You are already carrying a flag.");
            return false;
        }
        if (!canEquipFlag(player)) {
            return false;
        }

        equipFlag(player, flagTeam);
        setFlagAtBase(flagTeam, false, player);
        player.getPacketSender().sendGameMessage("You take the " + getTeamName(flagTeam) + " flag!");
        return true;
    }

    public static boolean isDroppedFlagGroundItem(GroundItem groundItem) {
        return getDroppedFlagTeam(groundItem) != null;
    }

    public static boolean handleDroppedFlagPickup(Player player, GroundItem groundItem) {
        Team flagTeam = getDroppedFlagTeam(groundItem);
        if (flagTeam == null) {
            return false;
        }

        Team playerTeam = gamePlayers.get(player);
        if (playerTeam == null) {
            player.getPacketSender().sendGameMessage("You can only take this flag during a Castle Wars game.");
            return true;
        }

        if (isCarryingEnemyFlag(player)) {
            player.getPacketSender().sendGameMessage("You are already carrying a flag.");
            return true;
        }
        if (!canEquipFlag(player)) {
            return true;
        }
        if (!GroundItemManager.getInstance().removeForPickup(groundItem, player)) {
            return true;
        }

        setDroppedFlag(flagTeam, null);
        equipFlag(player, flagTeam);
        setFlagAtBase(flagTeam, false, player);
        player.getPacketSender().sendGameMessage("You take the " + getTeamName(flagTeam) + " flag!");
        return true;
    }

    private static boolean canEquipFlag(Player player) {
        int neededSlots = 0;
        if (player.getEquipmentManager().getContainer().getItemAt(3) != null) {
            ++neededSlots;
        }
        if (player.getEquipmentManager().getContainer().getItemAt(5) != null) {
            ++neededSlots;
        }
        if (player.getInventoryManager().getContainer().getFreeSlots() < neededSlots) {
            player.getPacketSender().sendGameMessage("You need enough inventory space for your weapon and shield.");
            return false;
        }
        return true;
    }

    private static void equipFlag(Player player, Team flagTeam) {
        if (player.getEquipmentManager().getContainer().getItemAt(5) != null) {
            player.getEquipmentManager().unequipSlot(5);
        }
        if (player.getEquipmentManager().getContainer().getItemAt(3) != null) {
            player.getEquipmentManager().unequipSlot(3);
        }

        int flagId = flagTeam == Team.SARADOMIN ? SARADOMIN_FLAG_ID : ZAMORAK_FLAG_ID;
        player.getEquipmentManager().getContainer().setItem(3, new ItemStack(flagId));
        player.getEquipmentManager().refresh();
        player.setAppearanceUpdateRequired(true);
    }

    private static void setFlagAtBase(Team flagTeam, boolean atBase, Player holder) {
        if (flagTeam == Team.SARADOMIN) {
            saradominFlagAtBase = atBase;
            saradominFlagHolder = holder;
        } else {
            zamorakFlagAtBase = atBase;
            zamorakFlagHolder = holder;
        }
    }

    private static GroundItem getDroppedFlag(Team flagTeam) {
        return flagTeam == Team.SARADOMIN ? saradominDroppedFlag : zamorakDroppedFlag;
    }

    private static String getFlagStatusText(Team flagTeam) {
        if (isFlagAtBase(flagTeam)) {
            return "Safe";
        }
        return getDroppedFlag(flagTeam) != null ? "Dropped" : "Taken";
    }

    private static void setDroppedFlag(Team flagTeam, GroundItem groundItem) {
        if (flagTeam == Team.SARADOMIN) {
            saradominDroppedFlag = groundItem;
        } else {
            zamorakDroppedFlag = groundItem;
        }
    }

    private static Team getDroppedFlagTeam(GroundItem groundItem) {
        if (groundItem == null) {
            return null;
        }
        if (saradominDroppedFlag == groundItem) {
            return Team.SARADOMIN;
        }
        if (zamorakDroppedFlag == groundItem) {
            return Team.ZAMORAK;
        }
        return null;
    }

    private static Team getCarriedFlagTeam(Player player) {
        if (saradominFlagHolder == player) {
            return Team.SARADOMIN;
        }
        if (zamorakFlagHolder == player) {
            return Team.ZAMORAK;
        }
        return null;
    }

    private static void returnCarriedFlagToBase(Player player) {
        Team flagTeam = getCarriedFlagTeam(player);
        if (flagTeam == null) {
            return;
        }
        clearFlagWeapon(player);
        setFlagAtBase(flagTeam, true, null);
    }

    private static void dropCarriedFlag(Player player) {
        Team flagTeam = getCarriedFlagTeam(player);
        if (flagTeam == null) {
            return;
        }

        clearFlagWeapon(player);
        int flagId = flagTeam == Team.SARADOMIN ? SARADOMIN_FLAG_ID : ZAMORAK_FLAG_ID;
        GroundItem droppedFlag = new GroundItem(new ItemStack(flagId), player.getPosition(), false, true);
        setFlagAtBase(flagTeam, false, null);
        setDroppedFlag(flagTeam, droppedFlag);
        GroundItemManager.getInstance().spawn(droppedFlag);
        player.getPacketSender().sendGameMessage("You drop the " + getTeamName(flagTeam) + " flag.");
    }

    private static void cleanupDroppedFlags() {
        cleanupDroppedFlag(Team.SARADOMIN);
        cleanupDroppedFlag(Team.ZAMORAK);
    }

    private static void cleanupDroppedFlag(Team flagTeam) {
        GroundItem droppedFlag = getDroppedFlag(flagTeam);
        if (droppedFlag == null || GroundItemManager.getInstance().contains(droppedFlag)) {
            return;
        }
        setDroppedFlag(flagTeam, null);
        setFlagAtBase(flagTeam, true, null);
    }

    private static void returnDroppedFlagToBase(Team flagTeam) {
        GroundItem droppedFlag = getDroppedFlag(flagTeam);
        if (droppedFlag == null) {
            return;
        }
        GroundItemManager.getInstance().remove(droppedFlag);
        setDroppedFlag(flagTeam, null);
        setFlagAtBase(flagTeam, true, null);
    }

    private static void clearFlagWeapon(Player player) {
        ItemStack weapon = player.getEquipmentManager().getContainer().getItemAt(3);
        if (weapon != null && (weapon.getId() == SARADOMIN_FLAG_ID || weapon.getId() == ZAMORAK_FLAG_ID)) {
            player.getEquipmentManager().getContainer().setItem(3, null);
            player.getEquipmentManager().refresh();
            player.setAppearanceUpdateRequired(true);
        }
    }

    private static void takeBandages(Player player, int requestedAmount) {
        if (!isInGame(player)) {
            player.getPacketSender().sendGameMessage("You can only take bandages during a Castle Wars game.");
            return;
        }
        int amount = giveBandages(player, requestedAmount);
        if (amount <= 0) {
            player.getPacketSender().sendGameMessage("Not enough space in your inventory.");
            return;
        }
        player.getUpdateState().setAnimation(881);
        player.getPacketSender().sendGameMessage(amount == 1
                ? "You take a bandage."
                : "You take " + amount + " bandages.");
    }

    private static void passEnergyBarrier(Player player, Team barrierTeam, int objectX, int objectY) {
        Team playerTeam = gamePlayers.get(player);
        if (playerTeam == null) {
            player.getPacketSender().sendGameMessage("You can only pass this barrier during a Castle Wars game.");
            return;
        }
        if (playerTeam != barrierTeam) {
            player.getPacketSender().sendGameMessage("You are not allowed in the other team's spawn room.");
            return;
        }

        Position position = player.getPosition();
        if (barrierTeam == Team.SARADOMIN) {
            if (objectX == 2426 && objectY == 3080) {
                int y = position.getY() <= 3080 ? 3081 : 3080;
                player.moveTo(new Position(2426, y, 1));
                return;
            }
            if (objectX == 2422 && objectY == 3076) {
                int x = position.getX() <= 2422 ? 2423 : 2422;
                player.moveTo(new Position(x, 3076, 1));
                return;
            }
        } else {
            if (objectX == 2373 && objectY == 3126) {
                int y = position.getY() <= 3126 ? 3127 : 3126;
                player.moveTo(new Position(2373, y, 1));
                return;
            }
            if (objectX == 2377 && objectY == 3131) {
                int x = position.getX() <= 2376 ? 2377 : 2376;
                player.moveTo(new Position(x, 3131, 1));
                return;
            }
        }
        player.getPacketSender().sendGameMessage("You cannot pass the barrier from here.");
    }

    private static void useSpawnRoomLadder(Player player, Team ladderTeam, boolean climbUp) {
        player.getUpdateState().setAnimation(climbUp ? 828 : 827);
        if (ladderTeam == Team.SARADOMIN) {
            player.moveTo(new Position(2429, 3075, climbUp ? 2 : 1));
        } else {
            player.moveTo(new Position(2370, 3132, climbUp ? 2 : 1));
        }
    }

    private static void useSteppingStone(Player player, int objectX, int objectY) {
        if (player.getPosition().getPlane() != 0) {
            return;
        }
        if (player.isMovementLocked()) {
            player.getPacketSender().sendGameMessage("You cannot use the stepping stones while unable to move.");
            return;
        }

        Position destination = new Position(objectX, objectY, 0);
        if (!isSteppingStoneTile(destination)) {
            return;
        }
        if (!jumpSteppingStone(player, destination)) {
            player.getPacketSender().sendGameMessage("You need to jump to the next stepping stone.");
        }
    }

    public static Position getSteppingStoneApproach(Team sourceTeam, int routeVariant) {
        Position[] route = steppingStoneRoute(routeVariant);
        if (route == null || sourceTeam == null) {
            return null;
        }
        return sourceTeam == Team.SARADOMIN ? route[0] : route[route.length - 1];
    }

    public static Position getSteppingStoneExit(Team sourceTeam, int routeVariant) {
        Position[] route = steppingStoneRoute(routeVariant);
        if (route == null || sourceTeam == null) {
            return null;
        }
        return sourceTeam == Team.SARADOMIN ? route[route.length - 1] : route[0];
    }

    public static Position getSteppingStoneNextStep(Position current, Team sourceTeam, int routeVariant) {
        Position[] route = steppingStoneRoute(routeVariant);
        if (route == null || current == null || sourceTeam == null) {
            return null;
        }

        int index = steppingStoneRouteIndex(route, current);
        if (index < 0) {
            return null;
        }
        int nextIndex = sourceTeam == Team.SARADOMIN ? index + 1 : index - 1;
        if (nextIndex < 0 || nextIndex >= route.length) {
            return null;
        }
        return route[nextIndex];
    }

    public static Position getSteppingStoneShortcutWaypoint(Position current, Position destination) {
        if (current == null || destination == null
                || current.getPlane() != 0 || destination.getPlane() != 0) {
            return null;
        }

        Position[][] routes = new Position[][]{
            SOUTHWEST_STEPPING_STONE_ROUTE,
            NORTHEAST_STEPPING_STONE_ROUTE
        };

        // Once a bot is on a crossing, keep it moving along the stones toward
        // its target (including when the target is also standing on a stone).
        for (Position[] route : routes) {
            int currentIndex = steppingStoneRouteIndex(route, current);
            if (currentIndex < 0) {
                continue;
            }

            int destinationIndex = steppingStoneRouteIndex(route, destination);
            if (destinationIndex >= 0 && destinationIndex != currentIndex) {
                return route[currentIndex + (destinationIndex > currentIndex ? 1 : -1)];
            }

            int startDistance = GameUtil.getDistance(destination, route[0]);
            int endDistance = GameUtil.getDistance(destination, route[route.length - 1]);
            if (currentIndex == 0) {
                return endDistance + 2 < startDistance ? route[1] : null;
            }
            if (currentIndex == route.length - 1) {
                return startDistance + 2 < endDistance ? route[route.length - 2] : null;
            }
            return endDistance < startDistance ? route[currentIndex + 1] : route[currentIndex - 1];
        }

        Position bestApproach = null;
        int bestScore = Integer.MAX_VALUE;
        for (Position[] route : routes) {
            int last = route.length - 1;
            int destinationIndex = steppingStoneRouteIndex(route, destination);

            // A target already on the stones is always worth pursuing through
            // whichever end gives the shorter chase.
            if (destinationIndex > 0 && destinationIndex < last) {
                int startApproach = GameUtil.getDistance(current, route[0]);
                int endApproach = GameUtil.getDistance(current, route[last]);
                int startScore = startApproach + destinationIndex;
                int endScore = endApproach + (last - destinationIndex);
                if (startApproach <= 12 && startScore < bestScore) {
                    bestApproach = route[0];
                    bestScore = startScore;
                }
                if (endApproach <= 12 && endScore < bestScore) {
                    bestApproach = route[last];
                    bestScore = endScore;
                }
                continue;
            }

            Position[] starts = new Position[]{route[0], route[last]};
            Position[] ends = new Position[]{route[last], route[0]};
            for (int direction = 0; direction < 2; ++direction) {
                int approachDistance = GameUtil.getDistance(current, starts[direction]);
                int destinationExitDistance = GameUtil.getDistance(destination, ends[direction]);
                int destinationEntryDistance = GameUtil.getDistance(destination, starts[direction]);
                if (approachDistance > 10
                        || destinationExitDistance > 18
                        || destinationExitDistance + 3 >= destinationEntryDistance) {
                    continue;
                }

                int score = approachDistance + last + destinationExitDistance;
                if (score < bestScore) {
                    bestScore = score;
                    bestApproach = starts[direction];
                }
            }
        }
        return bestApproach;
    }

    public static boolean jumpSteppingStone(Player player, Position destination) {
        if (player == null || destination == null || player.isMovementLocked()
                || player.getPosition().getPlane() != 0 || destination.getPlane() != 0
                || !isInGame(player)) {
            return false;
        }

        Position current = player.getPosition();
        if (!areConsecutiveSteppingStoneTiles(current, destination)) {
            return false;
        }

        player.getMovementQueue().clear();
        player.getUpdateState().setAnimation(STEPPING_STONE_JUMP_ANIMATION);
        player.moveTo(new Position(destination.getX(), destination.getY(), 0));
        player.getMovementQueue().clearMovementActions();
        return true;
    }

    private static Position[] steppingStoneRoute(int routeVariant) {
        if (routeVariant == 1) {
            return SOUTHWEST_STEPPING_STONE_ROUTE;
        }
        if (routeVariant == 2) {
            return NORTHEAST_STEPPING_STONE_ROUTE;
        }
        return null;
    }

    private static boolean isSteppingStoneTile(Position position) {
        return isInteriorSteppingStoneTile(SOUTHWEST_STEPPING_STONE_ROUTE, position)
                || isInteriorSteppingStoneTile(NORTHEAST_STEPPING_STONE_ROUTE, position);
    }

    private static boolean isInteriorSteppingStoneTile(Position[] route, Position position) {
        int index = steppingStoneRouteIndex(route, position);
        return index > 0 && index < route.length - 1;
    }

    private static boolean areConsecutiveSteppingStoneTiles(Position first, Position second) {
        return areConsecutiveInRoute(SOUTHWEST_STEPPING_STONE_ROUTE, first, second)
                || areConsecutiveInRoute(NORTHEAST_STEPPING_STONE_ROUTE, first, second);
    }

    private static boolean areConsecutiveInRoute(Position[] route, Position first, Position second) {
        int firstIndex = steppingStoneRouteIndex(route, first);
        int secondIndex = steppingStoneRouteIndex(route, second);
        return firstIndex >= 0 && secondIndex >= 0 && Math.abs(firstIndex - secondIndex) == 1;
    }

    private static int steppingStoneRouteIndex(Position[] route, Position position) {
        if (route == null || position == null || position.getPlane() != 0) {
            return -1;
        }
        for (int index = 0; index < route.length; ++index) {
            Position routePosition = route[index];
            if (routePosition.getX() == position.getX()
                    && routePosition.getY() == position.getY()) {
                return index;
            }
        }
        return -1;
    }

    public static boolean isCastleWallCrossLevelPair(Player first, Player second) {
        if (first == null || second == null
                || !isInGame(first) || !isInGame(second)
                || !areOpponents(first, second)) {
            return false;
        }

        Position firstPosition = first.getPosition();
        Position secondPosition = second.getPosition();
        if (!((firstPosition.getPlane() == 1 && secondPosition.getPlane() == 0)
                || (firstPosition.getPlane() == 0 && secondPosition.getPlane() == 1))) {
            return false;
        }

        Position wallPosition = firstPosition.getPlane() == 1 ? firstPosition : secondPosition;
        Position groundPosition = firstPosition.getPlane() == 0 ? firstPosition : secondPosition;
        if (!isCastleBattlementPosition(wallPosition)) {
            return false;
        }

        return GameUtil.getDistance(wallPosition, groundPosition) <= 15;
    }

    public static boolean canBotTargetAcrossCastleLevels(Player attacker, Player target) {
        return attacker != null
                && attacker.botEnabled
                && attacker.botPrimaryCombatStyle != 0
                && isCastleWallCrossLevelPair(attacker, target);
    }

    public static boolean canBotAttackAcrossCastleLevels(Player attacker, Player target) {
        return attacker != null
                && attacker.botEnabled
                && attacker.botActiveCombatStyle != 0
                && isCastleWallCrossLevelPair(attacker, target);
    }

    public static boolean canBotAttackAcrossCastleLevels(Player attacker, Player target,
                                                          CombatType combatType) {
        return attacker != null
                && attacker.botEnabled
                && (combatType == CombatType.RANGED || combatType == CombatType.MAGIC)
                && isCastleWallCrossLevelPair(attacker, target);
    }

    public static boolean isCastleWallCrossLevelBotCombatPair(Player first, Player second) {
        if (!isCastleWallCrossLevelPair(first, second)) {
            return false;
        }
        return first.botEnabled && first.botPrimaryCombatStyle != 0
                || second.botEnabled && second.botPrimaryCombatStyle != 0;
    }

    public static int getBotCastleWallEngageRange(Player bot) {
        if (bot == null || !bot.botEnabled) {
            return 0;
        }
        return bot.botPrimaryCombatStyle == 2 ? 10
                : bot.botPrimaryCombatStyle == 1 ? 7 : 0;
    }

    public static boolean isCastleBattlementPosition(Position position) {
        if (position == null || position.getPlane() != 1) {
            return false;
        }

        int x = position.getX();
        int y = position.getY();
        boolean saradominCastle = x >= 2415 && x <= 2431
                && y >= 3072 && y <= 3083;
        boolean zamorakCastle = x >= 2368 && x <= 2384
                && y >= 3124 && y <= 3135;
        return saradominCastle || zamorakCastle;
    }

    public static Position getStairTraversalApproach(Player player, int objectId,
                                                    int objectX, int objectY) {
        if (player == null) {
            return null;
        }
        int plane = player.getPosition().getPlane();

        // Saradomin ground stairs.
        if (objectId == 4419 && objectX == 2417 && objectY == 3074 && plane == 0) {
            Position outside = new Position(2416, 3074, 0);
            Position inside = new Position(2417, 3077, 0);
            return nearestPosition(player.getPosition(), outside, inside);
        }

        // Saradomin stairs up.
        if (objectId == 4417) {
            if (objectX == 2419 && objectY == 3078 && plane == 0) {
                return new Position(2419, 3077, 0);
            }
            if (objectX == 2428 && objectY == 3081 && plane == 1) {
                return new Position(2427, 3081, 1);
            }
            if (objectX == 2425 && objectY == 3074 && plane == 2) {
                return new Position(2425, 3077, 2);
            }
        }

        // Shared down-stair object.
        if (objectId == 4415) {
            if (objectX == 2419 && objectY == 3080 && plane == 1) {
                return new Position(2420, 3080, 1);
            }
            if (objectX == 2430 && objectY == 3081 && plane == 2) {
                return new Position(2430, 3080, 2);
            }
            if (objectX == 2425 && objectY == 3074 && plane == 3) {
                return new Position(2426, 3074, 3);
            }
            if (objectX == 2380 && objectY == 3127 && plane == 1) {
                return new Position(2379, 3127, 1);
            }
            if (objectX == 2369 && objectY == 3126 && plane == 2) {
                return new Position(2369, 3127, 2);
            }
            if (objectX == 2374 && objectY == 3133 && plane == 3) {
                return new Position(2373, 3133, 3);
            }
        }

        // Zamorak ground stairs.
        if (objectId == 4420 && objectX == 2382 && objectY == 3131 && plane == 0) {
            Position outside = new Position(2383, 3133, 0);
            Position inside = new Position(2382, 3130, 0);
            return nearestPosition(player.getPosition(), outside, inside);
        }

        // Zamorak stairs up.
        if (objectId == 4418) {
            if (objectX == 2380 && objectY == 3127 && plane == 0) {
                return new Position(2380, 3130, 0);
            }
            if (objectX == 2369 && objectY == 3126 && plane == 1) {
                return new Position(2372, 3126, 1);
            }
            if (objectX == 2374 && objectY == 3131 && plane == 2) {
                return new Position(2374, 3130, 2);
            }
        }
        return null;
    }

    public static boolean isAtStairTraversalApproach(Player player, int objectId,
                                                     int objectX, int objectY) {
        Position approach = getStairTraversalApproach(player, objectId, objectX, objectY);
        return approach != null && samePosition(player.getPosition(), approach);
    }

    private static Position nearestPosition(Position origin, Position first, Position second) {
        return GameUtil.getDistance(origin, first) <= GameUtil.getDistance(origin, second)
                ? first : second;
    }

    private static boolean samePosition(Position first, Position second) {
        return first != null && second != null
                && first.getX() == second.getX()
                && first.getY() == second.getY()
                && first.getPlane() == second.getPlane();
    }

    private static void moveThroughCastleWarsStairs(Player player, Position destination) {
        boolean planeChange = player.getPosition().getPlane() != destination.getPlane();
        player.moveTo(destination);
        if (planeChange && !player.isBot) {
            // Same-region height changes can leave the client using stale
            // staircase occlusion until the camera is moved manually.
            player.getPacketSender().resetCamera();
        }
    }

    private static boolean handleCastleWarsTraversal(Player player, int objectId, int objectX, int objectY) {
        int plane = player.getPosition().getPlane();
        Position stairApproach = getStairTraversalApproach(player, objectId, objectX, objectY);
        if (stairApproach != null && !samePosition(player.getPosition(), stairApproach)) {
            return false;
        }

        // Saradomin castle stairs.
        if (objectId == 4419 && objectX == 2417 && objectY == 3074 && plane == 0) {
            if (player.getPosition().getX() <= 2416) {
                moveThroughCastleWarsStairs(player, new Position(2417, 3077, 0));
            } else {
                moveThroughCastleWarsStairs(player, new Position(2416, 3074, 0));
            }
            return true;
        }
        if (objectId == 4417) {
            if (objectX == 2419 && objectY == 3078 && plane == 0) {
                moveThroughCastleWarsStairs(player, new Position(2420, 3080, 1));
                return true;
            }
            if (objectX == 2428 && objectY == 3081 && plane == 1) {
                moveThroughCastleWarsStairs(player, new Position(2430, 3080, 2));
                return true;
            }
            if (objectX == 2425 && objectY == 3074 && plane == 2) {
                moveThroughCastleWarsStairs(player, new Position(2426, 3074, 3));
                return true;
            }
        }
        if (objectId == 4415) {
            if (objectX == 2419 && objectY == 3080 && plane == 1) {
                moveThroughCastleWarsStairs(player, new Position(2419, 3077, 0));
                return true;
            }
            if (objectX == 2430 && objectY == 3081 && plane == 2) {
                moveThroughCastleWarsStairs(player, new Position(2427, 3081, 1));
                return true;
            }
            if (objectX == 2425 && objectY == 3074 && plane == 3) {
                moveThroughCastleWarsStairs(player, new Position(2425, 3077, 2));
                return true;
            }

            // Zamorak castle stairs.
            if (objectX == 2380 && objectY == 3127 && plane == 1) {
                moveThroughCastleWarsStairs(player, new Position(2380, 3130, 0));
                return true;
            }
            if (objectX == 2369 && objectY == 3126 && plane == 2) {
                moveThroughCastleWarsStairs(player, new Position(2372, 3126, 1));
                return true;
            }
            if (objectX == 2374 && objectY == 3133 && plane == 3) {
                moveThroughCastleWarsStairs(player, new Position(2374, 3130, 2));
                return true;
            }
        }

        // Zamorak castle stairs.
        if (objectId == 4420 && objectX == 2382 && objectY == 3131 && plane == 0) {
            if (player.getPosition().getX() >= 2383) {
                moveThroughCastleWarsStairs(player, new Position(2382, 3130, 0));
            } else {
                moveThroughCastleWarsStairs(player, new Position(2383, 3133, 0));
            }
            return true;
        }
        if (objectId == 4418) {
            if (objectX == 2380 && objectY == 3127 && plane == 0) {
                moveThroughCastleWarsStairs(player, new Position(2379, 3127, 1));
                return true;
            }
            if (objectX == 2369 && objectY == 3126 && plane == 1) {
                moveThroughCastleWarsStairs(player, new Position(2369, 3127, 2));
                return true;
            }
            if (objectX == 2374 && objectY == 3131 && plane == 2) {
                moveThroughCastleWarsStairs(player, new Position(2373, 3133, 3));
                return true;
            }
        }

        // Ladders between the ground floor and first floor outside the spawn rooms.
        if (objectId == 4911 && plane == 1) {
            if (objectX == 2421 && objectY == 3073) {
                player.getUpdateState().setAnimation(827);
                player.moveTo(new Position(2421, 3074, 0));
                return true;
            }
            if (objectX == 2378 && objectY == 3134) {
                player.getUpdateState().setAnimation(827);
                player.moveTo(new Position(2378, 3133, 0));
                return true;
            }
        }
        if (objectId == 1747 && plane == 0) {
            if (objectX == 2421 && objectY == 3073) {
                player.getUpdateState().setAnimation(828);
                player.moveTo(new Position(2421, 3074, 1));
                return true;
            }
            if (objectX == 2378 && objectY == 3134) {
                player.getUpdateState().setAnimation(828);
                player.moveTo(new Position(2378, 3133, 1));
                return true;
            }
        }

        // Castle-to-tunnel ladders.
        if (objectId == 4912 && plane == 0) {
            if (objectX == 2430 && objectY == 3082) {
                player.getUpdateState().setAnimation(827);
                player.moveTo(new Position(2430, 9482, 0));
                return true;
            }
            if (objectX == 2369 && objectY == 3125) {
                player.getUpdateState().setAnimation(827);
                player.moveTo(new Position(2369, 9525, 0));
                return true;
            }
        }
        if (objectId == 1757 && plane == 0) {
            if (objectX == 2430 && objectY == 9482) {
                player.getUpdateState().setAnimation(828);
                player.moveTo(new Position(2430, 3081, 0));
                return true;
            }
            if (objectX == 2369 && objectY == 9525) {
                player.getUpdateState().setAnimation(828);
                player.moveTo(new Position(2369, 3126, 0));
                return true;
            }
            if (objectX == 2400 && objectY == 9508) {
                player.getUpdateState().setAnimation(828);
                player.moveTo(new Position(2400, 3107, 0));
                return true;
            }
            if (objectX == 2399 && objectY == 9499) {
                player.getUpdateState().setAnimation(828);
                player.moveTo(new Position(2399, 3100, 0));
                return true;
            }
        }

        // Surface trapdoors to the central tunnels.
        if (objectId == 1568 && plane == 0) {
            player.getUpdateState().setAnimation(827);
            if (objectX == 2399 && objectY == 3099) {
                player.moveTo(new Position(2399, 9500, 0));
            } else if (objectX == 2400 && objectY == 3108) {
                player.moveTo(new Position(2400, 9507, 0));
            } else {
                return false;
            }
            return true;
        }

        return false;
    }

    public static void respawnPlayer(Player player) {
        Team team = gamePlayers.get(player);
        if (team == null) {
            return;
        }
        dropCarriedFlag(player);
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
        replacementOffers.clear();
        replacementOfferCooldowns.clear();

        saradominScore = 0;
        zamorakScore = 0;
        CastleWarsEngineeringManager.resetForGame();
        saradominFlagAtBase = true;
        zamorakFlagAtBase = true;
        saradominFlagHolder = null;
        zamorakFlagHolder = null;
        saradominDroppedFlag = null;
        zamorakDroppedFlag = null;
        gameInProgress = true;
        gameEndMillis = now + GAME_DURATION_SECONDS * 1000L;
        nextGameStartMillis = -1L;

        for (Map.Entry<Player, Team> entry : starters.entrySet()) {
            Player player = entry.getKey();
            Team team = entry.getValue();
            if (!isOnline(player)) {
                continue;
            }
            clearWaitingRoomGodTransformation(player);
            gamePlayers.put(player, team);
            if (!isWearingTeamColours(player, team)) {
                equipTeamColours(player, team);
            }
            setCastleWarsAttackOption(player, true);
            moveToTeamSpawn(player, team);
            player.getPacketSender().sendGameMessage("The Castle Wars game has begun!");
            updateGameInterface(player, now);
        }

        gameTeamCapacity = Math.max(
                getGamePlayerCountInternal(Team.SARADOMIN),
                getGamePlayerCountInternal(Team.ZAMORAK));
    }

    private static void endGame(long now) {
        int saradominReward = saradominScore > zamorakScore ? 2 : saradominScore == zamorakScore ? 1 : 0;
        int zamorakReward = zamorakScore > saradominScore ? 2 : saradominScore == zamorakScore ? 1 : 0;

        if (saradominScore > zamorakScore) {
            recordTeamScoreboardVictory(Team.SARADOMIN);
        } else if (zamorakScore > saradominScore) {
            recordTeamScoreboardVictory(Team.ZAMORAK);
        }

        returnDroppedFlagToBase(Team.SARADOMIN);
        returnDroppedFlagToBase(Team.ZAMORAK);

        IdentityHashMap<Player, Team> finishers = new IdentityHashMap<Player, Team>(gamePlayers);
        gamePlayers.clear();
        closeAllReplacementOfferInterfaces();
        replacementOffers.clear();
        replacementOfferCooldowns.clear();
        gameTeamCapacity = 0;
        gameInProgress = false;
        gameEndMillis = -1L;

        for (Map.Entry<Player, Team> entry : finishers.entrySet()) {
            Player player = entry.getKey();
            Team team = entry.getValue();
            if (!isOnline(player)) {
                continue;
            }

            recordPlayerScoreboardResult(player, team);
            returnCarriedFlagToBase(player);
            setCastleWarsAttackOption(player, false);
            removeTeamColours(player);
            removeBandages(player);
            CastleWarsEngineeringManager.cleanupPlayerSupplies(player);
            clearCastleWarsInterface(player);
            clearFlagHint(player);

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

        CastleWarsEngineeringManager.cleanupAfterGame();
        nextGameStartMillis = hasMinimumPlayersToStartInternal()
            ? now + WAITING_DURATION_SECONDS * 1000L
            : -1L;
    }

    private static void applyWaitingRoomGodTransformation(Player player, int portalId) {
        int transformationId = -1;
        if (portalId == SARADOMIN_PORTAL_ID) {
            if (hasGodEquipment(player, God.GUTHIX) || hasGodEquipment(player, God.ZAMORAK)) {
                transformationId = SARADOMIN_RABBIT_TRANSFORMATION_ID;
            }
        } else if (portalId == GUTHIX_PORTAL_ID) {
            if (hasGodEquipment(player, God.SARADOMIN) || hasGodEquipment(player, God.ZAMORAK)) {
                transformationId = GUTHIX_SHEEP_TRANSFORMATION_ID;
            }
        } else if (portalId == ZAMORAK_PORTAL_ID) {
            if (hasGodEquipment(player, God.SARADOMIN) || hasGodEquipment(player, God.GUTHIX)) {
                transformationId = ZAMORAK_IMP_TRANSFORMATION_ID;
            }
        }

        if (transformationId > 0) {
            player.npcTransformationId = transformationId;
            player.setAppearanceUpdateRequired(true);
        }
    }

    private static boolean hasGodEquipment(Player player, God god) {
        ItemStack[] equipmentItems = player.getEquipmentManager().getContainer().getItems();
        for (ItemStack item : equipmentItems) {
            if (item == null || item.getDefinition() == null || item.getDefinition().getName() == null) {
                continue;
            }

            String itemName = item.getDefinition().getName().toLowerCase();
            if (god == God.SARADOMIN) {
                if (itemName.contains("saradomin")
                        || itemName.startsWith("holy symbol")
                        || itemName.startsWith("holy book")) {
                    return true;
                }
            } else if (god == God.GUTHIX) {
                if (itemName.contains("guthix")
                        || itemName.startsWith("book of balance")
                        || itemName.startsWith("void knight")) {
                    return true;
                }
            } else if (itemName.contains("zamorak")
                    || itemName.startsWith("unholy symbol")
                    || itemName.startsWith("unholy book")) {
                return true;
            }
        }
        return false;
    }

    private static void clearWaitingRoomGodTransformation(Player player) {
        if (player == null) {
            return;
        }
        int transformationId = player.npcTransformationId;
        if (transformationId != GUTHIX_SHEEP_TRANSFORMATION_ID
                && transformationId != SARADOMIN_RABBIT_TRANSFORMATION_ID
                && transformationId != ZAMORAK_IMP_TRANSFORMATION_ID) {
            return;
        }
        player.npcTransformationId = -1;
        player.setAppearanceUpdateRequired(true);
    }

    private static void equipTeamColours(Player player, Team team) {
        int hoodId = team == Team.SARADOMIN ? SARADOMIN_HOOD_ID : ZAMORAK_HOOD_ID;
        int cloakId = team == Team.SARADOMIN ? SARADOMIN_CLOAK_ID : ZAMORAK_CLOAK_ID;
        player.getEquipmentManager().getContainer().setItem(0, new ItemStack(hoodId));
        player.getEquipmentManager().getContainer().setItem(1, new ItemStack(cloakId));
        player.getEquipmentManager().refresh();
        player.setAppearanceUpdateRequired(true);
    }

    private static void setCastleWarsAttackOption(Player player, boolean enabled) {
        if (player == null || player.isBot) {
            return;
        }
        player.getPacketSender().sendPlayerOption(enabled ? "Attack" : "null", 1, false);
    }

    private static void removeTemporaryCastleWarsInventoryItems(Player player) {
        int[] temporaryItemIds = new int[]{
                SARADOMIN_HOOD_ID,
                SARADOMIN_CLOAK_ID,
                ZAMORAK_HOOD_ID,
                ZAMORAK_CLOAK_ID,
                SARADOMIN_FLAG_ID,
                ZAMORAK_FLAG_ID
        };
        for (int itemId : temporaryItemIds) {
            int amount = player.getInventoryManager().getItemAmount(itemId);
            if (amount > 0) {
                player.getInventoryManager().removeItem(new ItemStack(itemId, amount));
            }
        }
    }

    private static void removeBandages(Player player) {
        int amount = player.getInventoryManager().getItemAmount(4049);
        if (amount > 0) {
            player.getInventoryManager().removeItem(new ItemStack(4049, amount));
        }
    }

    private static boolean isWearingTeamColours(Player player, Team team) {
        int expectedHood = team == Team.SARADOMIN ? SARADOMIN_HOOD_ID : ZAMORAK_HOOD_ID;
        int expectedCloak = team == Team.SARADOMIN ? SARADOMIN_CLOAK_ID : ZAMORAK_CLOAK_ID;
        return player.getEquipmentManager().getItemIdAtSlot(0) == expectedHood
                && player.getEquipmentManager().getItemIdAtSlot(1) == expectedCloak;
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

    public static void moveBotToUndergroundEntrance(Player player, Team team) {
        if (player == null || team == null) {
            return;
        }
        player.getMovementQueue().reset();
        player.resetInteractionState();
        player.resetCombatState();
        if (team == Team.SARADOMIN) {
            player.moveTo(new Position(2430, 3081, 0));
        } else {
            player.moveTo(new Position(2369, 3126, 0));
        }
    }

    public static void moveBotToGroundBattlefield(Player player, Team team) {
        if (player == null || team == null) {
            return;
        }
        player.getMovementQueue().reset();
        player.resetInteractionState();
        player.resetCombatState();
        if (team == Team.SARADOMIN) {
            player.moveTo(new Position(2416 + GameUtil.randomInt(3), 3073 + GameUtil.randomInt(3), 0));
        } else {
            player.moveTo(new Position(2381 + GameUtil.randomInt(3), 3132 + GameUtil.randomInt(3), 0));
        }
    }

    private static void moveToTeamSpawn(Player player, Team team) {
        if (team == Team.SARADOMIN) {
            player.moveTo(new Position(2424 + GameUtil.randomInt(5), 3075 + GameUtil.randomInt(4), 1));
        } else {
            player.moveTo(new Position(2370 + GameUtil.randomInt(5), 3128 + GameUtil.randomInt(4), 1));
        }
    }

    public static boolean isAllowedLobbyPortalInventoryItem(Player player, ItemStack item) {
        if (item == null) {
            return true;
        }
        if (item.getDefinition().getEquipmentSlot() >= 0) {
            return true;
        }
        PotionHandler potionHandler = new PotionHandler(player);
        if (potionHandler.selectPotionForItemId(item.getId())) {
            return true;
        }
        String itemName = item.getDefinition().getName();
        return itemName != null && itemName.toLowerCase().endsWith(" rune");
    }

    private static boolean hasRestrictedInventoryItems(Player player) {
        ItemStack[] inventoryItems = player.getInventoryManager().getContainer().getItems();
        for (ItemStack item : inventoryItems) {
            if (!isAllowedLobbyPortalInventoryItem(player, item)) {
                return true;
            }
        }
        return false;
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

    private static int getGamePlayerCountInternal(Team team) {
        int count = 0;
        for (Team gameTeam : gamePlayers.values()) {
            if (gameTeam == team) {
                ++count;
            }
        }
        return count;
    }

    private static boolean hasReplacementVacancy(Team team) {
        return gameInProgress
                && gameTeamCapacity > 0
                && getGamePlayerCountInternal(team) < gameTeamCapacity;
    }

    private static void processReplacementOffers(long now) {
        Iterator<Map.Entry<Player, Long>> cooldownIterator = replacementOfferCooldowns.entrySet().iterator();
        while (cooldownIterator.hasNext()) {
            Map.Entry<Player, Long> entry = cooldownIterator.next();
            if (!waitingPlayers.containsKey(entry.getKey()) || now >= entry.getValue()) {
                cooldownIterator.remove();
            }
        }

        Iterator<Map.Entry<Player, ReplacementOffer>> offerIterator = replacementOffers.entrySet().iterator();
        while (offerIterator.hasNext()) {
            Map.Entry<Player, ReplacementOffer> entry = offerIterator.next();
            Player player = entry.getKey();
            ReplacementOffer offer = entry.getValue();
            boolean expired = now >= offer.expiresAtMillis;
            boolean valid = isOnline(player)
                    && waitingPlayers.get(player) == offer.team
                    && !expired
                    && hasReplacementVacancy(offer.team);
            if (valid) {
                continue;
            }

            offerIterator.remove();
            if (isOnline(player) && waitingPlayers.get(player) == offer.team) {
                if (expired) {
                    replacementOfferCooldowns.put(
                            player,
                            now + REPLACEMENT_OFFER_DURATION_SECONDS * 1000L);
                }
                closeReplacementOfferInterface(player);
                updateWaitingRoomInterface(player, now);
            }
        }

        offerReplacementPlayers(Team.SARADOMIN, now);
        offerReplacementPlayers(Team.ZAMORAK, now);
    }

    private static void offerReplacementPlayers(Team team, long now) {
        int reserved = 0;
        for (ReplacementOffer offer : replacementOffers.values()) {
            if (offer.team == team) {
                ++reserved;
            }
        }

        int vacancies = gameTeamCapacity - getGamePlayerCountInternal(team) - reserved;
        if (vacancies <= 0) {
            return;
        }

        IdentityHashMap<Player, Team> candidates = new IdentityHashMap<Player, Team>(waitingPlayers);
        for (Map.Entry<Player, Team> entry : candidates.entrySet()) {
            if (vacancies <= 0) {
                break;
            }

            Player player = entry.getKey();
            Long cooldownUntil = replacementOfferCooldowns.get(player);
            if (entry.getValue() != team
                    || replacementOffers.containsKey(player)
                    || (cooldownUntil != null && now < cooldownUntil)
                    || !isOnline(player)) {
                continue;
            }

            if (player.isBot) {
                joinReplacementPlayer(player, team, now);
            } else {
                replacementOffers.put(player, new ReplacementOffer(
                        team,
                        now + REPLACEMENT_OFFER_DURATION_SECONDS * 1000L));
                player.getDialogueManager().showTwoOptionsWithTitle(
                        "A place is available on the " + getTeamName(team) + " team.",
                        "Join the game.",
                        "Stay in the waiting room.");
                player.getPacketSender().sendGameMessage(
                        "A place has become available in the current Castle Wars game.");
            }
            --vacancies;
        }
    }

    private static void joinReplacementPlayer(Player player, Team team, long now) {
        if (waitingPlayers.remove(player) == null) {
            return;
        }

        replacementOffers.remove(player);
        replacementOfferCooldowns.remove(player);
        clearWaitingRoomGodTransformation(player);
        gamePlayers.put(player, team);
        if (!isWearingTeamColours(player, team)) {
            equipTeamColours(player, team);
        }
        setCastleWarsAttackOption(player, true);
        player.resetCombatState();
        moveToTeamSpawn(player, team);
        player.getPacketSender().sendGameMessage(
                "You join the ongoing Castle Wars game for the " + getTeamName(team) + " team.");
        updateGameInterface(player, now);
    }

    private static void cleanupWaitingPlayers() {
        Iterator<Map.Entry<Player, Team>> iterator = waitingPlayers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, Team> entry = iterator.next();
            Player player = entry.getKey();
            if (!isOnline(player)) {
                iterator.remove();
                continue;
            }
            if (!isInWaitingRoom(player, entry.getValue())) {
                clearWaitingRoomGodTransformation(player);
                iterator.remove();
            }
        }
    }

    private static void cleanupGamePlayers() {
        Iterator<Map.Entry<Player, Team>> iterator = gamePlayers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, Team> entry = iterator.next();
            if (!isOnline(entry.getKey())) {
                returnCarriedFlagToBase(entry.getKey());
                iterator.remove();
            }
        }
    }

    private static void closeReplacementOfferInterface(Player player) {
        if (player != null
                && (player.isInterfaceIdOpen(2461) || player.isInterfaceIdOpen(2462))) {
            player.getPacketSender().closeInterfaces();
        }
    }

    private static void closeAllReplacementOfferInterfaces() {
        for (Player player : replacementOffers.keySet()) {
            closeReplacementOfferInterface(player);
        }
    }

    private static final class ReplacementOffer {
        private final Team team;
        private final long expiresAtMillis;

        private ReplacementOffer(Team team, long expiresAtMillis) {
            this.team = team;
            this.expiresAtMillis = expiresAtMillis;
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

    private static boolean isOccupiedWaitingTile(int x, int y, int plane, Player ignoredPlayer) {
        Player[] players = World.getPlayers();
        for (Player player : players) {
            if (player == null || player == ignoredPlayer) {
                continue;
            }
            Position position = player.getPosition();
            if (position.getX() == x && position.getY() == y && position.getPlane() == plane) {
                return true;
            }
        }
        return false;
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
            timerText = "Waiting for the other team...";
        }

        player.getPacketSender().showWalkableInterface(WAITING_INTERFACE_ID);
        player.getPacketSender().sendInterfaceText(timerText, WAITING_TIMER_TEXT_ID);
        player.getPacketSender().sendInterfaceText("", WAITING_ZAMORAK_TEXT_ID);
        player.getPacketSender().sendInterfaceText("", WAITING_SARADOMIN_TEXT_ID);
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
        player.getPacketSender().sendInterfaceText("Zamorak = " + zamorakScore, GAME_ZAMORAK_SCORE_TEXT_ID);
        player.getPacketSender().sendInterfaceText(saradominScore + " = Saradomin", GAME_SARADOMIN_SCORE_TEXT_ID);
        player.getPacketSender().sendInterfaceText(formatTime(Math.max(0L, gameEndMillis - now)), GAME_TIMER_TEXT_ID);
        player.getPacketSender().sendInterfaceText(getFlagStatusText(Team.ZAMORAK), GAME_ZAMORAK_FLAG_TEXT_ID);
        player.getPacketSender().sendInterfaceText(getFlagStatusText(Team.SARADOMIN), GAME_SARADOMIN_FLAG_TEXT_ID);
        Team team = gamePlayers.get(player);
        updateEnemyFlagHint(player, team);
        if (team != null) {
            player.getPacketSender().sendInterfaceText(
                    "Health " + CastleWarsEngineeringManager.getMainDoorHitpoints(team) + "%",
                    GAME_MAIN_GATE_TEXT_ID);
            player.getPacketSender().sendInterfaceText(
                    CastleWarsEngineeringManager.isSideDoorOpen(team) ? "Unlocked" : "Locked",
                    GAME_SIDE_DOOR_TEXT_ID);
            player.getPacketSender().sendInterfaceText(
                    CastleWarsEngineeringManager.isHomeTunnelCollapsed(team, 0) ? "Collapsed" : "Cleared",
                    GAME_TUNNEL_ONE_TEXT_ID);
            player.getPacketSender().sendInterfaceText(
                    CastleWarsEngineeringManager.isHomeTunnelCollapsed(team, 1) ? "Collapsed" : "Cleared",
                    GAME_TUNNEL_TWO_TEXT_ID);
            player.getPacketSender().sendInterfaceText(
                    CastleWarsEngineeringManager.isCatapultOperational(team) ? "Operational" : "Destroyed",
                    GAME_CATAPULT_TEXT_ID);
        }
    }

    private static void updateEnemyFlagHint(Player player, Team team) {
        if (team == null) {
            clearFlagHint(player);
            return;
        }

        Team enemyFlagTeam = team == Team.SARADOMIN ? Team.ZAMORAK : Team.SARADOMIN;
        if (isFlagAtBase(enemyFlagTeam)) {
            clearFlagHint(player);
            return;
        }

        Position target = getFlagHintPosition(enemyFlagTeam);
        if (target == null) {
            clearFlagHint(player);
            return;
        }

        int targetY = target.getY();
        if (targetY >= 9468 && targetY <= 9540) {
            targetY -= 6400;
        }
        player.getPacketSender().sendPositionHintIcon(target.getX(), targetY, 0, 2);
    }

    private static Position getFlagHintPosition(Team flagTeam) {
        Player holder = getFlagHolder(flagTeam);
        if (holder != null) {
            return holder.getPosition();
        }
        GroundItem droppedFlag = getDroppedFlag(flagTeam);
        return droppedFlag == null ? null : droppedFlag.getPosition();
    }

    private static void clearFlagHint(Player player) {
        player.getPacketSender().sendEntityHintIcon(0, 0);
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

    private enum God {
        SARADOMIN,
        GUTHIX,
        ZAMORAK
    }

    public enum Team {
        SARADOMIN,
        ZAMORAK
    }
}
