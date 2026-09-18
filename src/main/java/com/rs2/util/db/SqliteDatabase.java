package com.rs2.util.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class SqliteDatabase {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String URL = "jdbc:sqlite:data/server.db";

    private SqliteDatabase() {
    }

    public static Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
            statement.execute("PRAGMA busy_timeout = 5000");
            statement.execute("PRAGMA journal_mode = WAL");
        }
        return connection;
    }

    public static void initialize() throws Exception {
        File dataDirectory = new File("data");
        if (!dataDirectory.exists() && !dataDirectory.mkdirs()) throw new SQLException("Unable to create " + dataDirectory.getAbsolutePath());
        Class.forName(DRIVER);
        try (Connection connection = openConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS skills (playerName TEXT PRIMARY KEY, Attacklvl INTEGER, Attackxp REAL, Defencelvl INTEGER, Defencexp REAL, Strengthlvl INTEGER, Strengthxp REAL, Hitpointslvl INTEGER, Hitpointsxp REAL, Rangelvl INTEGER, Rangexp REAL, Prayerlvl INTEGER, Prayerxp REAL, Magiclvl INTEGER, Magicxp REAL, Cookinglvl INTEGER, Cookingxp REAL, Woodcuttinglvl INTEGER, Woodcuttingxp REAL, Fletchinglvl INTEGER, Fletchingxp REAL, Fishinglvl INTEGER, Fishingxp REAL, Firemakinglvl INTEGER, Firemakingxp REAL, Craftinglvl INTEGER, Craftingxp REAL, Smithinglvl INTEGER, Smithingxp REAL, Mininglvl INTEGER, Miningxp REAL, Herblorelvl INTEGER, Herblorexp REAL, Agilitylvl INTEGER, Agilityxp REAL, Thievinglvl INTEGER, Thievingxp REAL, Slayerlvl INTEGER, Slayerxp REAL, Farminglvl INTEGER, Farmingxp REAL, Runecraftlvl INTEGER, Runecraftxp REAL)");
            statement.execute("CREATE TABLE IF NOT EXISTS skillsoverall (playerName TEXT PRIMARY KEY, level INTEGER NOT NULL, xp REAL NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS prs06_users (uid INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE COLLATE NOCASE, world INTEGER NOT NULL DEFAULT 0)");
            statement.execute("CREATE TABLE IF NOT EXISTS prs06_players (id INTEGER PRIMARY KEY, username TEXT NOT NULL UNIQUE COLLATE NOCASE, x INTEGER DEFAULT 3222, y INTEGER DEFAULT 3222, z INTEGER DEFAULT 0, is_male INTEGER DEFAULT 0, is_auto_retaliate INTEGER DEFAULT 1, fight_mode INTEGER DEFAULT 0, brightness INTEGER DEFAULT 0, mouse_buttons INTEGER DEFAULT 0, chat_effects INTEGER DEFAULT 0, split_private_chat INTEGER DEFAULT 0, accept_aid INTEGER DEFAULT 0, music_volume INTEGER DEFAULT 0, effect_volume INTEGER DEFAULT 0, quest_points INTEGER DEFAULT 0, special INTEGER DEFAULT 100, energy INTEGER DEFAULT 100, is_running INTEGER DEFAULT 0, skull_timer INTEGER DEFAULT 0, pin TEXT DEFAULT 'na', appearance_0 INTEGER DEFAULT 0, appearance_1 INTEGER DEFAULT 0, appearance_2 INTEGER DEFAULT 0, appearance_3 INTEGER DEFAULT 0, appearance_4 INTEGER DEFAULT 0, appearance_5 INTEGER DEFAULT 0, appearance_6 INTEGER DEFAULT 0, color_0 INTEGER DEFAULT 0, color_1 INTEGER DEFAULT 0, color_2 INTEGER DEFAULT 0, color_3 INTEGER DEFAULT 0, color_4 INTEGER DEFAULT 0, tutorial_stage INTEGER DEFAULT 0, tutorial_progress INTEGER DEFAULT 0, ban_expires INTEGER DEFAULT 0, mute_expires INTEGER DEFAULT 0, changing_bankpin INTEGER DEFAULT 0, deleting_bankpin INTEGER DEFAULT 0, pin_append_year INTEGER DEFAULT 0, pin_append_date INTEGER DEFAULT 0, binding_neck_charge INTEGER DEFAULT 0, ring_of_forging_life INTEGER DEFAULT 0, ring_of_recoil_life INTEGER DEFAULT 0, slayer_master INTEGER DEFAULT 0, slayer_task TEXT DEFAULT '', task_amount INTEGER DEFAULT 0, using_ancients INTEGER DEFAULT 0, brimhaven_open INTEGER DEFAULT 0, killed_clue_attacker INTEGER DEFAULT 0)");
            statement.execute("CREATE TABLE IF NOT EXISTS prs06_contacts (id INTEGER PRIMARY KEY, player_id INTEGER NOT NULL, slot INTEGER NOT NULL, contact INTEGER NOT NULL, `ignore` INTEGER NOT NULL DEFAULT 0)");
            statement.execute("CREATE TABLE IF NOT EXISTS prs06_containers (id INTEGER PRIMARY KEY, container_id INTEGER NOT NULL, user_id INTEGER NOT NULL, item_id INTEGER NOT NULL, amount INTEGER NOT NULL, slot INTEGER NOT NULL, timer INTEGER NOT NULL DEFAULT 0)");
            statement.execute(createSkillsTableSql());
        }
    }

    private static String createSkillsTableSql() {
        String[] skills = {"attack", "defence", "strength", "hitpoints", "ranged", "prayer", "magic", "cooking", "woodcutting", "fletching", "fishing", "firemaking", "crafting", "smithing", "mining", "herblore", "agility", "thieving", "slayer", "farming", "runecrafting"};
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS prs06_skills (id INTEGER PRIMARY KEY, player_id INTEGER NOT NULL UNIQUE");
        for (String skill : skills) sql.append(", cur_").append(skill).append(" INTEGER NOT NULL DEFAULT 1");
        for (String skill : skills) sql.append(", exp_").append(skill).append(" REAL NOT NULL DEFAULT 0");
        return sql.append(')').toString();
    }
}
