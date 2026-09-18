package com.rs2.bot;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.Position;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.util.GameUtil;

public final class MinigameBotManager {
    private static final Position CASTLE_WARS_LOBBY = new Position(2440, 3089, 0);

    private MinigameBotManager() {
    }

    public static void startMinigameBot(BotPlayer botPlayer) {
        randomizeCombatLevels(botPlayer);
        prepareCastleWarsEquipment(botPlayer);
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

    public static boolean joinCastleWars(BotPlayer botPlayer) {
        if (!botPlayer.isRegistered()) {
            return false;
        }
        if (CastleWarsManager.isWaitingPlayer(botPlayer) || CastleWarsManager.isInGame(botPlayer)) {
            return true;
        }

        prepareCastleWarsEquipment(botPlayer);
        placeAtCastleWarsLobby(botPlayer);
        return CastleWarsManager.handleLobbyPortal(botPlayer, CastleWarsManager.GUTHIX_PORTAL_ID);
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
