package com.rs2.util;

import com.rs2.HiscoresDatabase;
import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.util.CharacterFileManager;

public final class SaveAllPlayersShutdownHook
extends Thread {
    @Override
    public final void run() {
        if (Server.serverStatus != 0) {
            CharacterFileManager.saveAllPlayers();

            if (ServerSettings.sqliteHiscoresEnabled) {
                HiscoresDatabase.disconnect();
            }

            System.out.println("Saved all players.");
        }
    }
}

