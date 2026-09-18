package com.rs2;

import com.rs2.model.player.Player;
import com.rs2.util.db.SqliteDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;

public final class HiscoresDatabase {
    private static final String[] SKILLS = {"Attack", "Defence", "Strength", "Hitpoints", "Range", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecraft"};
    private static Connection connection;

    private HiscoresDatabase() {
    }

    public static synchronized void connect() {
        try {
            SqliteDatabase.initialize();
            connection = SqliteDatabase.openConnection();
            System.out.println("Successfully connected to SQLite database.");
        } catch (Exception exception) {
            connection = null;
            exception.printStackTrace();
        }
    }

    public static synchronized void disconnect() {
        try {
            if (connection != null) connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            connection = null;
        }
    }

    public static synchronized boolean savePlayer(Player player) {
        if (connection == null || player.isBot && player.botMode != 4) return false;
        try {
            StringBuilder columns = new StringBuilder("playerName");
            StringBuilder placeholders = new StringBuilder("?");
            for (String skill : SKILLS) {
                columns.append(',').append(skill).append("lvl,").append(skill).append("xp");
                placeholders.append(",?,?");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement deleteSkills = connection.prepareStatement("DELETE FROM skills WHERE playerName = ?");
                 PreparedStatement deleteOverall = connection.prepareStatement("DELETE FROM skillsoverall WHERE playerName = ?");
                 PreparedStatement insertSkills = connection.prepareStatement("INSERT INTO skills (" + columns + ") VALUES (" + placeholders + ")");
                 PreparedStatement insertOverall = connection.prepareStatement("INSERT INTO skillsoverall (playerName, level, xp) VALUES (?, ?, ?)")) {
                deleteSkills.setString(1, player.getUsername());
                deleteSkills.executeUpdate();
                deleteOverall.setString(1, player.getUsername());
                deleteOverall.executeUpdate();
                insertSkills.setString(1, player.getUsername());
                int totalLevel = 0;
                double totalExperience = 0;
                for (int skill = 0; skill < SKILLS.length; skill++) {
                    int level = player.getSkillManager().getBaseLevel(skill);
                    double experience = player.getSkillManager().getExperience()[skill];
                    insertSkills.setInt(skill * 2 + 2, level);
                    insertSkills.setDouble(skill * 2 + 3, experience);
                    totalLevel += level;
                    totalExperience += experience;
                }
                insertSkills.executeUpdate();
                insertOverall.setString(1, player.getUsername());
                insertOverall.setInt(2, totalLevel);
                insertOverall.setDouble(3, totalExperience);
                insertOverall.executeUpdate();
                connection.commit();
            } catch (Exception exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
