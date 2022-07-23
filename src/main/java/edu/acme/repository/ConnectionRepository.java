package edu.acme.repository;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.acme.DatabaseConnectionPooling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.System.exit;

public class ConnectionRepository {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionPooling.class);
    private static final Lorem generator = LoremIpsum.getInstance();

    private static final String DB_CONNECTION_URL_FILE_MODE = "jdbc:oracle:thin:@//localhost:1522/XEPDB1";
    private static final String DB_USERNAME = "adm";
    private static final String DB_PASSWORD = "password";
    private static HikariDataSource hikariDataSource;
    private static final Properties sqlCommands = new Properties();

    public static String get(String s) {
        return sqlCommands.getProperty(s);
    }


    private void initializeHikariConnectionPooling() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_CONNECTION_URL_FILE_MODE);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(1800000);
        config.setMinimumIdle(2);
        config.setMaximumPoolSize(10);
        config.setAutoCommit(true);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        hikariDataSource = new HikariDataSource(config);
    }

    public ConnectionRepository() {
    }

    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private void loadSqlCommands() {
        try (InputStream inputStream = DatabaseConnectionPooling.class.getClassLoader().getResourceAsStream(
                "sql.properties")) {
            if (inputStream == null) {
                logger.error("Sorry, unable to find sql.properties, exiting application.");
                // Abnormal exit
                exit(-1);
            }

            //load a properties file from class path, inside static method
            sqlCommands.load(inputStream);
        } catch (IOException ex) {
            logger.error("Sorry, unable to parse sql.properties, exiting application.", ex);
            // Abnormal exit
            exit(-1);
        }
    }



}
