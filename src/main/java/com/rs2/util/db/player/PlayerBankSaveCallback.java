package com.rs2.util.db.player;

import com.rs2.util.db.DatabaseCallback;
import java.sql.ResultSet;

public final class PlayerBankSaveCallback implements DatabaseCallback {
    public PlayerBankSaveCallback() {
    }

    @Override
    public void onResult(ResultSet resultSet) throws java.sql.SQLException {
    }

    @Override
    public void onException(Exception exception) {
        exception.printStackTrace();
    }
}
