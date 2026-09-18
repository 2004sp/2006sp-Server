package com.rs2.util.db.player;

import com.rs2.util.db.DatabaseQuery;
import com.rs2.util.db.player.PlayerSaveQueryFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class PlayerContactsSaveQuery
extends DatabaseQuery {
    private PlayerSaveQueryFactory factory;

    public PlayerContactsSaveQuery(PlayerSaveQueryFactory playerSaveQueryFactory) {
        super(PlayerSaveQueryFactory.getContactsSaveSql());
        this.factory = playerSaveQueryFactory;
    }

    @Override
    public final ResultSet executeStatement(PreparedStatement preparedStatement) throws java.sql.SQLException {
        int initialValue = 1;
        long[] player = PlayerSaveQueryFactory.getPlayer(this.factory).getIgnoreList();
        int index = 0;
        while (index < player.length) {
            preparedStatement.setInt(initialValue++, 0x40000000 | index << 24 | PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
            preparedStatement.setInt(initialValue++, PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
            preparedStatement.setInt(initialValue++, index);
            preparedStatement.setLong(initialValue++, player[index]);
            preparedStatement.setBoolean(initialValue++, true);
            ++index;
        }
        long[] player2 = PlayerSaveQueryFactory.getPlayer(this.factory).getFriendsList();
        int index2 = 0;
        while (index2 < player2.length) {
            preparedStatement.setInt(initialValue++, index2 << 24 | PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
            preparedStatement.setInt(initialValue++, PlayerSaveQueryFactory.getPlayer(this.factory).getReferenceId());
            preparedStatement.setInt(initialValue++, index2);
            preparedStatement.setLong(initialValue++, player2[index2]);
            preparedStatement.setBoolean(initialValue++, false);
            ++index2;
        }
        preparedStatement.executeUpdate();
        return null;
    }
}

