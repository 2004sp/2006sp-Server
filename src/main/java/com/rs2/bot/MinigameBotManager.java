package com.rs2.bot;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.Position;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.util.GameUtil;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public final class MinigameBotManager {
    private static final Position CASTLE_WARS_LOBBY = new Position(2440, 3089, 0);
    private static final Set<BotPlayer> spreadWaitingBots =
        Collections.newSetFromMap(new IdentityHashMap<BotPlayer, Boolean>());

    private static final String[] WAITING_CHAT = new String[]{
        "gl all",
        "how long left",
        "ready",
        "lets go",
        "good luck",
        "rush mid",
        "def flag",
        "who got flag",
        "lol",
        "nice"
    };

    private static final String[] GAME_CHAT = new String[]{
        "rush mid",
        "get the flag",
        "def base",
        "nice one",
        "incoming",
        "help mid",
        "push",
        "lol",
        "gf",
        "go go go"
    };

    private MinigameBotManager() {
    }

    public static void startMinigameBot(BotPlayer botPlayer) {
        randomizeCombatLevels(botPlayer);
        preparePvpLoadout(botPlayer);
        placeAtCastleWarsLobby(botPlayer);
        CastleWarsManager.leaveWaitingRoom(botPlayer);
    }

    public static void placeAtCastleWarsLobby(BotPlayer botPlayer) {
        botPlayer.moveTo(new Position(
            CASTLE_WARS_LOBBY.getX(),
            CASTLE_WARS_LOBBY.getY(),
            CASTLE_WARS_LOBBY.getPlane()
        ));
    }

    public static void processMinigameBot(BotPlayer botPlayer) {
        joinCastleWars(botPlayer);

        if (!botPlayer.isRegistered() || botPlayer.isDead()) {
            return;
        }
        if (!CastleWarsManager.isWaitingPlayer(botPlayer) && !CastleWarsManager.isInGame(botPlayer)) {
            return;
        }

        // This task runs every two game ticks. A 1/180 roll keeps a large
        // Castle Wars population chatty without flooding public chat.
        if (GameUtil.randomInt(180) != 0) {
            return;
        }

        CastleWarsManager.Team team = CastleWarsManager.getTeam(botPlayer);
        if (GameUtil.randomInt(5) == 0 && team != null) {
            botPlayer.queuePublicChatMessage(team == CastleWarsManager.Team.SARADOMIN ? "sara ftw" : "zammy ftw");
            return;
        }

        String[] chat = CastleWarsManager.isInGame(botPlayer) ? GAME_CHAT : WAITING_CHAT;
        botPlayer.queuePublicChatMessage(chat[GameUtil.randomInt(chat.length)]);
    }

    public static boolean joinCastleWars(BotPlayer botPlayer) {
        if (!botPlayer.isRegistered()) {
            return false;
        }
        if (CastleWarsManager.isInGame(botPlayer)) {
            spreadWaitingBots.remove(botPlayer);
            return true;
        }
        if (CastleWarsManager.isWaitingPlayer(botPlayer)) {
            if (spreadWaitingBots.add(botPlayer)) {
                CastleWarsManager.spreadWaitingPlayer(botPlayer);
            }
            return true;
        }

        prepareCastleWarsEquipment(botPlayer);
        placeAtCastleWarsLobby(botPlayer);
        boolean handled = CastleWarsManager.handleLobbyPortal(botPlayer, CastleWarsManager.GUTHIX_PORTAL_ID);
        if (handled && CastleWarsManager.isWaitingPlayer(botPlayer)) {
            CastleWarsManager.spreadWaitingPlayer(botPlayer);
            spreadWaitingBots.add(botPlayer);
        }
        return handled;
    }

    private static void preparePvpLoadout(BotPlayer botPlayer) {
        BotCombatLoadoutManager.selectCombatStyleFromStats(botPlayer, false);
        BotCombatLoadoutManager.prepareCombatLoadout(botPlayer, false);
        prepareCastleWarsEquipment(botPlayer);
    }

    private static void prepareCastleWarsEquipment(BotPlayer botPlayer) {
        boolean changed = false;
        if (botPlayer.getEquipmentManager().getContainer().getItemAt(0) != null) {
            botPlayer.getEquipmentManager().getContainer().setItem(0, null);
            changed = true;
        }
        if (botPlayer.getEquipmentManager().getContainer().getItemAt(1) != null) {
            botPlayer.getEquipmentManager().getContainer().setItem(1, null);
            changed = true;
        }
        if (changed) {
            botPlayer.getEquipmentManager().refresh();
            botPlayer.setAppearanceUpdateRequired(true);
        }
    }

    private static void randomizeCombatLevels(BotPlayer botPlayer) {
        BotCombatHelper.setBotSkillLevel(botPlayer, 0, 10 + GameUtil.randomInt(90));
        BotCombatHelper.setBotSkillLevel(botPlayer, 1, 10 + GameUtil.randomInt(90));
        BotCombatHelper.setBotSkillLevel(botPlayer, 2, 10 + GameUtil.randomInt(90));
        BotCombatHelper.setBotSkillLevel(botPlayer, 3, 30 + GameUtil.randomInt(70));
        BotCombatHelper.setBotSkillLevel(botPlayer, 4, 10 + GameUtil.randomInt(90));
        BotCombatHelper.setBotSkillLevel(botPlayer, 5, 1 + GameUtil.randomInt(70));
        BotCombatHelper.setBotSkillLevel(botPlayer, 6, 10 + GameUtil.randomInt(90));
        botPlayer.getSkillManager().refreshAllSkills();
    }
}
