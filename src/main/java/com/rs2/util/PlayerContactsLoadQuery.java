package com.rs2.util;

import com.rs2.model.player.Player;
import com.rs2.util.PlayerLoginLoadCallback;
import com.rs2.util.db.DatabaseQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class PlayerContactsLoadQuery
extends DatabaseQuery {
    private final Player player;

    public PlayerContactsLoadQuery(PlayerLoginLoadCallback playerLoginLoadCallback, String text2, Player player) {
        super(text2);
        this.player = player;
    }

    @Override
    public final ResultSet executeStatement(PreparedStatement preparedStatement) throws java.sql.SQLException {
        preparedStatement.setInt(1, this.player.getReferenceId());
        return preparedStatement.executeQuery();
    }
}

