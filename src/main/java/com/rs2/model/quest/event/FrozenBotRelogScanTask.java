package com.rs2.model.quest.event;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

public final class FrozenBotRelogScanTask
extends TickTask {
    public FrozenBotRelogScanTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int value2) {
        super(100);
    }

    @Override
    public final void execute() {
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;

        while (index < length) {
            Player player = playerArray[index];
            if (player != null
                && player.isBot
                && (player.botMode == 4 || player.botMode == 0)
                && player.hasBotStalled()) {
                System.out.println(
                    "Detected possibly frozen bot: "
                    + player.getUsername()
                    + " at: "
                    + ((Entity)player).getPosition()
                    + ". Teleporting to Lumbridge."
                );
                player.moveTo(new Position(ServerSettings.respawnX, ServerSettings.respawnY, ServerSettings.respawnPlane));
            }
            ++index;
        }
    }
}
