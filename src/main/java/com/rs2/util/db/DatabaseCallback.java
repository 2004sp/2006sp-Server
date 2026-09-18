package com.rs2.util.db;

import java.sql.ResultSet;

public interface DatabaseCallback {
    public void onResult(ResultSet resultSet) throws java.sql.SQLException;

    public void onException(Exception exception);
}
