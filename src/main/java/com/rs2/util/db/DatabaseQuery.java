package com.rs2.util.db;

import com.rs2.util.db.DatabaseCallback;
import com.rs2.util.db.DatabaseService;
import com.rs2.util.db.DatabaseThreadContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class DatabaseQuery
implements Runnable {
    private final String sql;
    private ThreadLocal threadContextLocal;
    private DatabaseCallback callback;
    private DatabaseService service;

    public DatabaseQuery(String sql) {
        this.sql = sql;
    }

    public abstract ResultSet executeStatement(PreparedStatement statement) throws SQLException;

    @Override
    public void run() {
        runControlExit1: {
            try {
                PreparedStatement preparedStatement;
                Connection connection;
                Object databaseThreadContext = (DatabaseThreadContext)this.threadContextLocal.get();
                if (databaseThreadContext == null) {
                    databaseThreadContext = new DatabaseThreadContext(this);
                    this.threadContextLocal.set(databaseThreadContext);
                }
                if ((connection = DatabaseThreadContext.getConnection((DatabaseThreadContext)databaseThreadContext)) == null || connection.isClosed()) {
                    connection = this.service.openConnection();
                    DatabaseThreadContext.setConnection((DatabaseThreadContext)databaseThreadContext, connection);
                }
                if (DatabaseThreadContext.getPreparedStatements((DatabaseThreadContext)databaseThreadContext) == null) {
                    DatabaseThreadContext.setPreparedStatements((DatabaseThreadContext)databaseThreadContext, new HashMap());
                }
                if ((preparedStatement = (PreparedStatement)DatabaseThreadContext.getPreparedStatements((DatabaseThreadContext)databaseThreadContext).get(this.sql)) == null) {
                    preparedStatement = connection.prepareStatement(this.sql);
                    DatabaseThreadContext.getPreparedStatements((DatabaseThreadContext)databaseThreadContext).put(this.sql, preparedStatement);
                }
                preparedStatement.setQueryTimeout(5);
                databaseThreadContext = this.executeStatement(preparedStatement);
                if (this.callback != null) {
                    this.callback.onResult((ResultSet)databaseThreadContext);
                }
                if (databaseThreadContext != null) {
                    ((ResultSet)databaseThreadContext).close();
                    return;
                }
            }
            catch (SQLException sQLException) {
                if (this.callback == null) break runControlExit1;
                this.callback.onException(sQLException);
            }
        }
    }

    protected final void setThreadContextLocal(ThreadLocal threadLocal) {
        this.threadContextLocal = threadLocal;
    }

    protected final void setService(DatabaseService databaseService) {
        this.service = databaseService;
    }

    protected final void setCallback(DatabaseCallback databaseCallback) {
        this.callback = databaseCallback;
    }
}
