package com.rs2.util;

import com.rs2.util.db.DatabaseQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public final class ConverterUidLookupQuery
extends DatabaseQuery {
    private final String username;

    public ConverterUidLookupQuery(String text3, String username) {
        super(text3);
        this.username = username;
    }

    @Override
    public final ResultSet executeStatement(PreparedStatement preparedStatement) throws java.sql.SQLException {
        preparedStatement.setString(1, this.username);
        return preparedStatement.executeQuery();
    }
}

