package com.rs2.util.db.player;

import com.rs2.model.Position;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.util.db.DatabaseCallback;
import com.rs2.util.db.player.PlayerLoadQueryFactory;
import java.sql.ResultSet;

public final class PlayerProfileLoadCallback
implements DatabaseCallback {
    private PlayerLoadQueryFactory factory;

    private PlayerProfileLoadCallback(PlayerLoadQueryFactory playerLoadQueryFactory) {
        this.factory = playerLoadQueryFactory;
    }

    @Override
    public final void onResult(ResultSet resultSet2) throws java.sql.SQLException {
        if (resultSet2.next()) {
            String text;
            ResultSet resultSet = resultSet2;
            Object value = this;
            value = PlayerLoadQueryFactory.getPlayer(((PlayerProfileLoadCallback)value).factory).getPosition();
            ((Position)value).setX(resultSet.getInt("x"));
            ((Position)value).setPreviousX(resultSet.getInt("x"));
            ((Position)value).setY(resultSet.getInt("y"));
            ((Position)value).setPreviousY(resultSet.getInt("y"));
            ((Position)value).setPlane(resultSet.getInt("z"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setGender(resultSet2.getInt("is_male"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setAutoRetaliate(resultSet2.getBoolean("is_auto_retaliate"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setFightMode(resultSet2.getInt("fight_mode"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setBrightness(resultSet2.getInt("brightness"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setMouseButtons(resultSet2.getInt("mouse_buttons"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setPublicChatEffects(resultSet2.getInt("chat_effects"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setSplitPrivateChat(resultSet2.getInt("split_private_chat"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setAcceptAid(resultSet2.getInt("accept_aid"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setMusicVolume(resultSet2.getInt("music_volume"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setEffectVolume(resultSet2.getInt("effect_volume"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setSpecialEnergy(resultSet2.getInt("special"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setRunEnergyPercent(resultSet2.getInt("energy"));
            PlayerLoadQueryFactory.getPlayer(this.factory).getMovementQueue().setRunning(resultSet2.getBoolean("is_running"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setMuteExpires(resultSet2.getLong("mute_expires"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setBanExpires(resultSet2.getLong("ban_expires"));
            PlayerLoadQueryFactory.getPlayer(this.factory).getBankPinManager().setChangingPin(resultSet2.getBoolean("changing_bankpin"));
            PlayerLoadQueryFactory.getPlayer(this.factory).getBankPinManager().setDeletingPin(resultSet2.getBoolean("deleting_bankpin"));
            PlayerLoadQueryFactory.getPlayer(this.factory).getBankPinManager().setPinAppendYear(resultSet2.getInt("pin_append_year"));
            PlayerLoadQueryFactory.getPlayer(this.factory).getBankPinManager().setPinAppendYear(resultSet2.getInt("pin_append_date"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setBindingNecklaceCharge(resultSet2.getInt("binding_neck_charge"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setRingOfForgingLife(resultSet2.getInt("ring_of_forging_life"));
            PlayerLoadQueryFactory.getPlayer(this.factory).setRingOfForgingLife(resultSet2.getInt("ring_of_recoil_life"));
            int intValue = resultSet2.getInt("skull_timer");
            if (intValue > 0) {
                PlayerLoadQueryFactory.getPlayer(this.factory).addPvpCombatReference(PlayerLoadQueryFactory.getPlayer(this.factory), intValue);
            }
            if (!(text = resultSet2.getString("pin")).equals("na")) {
                int index = 0;
                while (index < PlayerLoadQueryFactory.getPlayer(this.factory).getBankPinManager().getCurrentPin().length) {
                    PlayerLoadQueryFactory.getPlayer((PlayerLoadQueryFactory)this.factory).getBankPinManager().getCurrentPin()[index] = Integer.parseInt(text.substring(index, index + 1));
                    ++index;
                }
            }
            int index2 = 0;
            while (index2 < PlayerLoadQueryFactory.getPlayer(this.factory).getAppearanceParts().length) {
                PlayerLoadQueryFactory.getPlayer((PlayerLoadQueryFactory)this.factory).getAppearanceParts()[index2] = resultSet2.getInt("appearance_" + index2);
                ++index2;
            }
            index2 = 0;
            while (index2 < PlayerLoadQueryFactory.getPlayer(this.factory).getAppearanceColors().length) {
                PlayerLoadQueryFactory.getPlayer((PlayerLoadQueryFactory)this.factory).getAppearanceColors()[index2] = resultSet2.getInt("color_" + index2);
                ++index2;
            }
            PlayerLoadQueryFactory.getPlayer((PlayerLoadQueryFactory)this.factory).getSlayerManager().slayerMasterId = resultSet2.getInt("slayer_master");
            PlayerLoadQueryFactory.getPlayer((PlayerLoadQueryFactory)this.factory).getSlayerManager().slayerTaskName = resultSet2.getString("slayer_task");
            PlayerLoadQueryFactory.getPlayer((PlayerLoadQueryFactory)this.factory).getSlayerManager().taskAmount = resultSet2.getInt("task_amount");
            PlayerLoadQueryFactory.getPlayer(this.factory).setSpellbook(resultSet2.getBoolean("using_ancients") ? Spellbook.ANCIENT : Spellbook.MODERN);
            PlayerLoadQueryFactory.getPlayer(this.factory).setBrimhavenOpen(resultSet2.getBoolean("brimhaven_open"));
            boolean booleanValue = resultSet2.getBoolean("killed_clue_attacker");
            PlayerLoadQueryFactory.getPlayer(this.factory).killedClueAttacker = booleanValue;
        }
    }

    @Override
    public final void onException(Exception exception) {
        exception.printStackTrace();
    }

    public PlayerProfileLoadCallback(PlayerLoadQueryFactory playerLoadQueryFactory, byte value2) {
        this(playerLoadQueryFactory);
    }
}

