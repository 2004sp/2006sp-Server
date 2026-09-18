package com.rs2.bot.combat;

import com.rs2.ServerSettings;
import com.rs2.bot.ClanWarsBotManager;
import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class BotCombatEscapeLogoutTask
extends TickTask {
    private final Player player;

    public BotCombatEscapeLogoutTask(int value2, Player player) {
        super(2);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.isRegistered()) {
            this.stop();
        }
        if (this.player.getPosition().getY() < 3520 || !this.player.isInWilderness()) {
            if (this.player.currentBotTask == null && !this.player.clanWarsBot) {
                BotCombatHelper.sellBotLootItems(this.player);
            }
            if (this.player.isBot) {
                if (this.player.currentBotTask == null) {
                    if (this.player.clanWarsBot) {
                        ClanWarsBotManager.hideClanWarsBot(this.player);
                        return;
                    }
                    BotCombatLoadoutManager.startCombatLoadoutBot(this.player);
                    return;
                }
                if (this.player.botMode != 4) {
                    GameplayHelper.startNextBotTask(this.player);
                    return;
                }
                System.out.println("Bot needs to escape combat: " + this.player.getUsername() + ", trying to fix by reseting and relogging.");
                this.player.currentBotTask = null;
                this.player.deferredBotTask = null;
                this.player.applyTeleportPosition(new Position(ServerSettings.respawnX, ServerSettings.respawnY, ServerSettings.respawnPlane));
                World.logoutBotAndScheduleRelogin(this.player);
                return;
            }
            if (!this.player.getRecentCombatTimer().hasElapsed()) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You have to wait 10 seconds after combat in order to logout.");
                return;
            }
            if (this.player.getSingleCombatTimer().hasElapsed()) {
                Player player = this.player;
                player.packetSender.sendLogout();
                this.player.disconnect();
                return;
            }
        } else {
            BotCombatEscapeHandler.processBotCombatEscape(this.player);
        }
    }
}

