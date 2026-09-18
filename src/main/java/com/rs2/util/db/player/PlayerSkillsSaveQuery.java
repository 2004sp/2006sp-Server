package com.rs2.util.db.player;

import com.rs2.util.db.DatabaseQuery;
import com.rs2.util.db.player.PlayerSaveQueryFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class PlayerSkillsSaveQuery
extends DatabaseQuery {
    private PlayerSaveQueryFactory factory;

    public PlayerSkillsSaveQuery(PlayerSaveQueryFactory playerSaveQueryFactory) {
        super("INSERT INTO prs06_skills (id, player_id, cur_attack, cur_defence, cur_strength, cur_hitpoints, cur_ranged, cur_prayer, cur_magic, cur_cooking, cur_woodcutting, cur_fletching, cur_fishing, cur_firemaking, cur_crafting, cur_smithing, cur_mining, cur_herblore, cur_agility, cur_thieving, cur_slayer, cur_farming, cur_runecrafting, exp_attack, exp_defence, exp_strength, exp_hitpoints, exp_ranged, exp_prayer, exp_magic, exp_cooking, exp_woodcutting, exp_fletching, exp_fishing, exp_firemaking, exp_crafting, exp_smithing, exp_mining, exp_herblore, exp_agility, exp_thieving, exp_slayer, exp_farming, exp_runecrafting) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT(id) DO UPDATE SET cur_attack = excluded.cur_attack, cur_defence = excluded.cur_defence, cur_strength = excluded.cur_strength, cur_hitpoints = excluded.cur_hitpoints, cur_ranged = excluded.cur_ranged, cur_prayer = excluded.cur_prayer, cur_magic = excluded.cur_magic, cur_cooking = excluded.cur_cooking, cur_woodcutting = excluded.cur_woodcutting, cur_fletching = excluded.cur_fletching, cur_fishing = excluded.cur_fishing, cur_firemaking = excluded.cur_firemaking, cur_thieving = excluded.cur_thieving, cur_slayer = excluded.cur_slayer, cur_farming = excluded.cur_farming, cur_runecrafting = excluded.cur_runecrafting, exp_attack = excluded.exp_attack, exp_defence = excluded.exp_defence, exp_strength = excluded.exp_strength, exp_hitpoints = excluded.exp_hitpoints, exp_ranged = excluded.exp_ranged, exp_prayer = excluded.exp_prayer, exp_magic = excluded.exp_magic, exp_cooking = excluded.exp_cooking, exp_woodcutting = excluded.exp_woodcutting, exp_fletching = excluded.exp_fletching, exp_fishing = excluded.exp_fishing, exp_firemaking = excluded.exp_firemaking, exp_thieving = excluded.exp_thieving, exp_slayer = excluded.exp_slayer, exp_farming = excluded.exp_farming, exp_runecrafting = excluded.exp_runecrafting");
        this.factory = playerSaveQueryFactory;
    }

    @Override
    public final ResultSet executeStatement(PreparedStatement preparedStatement) throws java.sql.SQLException {
        preparedStatement.setInt(1, PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
        preparedStatement.setInt(2, PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
        int index = 0;
        while (index <= 20) {
            preparedStatement.setInt(index + 3, PlayerSaveQueryFactory.getPlayer(this.factory).getSkillManager().getCurrentLevels()[index]);
            preparedStatement.setInt(index + 24, (int)PlayerSaveQueryFactory.getPlayer(this.factory).getSkillManager().getExperience()[index]);
            ++index;
        }
        preparedStatement.executeUpdate();
        return null;
    }
}

