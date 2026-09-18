package com.rs2.util;

import com.rs2.model.player.Player;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.Converter;
import com.rs2.util.db.DatabaseCallback;
import java.io.File;
import java.sql.ResultSet;

public final class ConverterUidLookupCallback
implements DatabaseCallback {
    private final File characterDirectory;
    private final Player player;
    private final String username;

    public ConverterUidLookupCallback(File file, Player player, String username) {
        this.characterDirectory = file;
        this.player = player;
        this.username = username;
    }

    @Override
    public final void onResult(ResultSet resultSet) throws java.sql.SQLException {
        try {
            try {
                StringBuilder stringBuilder = new StringBuilder("Converting player file [");
                int convertedCount = Converter.getConvertedCount() + 1;
                Converter.setConvertedCount(convertedCount);
                System.out.println(stringBuilder.append(convertedCount).append("/").append(this.characterDirectory.listFiles().length).append("]").toString());
                if (resultSet.next()) {
                    int intValue = resultSet.getInt("uid");
                    this.player.setReferenceId(intValue);
                    CharacterFileManager.loadPlayerFromFile("./data/characters/", this.player);
                    CharacterFileManager.savePlayer(this.player);
                } else {
                    System.out.println("User `" + this.username + "` doesn't exist");
                }
            }
            catch (Exception exception) {
                System.out.println("User `" + this.username + "` file is corrupt");
                Converter.markReadyForNextFile(true);
                return;
            }
        }
        finally {
            Converter.markReadyForNextFile(true);
        }
    }

    @Override
    public final void onException(Exception exception) {
        System.out.println("Issue with grabbing uid for player `" + this.username + "`");
        Converter.markReadyForNextFile(true);
    }
}

