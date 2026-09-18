package com.rs2.bot;

import com.rs2.model.Position;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;

public final class MinigameBotManager {
    private static final Position CASTLE_WARS_LOBBY = new Position(2440, 3089, 0);

    private MinigameBotManager() {
    }

    public static void startMinigameBot(BotPlayer botPlayer) {
        botPlayer.moveTo(new Position(CASTLE_WARS_LOBBY.getX(), CASTLE_WARS_LOBBY.getY(), CASTLE_WARS_LOBBY.getPlane()));
        CastleWarsManager.leaveWaitingRoom(botPlayer);
    }

    public static void joinCastleWars(BotPlayer botPlayer) {
        CastleWarsManager.handleLobbyPortal(botPlayer, CastleWarsManager.GUTHIX_PORTAL_ID);
    }
}
