package com.rs2.util.db;

import com.rs2.util.db.DatabaseCallback;
import com.rs2.util.db.DatabaseQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class DatabaseService {
    private static DatabaseService instance;
    private ExecutorService executor;
    private ThreadLocal threadContextLocal;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public DatabaseService(int value2, String text5, String jdbcUrl, String username, String password) throws ClassNotFoundException {
        Class.forName(text5);
        this.executor = Executors.newFixedThreadPool(8);
        this.threadContextLocal = new ThreadLocal();
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public final void submit(DatabaseQuery databaseQuery, DatabaseCallback databaseCallback) {
        databaseQuery.setThreadContextLocal(this.threadContextLocal);
        databaseQuery.setCallback(databaseCallback);
        databaseQuery.setService(this);
        this.executor.execute(databaseQuery);
    }

    protected final Connection openConnection() throws java.sql.SQLException {
        if (this.jdbcUrl.startsWith("jdbc:sqlite:")) {
            return SqliteDatabase.openConnection();
        }
        return DriverManager.getConnection(this.jdbcUrl, this.username, this.password);
    }

    public static DatabaseService getInstance() {
        return instance;
    }

    public final void shutdown() {
        if (this.executor != null) {
            this.executor.shutdownNow();
        }
    }

    public static synchronized void shutdownInstance() {
        if (instance != null) {
            instance.shutdown();
            instance = null;
        }
    }

    public static void setInstance(DatabaseService databaseService) {
        instance = databaseService;
    }
}
