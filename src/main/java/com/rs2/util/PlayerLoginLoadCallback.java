package com.rs2.util;

import com.rs2.Server;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.util.PlayerContactsLoadQuery;
import com.rs2.util.PlayerProfileLoadQuery;
import com.rs2.util.PlayerSkillsLoadQuery;
import com.rs2.util.PlayerWorldUpdateQuery;
import com.rs2.util.db.DatabaseCallback;
import com.rs2.util.db.DatabaseService;
import com.rs2.util.db.player.PlayerLoadQueryFactory;
import java.sql.ResultSet;

public final class PlayerLoginLoadCallback
implements DatabaseCallback {
    private final Player player;

    public PlayerLoginLoadCallback(Player player) {
        this.player = player;
    }

    @Override
    public final void onResult(ResultSet resultSet) throws java.sql.SQLException {
        if (!this.processUidLookupResult(resultSet)) {
            this.player.disconnect();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean processUidLookupResult(ResultSet resultSet) throws java.sql.SQLException {
        if (!resultSet.next()) {
            this.player.setLoginResponseCode(3);
            this.player.sendLoginResponse();
            return false;
        }
        try {
            int intValue = resultSet.getInt("uid");
            this.player.setReferenceId(intValue);
            Player[] playerArray = World.getPlayers();
            int length = playerArray.length;
            int index = 0;
            while (true) {
                if (index >= length) {
                    this.player.setPlayerRights(2);
                    break;
                }
                Player player = playerArray[index];
                if (player != null && this.player.getNameHash() == player.getNameHash()) {
                    this.player.setLoginResponseCode(5);
                    this.player.sendLoginResponse();
                    player.disconnect();
                    return false;
                }
                ++index;
            }
        }
        catch (Exception exception) {
            this.onException(exception);
            return false;
        }
        PlayerLoadQueryFactory playerLoadQueryFactory = new PlayerLoadQueryFactory(this.player);
        DatabaseService.getInstance().submit(new PlayerProfileLoadQuery(this, "SELECT x, y, z, is_male, is_auto_retaliate, fight_mode, brightness, mouse_buttons, chat_effects, split_private_chat, accept_aid, music_volume, effect_volume, quest_points, special, energy, is_running, skull_timer, pin, appearance_0, appearance_1, appearance_2, appearance_3, appearance_4, appearance_5, appearance_6, color_0, color_1, color_2, color_3, color_4, tutorial_stage, tutorial_progress, ban_expires, mute_expires, changing_bankpin, deleting_bankpin, pin_append_year, pin_append_date, binding_neck_charge, ring_of_forging_life, ring_of_recoil_life, slayer_master, slayer_task, task_amount, using_ancients, brimhaven_open, killed_clue_attacker FROM `prs06_players` WHERE id = ?", this.player), playerLoadQueryFactory.createProfileLoadCallback());
        DatabaseService.getInstance().submit(playerLoadQueryFactory.createContainerLoadQuery(0), playerLoadQueryFactory.createBankLoadCallback());
        DatabaseService.getInstance().submit(playerLoadQueryFactory.createContainerLoadQuery(1), playerLoadQueryFactory.createInventoryLoadCallback());
        DatabaseService.getInstance().submit(playerLoadQueryFactory.createContainerLoadQuery(2), playerLoadQueryFactory.createEquipmentLoadCallback());
        DatabaseService.getInstance().submit(new PlayerContactsLoadQuery(this, "SELECT `contact`, `ignore`, `slot` FROM prs06_contacts WHERE `player_id` = ?", this.player), playerLoadQueryFactory.createContactsLoadCallback());
        DatabaseService.getInstance().submit(new PlayerSkillsLoadQuery(this, "SELECT * FROM prs06_skills WHERE player_id = ? LIMIT 1", this.player), playerLoadQueryFactory.createSkillsLoadCallback());
        if (this.player.isBanned()) {
            this.player.setLoginResponseCode(4);
            this.player.sendLoginResponse();
            return false;
        }
        Server.getInstance().queueLogin(this.player);
        DatabaseService.getInstance().submit(new PlayerWorldUpdateQuery(this, "UPDATE `prs06_users` SET `world`=? WHERE `uid` = ?", this.player), null);
        return true;
    }

    @Override
    public final void onException(Exception exception) {
        this.player.setLoginResponseCode(11);
        this.player.sendLoginResponse();
        exception.printStackTrace();
    }
}
