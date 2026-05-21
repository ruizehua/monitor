package com.nari.monitor.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class SQLiteConfig {

    private static final Logger logger = LoggerFactory.getLogger(SQLiteConfig.class);

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            
            statement.execute("PRAGMA journal_mode=WAL;");
            statement.execute("PRAGMA synchronous=NORMAL;");
            statement.execute("PRAGMA cache_size=-20000;");
            statement.execute("PRAGMA busy_timeout=5000;");
            statement.execute("PRAGMA foreign_keys=ON;");
            
            logger.info("SQLite WAL mode and concurrent settings initialized successfully");
        } catch (SQLException e) {
            logger.error("Failed to initialize SQLite settings", e);
        }
    }
}
