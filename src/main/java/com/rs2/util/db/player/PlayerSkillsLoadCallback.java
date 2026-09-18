package com.rs2.util.db.player;

import com.rs2.util.db.DatabaseCallback;
import com.rs2.util.db.player.PlayerLoadQueryFactory;
import java.sql.ResultSet;
import java.util.HashMap;

public final class PlayerSkillsLoadCallback
implements DatabaseCallback {
    private PlayerLoadQueryFactory factory;

    private PlayerSkillsLoadCallback(PlayerLoadQueryFactory playerLoadQueryFactory) {
        this.factory = playerLoadQueryFactory;
    }

    @Override
    public final void onResult(ResultSet resultSet) throws java.sql.SQLException {
        while (resultSet.next()) {
            HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
            hashMap.put(0, resultSet.getInt("cur_attack"));
            hashMap.put(1, resultSet.getInt("cur_defence"));
            hashMap.put(2, resultSet.getInt("cur_strength"));
            hashMap.put(3, resultSet.getInt("cur_hitpoints"));
            hashMap.put(4, resultSet.getInt("cur_ranged"));
            hashMap.put(5, resultSet.getInt("cur_prayer"));
            hashMap.put(6, resultSet.getInt("cur_magic"));
            hashMap.put(7, resultSet.getInt("cur_cooking"));
            hashMap.put(8, resultSet.getInt("cur_woodcutting"));
            hashMap.put(9, resultSet.getInt("cur_fletching"));
            hashMap.put(10, resultSet.getInt("cur_fishing"));
            hashMap.put(11, resultSet.getInt("cur_firemaking"));
            hashMap.put(12, resultSet.getInt("cur_crafting"));
            hashMap.put(13, resultSet.getInt("cur_smithing"));
            hashMap.put(14, resultSet.getInt("cur_mining"));
            hashMap.put(15, resultSet.getInt("cur_herblore"));
            hashMap.put(16, resultSet.getInt("cur_agility"));
            hashMap.put(17, resultSet.getInt("cur_thieving"));
            hashMap.put(18, resultSet.getInt("cur_slayer"));
            hashMap.put(19, resultSet.getInt("cur_farming"));
            hashMap.put(20, resultSet.getInt("cur_runecrafting"));
            HashMap<Integer, Integer> hashMap2 = new HashMap<Integer, Integer>();
            hashMap2.put(0, resultSet.getInt("exp_attack"));
            hashMap2.put(1, resultSet.getInt("exp_defence"));
            hashMap2.put(2, resultSet.getInt("exp_strength"));
            hashMap2.put(3, resultSet.getInt("exp_hitpoints"));
            hashMap2.put(4, resultSet.getInt("exp_ranged"));
            hashMap2.put(5, resultSet.getInt("exp_prayer"));
            hashMap2.put(6, resultSet.getInt("exp_magic"));
            hashMap2.put(7, resultSet.getInt("exp_cooking"));
            hashMap2.put(8, resultSet.getInt("exp_woodcutting"));
            hashMap2.put(9, resultSet.getInt("exp_fletching"));
            hashMap2.put(10, resultSet.getInt("exp_fishing"));
            hashMap2.put(11, resultSet.getInt("exp_firemaking"));
            hashMap2.put(12, resultSet.getInt("exp_crafting"));
            hashMap2.put(13, resultSet.getInt("exp_smithing"));
            hashMap2.put(14, resultSet.getInt("exp_mining"));
            hashMap2.put(15, resultSet.getInt("exp_herblore"));
            hashMap2.put(16, resultSet.getInt("exp_agility"));
            hashMap2.put(17, resultSet.getInt("exp_thieving"));
            hashMap2.put(18, resultSet.getInt("exp_slayer"));
            hashMap2.put(19, resultSet.getInt("exp_farming"));
            hashMap2.put(20, resultSet.getInt("exp_runecrafting"));
            System.out.println(hashMap2.get(20));
            int index = 0;
            while (index <= 20) {
                PlayerLoadQueryFactory.getPlayer((PlayerLoadQueryFactory)this.factory).getSkillManager().getCurrentLevels()[index] = (Integer)hashMap.get(index);
                ++index;
            }
            index = 0;
            while (index <= 20) {
                PlayerLoadQueryFactory.getPlayer((PlayerLoadQueryFactory)this.factory).getSkillManager().getExperience()[index] = ((Integer)hashMap2.get(index)).intValue();
                ++index;
            }
        }
    }

    @Override
    public final void onException(Exception exception) {
        exception.printStackTrace();
    }

    public PlayerSkillsLoadCallback(PlayerLoadQueryFactory playerLoadQueryFactory, byte value2) {
        this(playerLoadQueryFactory);
    }
}

