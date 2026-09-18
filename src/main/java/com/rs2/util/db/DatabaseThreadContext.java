package com.rs2.util.db;

import com.rs2.util.db.DatabaseQuery;
import java.sql.Connection;
import java.util.Map;

public final class DatabaseThreadContext {
    private Connection connection;
    private Map preparedStatements;

    public DatabaseThreadContext(DatabaseQuery databaseQuery) {
    }

    static Connection getConnection(DatabaseThreadContext databaseThreadContext) {
        return databaseThreadContext.connection;
    }

    static void setConnection(DatabaseThreadContext databaseThreadContext, Connection connection) {
        databaseThreadContext.connection = connection;
    }

    static Map getPreparedStatements(DatabaseThreadContext databaseThreadContext) {
        return databaseThreadContext.preparedStatements;
    }

    static void setPreparedStatements(DatabaseThreadContext databaseThreadContext, Map map) {
        databaseThreadContext.preparedStatements = map;
    }
}

