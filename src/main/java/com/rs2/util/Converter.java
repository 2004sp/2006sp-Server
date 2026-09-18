package com.rs2.util;

import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.util.ConverterUidLookupCallback;
import com.rs2.util.ConverterUidLookupQuery;
import com.rs2.util.db.DatabaseService;
import com.rs2.util.db.SqliteDatabase;
import java.io.File;
import java.util.Arrays;

public class Converter {
    private static int convertedCount = 0;
    private static volatile boolean readyForNextFile = true;

    public static void main(String[] stringValues2) {
        try {
            ServerSettings.sqlitePlayerSaveEnabled = true;
            File characterDirectory = new File("./data/characters/");
            SqliteDatabase.initialize();
            DatabaseService.setInstance(new DatabaseService(8, ServerSettings.databaseDriverClass, ServerSettings.databaseJdbcUrl, ServerSettings.databaseUsername, ServerSettings.databasePassword));
            File[] characterFiles = characterDirectory.listFiles();
            System.out.println("Preparing to convert " + characterFiles.length + " character files...");
            for (File characterFile : Arrays.asList(characterFiles)) {
                while (!readyForNextFile) {
                }
                readyForNextFile = false;
                String username = characterFile.getName().substring(0, characterFile.getName().indexOf(46)).toLowerCase();
                Player player = new Player(null);
                player.setUsername(username);
                ConverterUidLookupQuery converterUidLookupQuery = new ConverterUidLookupQuery("SELECT uid FROM `prs06_users` WHERE username = ?", username);
                DatabaseService.getInstance().submit(converterUidLookupQuery, new ConverterUidLookupCallback(characterDirectory, player, username));
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static void markReadyForNextFile(boolean enabled2) {
        readyForNextFile = true;
    }

    static int getConvertedCount() {
        return convertedCount;
    }

    static void setConvertedCount(int convertedCount) {
        convertedCount = convertedCount;
    }
}
