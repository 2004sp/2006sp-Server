package com.rs2.util.db.player;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.util.db.DatabaseQuery;
import com.rs2.util.db.player.PlayerSaveQueryFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class PlayerProfileSaveQuery
extends DatabaseQuery {
    private PlayerSaveQueryFactory factory;

    public PlayerProfileSaveQuery(PlayerSaveQueryFactory playerSaveQueryFactory) {
        super("INSERT INTO prs06_players (id, username, x, y, z, appearance_0, appearance_1, appearance_2, appearance_3, appearance_4, appearance_5, appearance_6, color_0, color_1, color_2, color_3, color_4, pin, tutorial_stage, tutorial_progress, is_male, ban_expires, mute_expires, changing_bankpin, deleting_bankpin, pin_append_year, pin_append_date, binding_neck_charge, ring_of_forging_life, ring_of_recoil_life, slayer_master, slayer_task, task_amount, using_ancients, brimhaven_open, killed_clue_attacker) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT(id) DO UPDATE SET x = excluded.x, y = excluded.y, z = excluded.z, appearance_0 = excluded.appearance_0, appearance_1 = excluded.appearance_1, appearance_2 = excluded.appearance_2, appearance_3 = excluded.appearance_3, appearance_4 = excluded.appearance_4, appearance_5 = excluded.appearance_5, appearance_6 = excluded.appearance_6, color_0 = excluded.color_0, color_1 = excluded.color_1, color_2 = excluded.color_2, color_3 = excluded.color_3, color_4 = excluded.color_4, pin = excluded.pin, tutorial_stage = excluded.tutorial_stage, tutorial_progress = excluded.tutorial_progress, is_male = excluded.is_male, ban_expires = excluded.ban_expires, mute_expires = excluded.mute_expires, changing_bankpin = excluded.changing_bankpin, deleting_bankpin = excluded.deleting_bankpin,pin_append_year = excluded.pin_append_year, pin_append_date = excluded.pin_append_date, binding_neck_charge = excluded.binding_neck_charge, ring_of_forging_life = excluded.ring_of_forging_life, ring_of_recoil_life = excluded.ring_of_recoil_life, slayer_master = excluded.slayer_master, slayer_task = excluded.slayer_task, task_amount = excluded.task_amount, using_ancients = excluded.using_ancients, brimhaven_open = excluded.brimhaven_open, killed_clue_attacker = excluded.killed_clue_attacker");
        this.factory = playerSaveQueryFactory;
    }

    @Override
    public final ResultSet executeStatement(PreparedStatement preparedStatement) throws java.sql.SQLException {
        preparedStatement.setInt(1, PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
        preparedStatement.setString(2, PlayerSaveQueryFactory.getPlayer(this.factory).getUsername());
        Position position = PlayerSaveQueryFactory.getPlayer(this.factory).getPosition();
        preparedStatement.setInt(3, position.getX());
        preparedStatement.setInt(4, position.getY());
        preparedStatement.setInt(5, position.getPlane());
        int index = 0;
        while (index < PlayerSaveQueryFactory.getPlayer(this.factory).getAppearanceParts().length) {
            preparedStatement.setInt(index + 6, PlayerSaveQueryFactory.getPlayer(this.factory).getAppearanceParts()[index]);
            ++index;
        }
        index = 0;
        while (index < PlayerSaveQueryFactory.getPlayer(this.factory).getAppearanceColors().length) {
            preparedStatement.setInt(index + 13, PlayerSaveQueryFactory.getPlayer(this.factory).getAppearanceColors()[index]);
            ++index;
        }
        Object value = "";
        int index2 = 0;
        while (index2 < PlayerSaveQueryFactory.getPlayer(this.factory).getBankPinManager().getCurrentPin().length) {
            int player = PlayerSaveQueryFactory.getPlayer(this.factory).getBankPinManager().getCurrentPin()[index2];
            if (player == -1) {
                value = "na";
                break;
            }
            value = String.valueOf(value) + player;
            ++index2;
        }
        preparedStatement.setString(18, (String)value);
        preparedStatement.setInt(21, PlayerSaveQueryFactory.getPlayer(this.factory).getGender());
        preparedStatement.setLong(22, PlayerSaveQueryFactory.getPlayer(this.factory).getBanExpires());
        preparedStatement.setLong(23, PlayerSaveQueryFactory.getPlayer(this.factory).getMuteExpires());
        preparedStatement.setBoolean(24, PlayerSaveQueryFactory.getPlayer(this.factory).getBankPinManager().isChangingPin());
        preparedStatement.setBoolean(25, PlayerSaveQueryFactory.getPlayer(this.factory).getBankPinManager().isDeletingPin());
        preparedStatement.setInt(26, PlayerSaveQueryFactory.getPlayer(this.factory).getBankPinManager().getPinAppendYear());
        preparedStatement.setInt(27, PlayerSaveQueryFactory.getPlayer(this.factory).getBankPinManager().getPinAppendDate());
        preparedStatement.setInt(28, PlayerSaveQueryFactory.getPlayer(this.factory).getBindingNecklaceCharge());
        preparedStatement.setInt(29, PlayerSaveQueryFactory.getPlayer(this.factory).getRingOfForgingLife());
        preparedStatement.setInt(30, PlayerSaveQueryFactory.getPlayer(this.factory).getRingOfRecoilLife());
        preparedStatement.setInt(31, PlayerSaveQueryFactory.getPlayer((PlayerSaveQueryFactory)this.factory).getSlayerManager().slayerMasterId);
        preparedStatement.setString(32, PlayerSaveQueryFactory.getPlayer((PlayerSaveQueryFactory)this.factory).getSlayerManager().slayerTaskName);
        preparedStatement.setInt(33, PlayerSaveQueryFactory.getPlayer((PlayerSaveQueryFactory)this.factory).getSlayerManager().taskAmount);
        preparedStatement.setBoolean(34, PlayerSaveQueryFactory.getPlayer(this.factory).getSpellbook() == Spellbook.ANCIENT);
        preparedStatement.setBoolean(35, PlayerSaveQueryFactory.getPlayer(this.factory).isBrimhavenOpen());
        value = PlayerSaveQueryFactory.getPlayer(this.factory);
        preparedStatement.setBoolean(36, ((Player)value).killedClueAttacker);
        preparedStatement.executeUpdate();
        return null;
    }
}

