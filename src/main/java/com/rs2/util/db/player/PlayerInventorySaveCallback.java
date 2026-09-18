package com.rs2.util.db.player;

import com.rs2.util.db.DatabaseCallback;
import java.sql.ResultSet;

public final class PlayerInventorySaveCallback implements DatabaseCallback {
    public PlayerInventorySaveCallback() {
    }

    @Override
    public void onResult(ResultSet resultSet) throws java.sql.SQLException {
    }

    @Override
    public void onException(Exception exception) {
        exception.printStackTrace();
    }
}
