package edu.acme;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.exit;

public class DatabaseConnectionPooling {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionPooling.class);
    private static final Lorem generator = LoremIpsum.getInstance();

    private static final String DB_CONNECTION_URL_FILE_MODE = "jdbc:oracle:thin:@//localhost:1522/XEPDB1";
    private static final String DB_USERNAME = "adm";
    private static final String DB_PASSWORD = "password";
    private final Properties sqlCommands = new Properties();
    private HikariDataSource hikariDataSource;
    public static void main(String[] args) {
            if (args.length == 0) {
                logger.debug("No arguments passed.");
            }

            var dss = new DatabaseConnectionPooling();

            dss.loadSqlCommands();
            dss.loadDatabaseDriver();

            // Initializing Connection Pooling mechanism
            dss.initializeHikariConnectionPooling();

            dss.dropTable();

            dss.createTable();
            dss.insertData();
            dss.insertGeneratedData(20);
            dss.readData();

            dss.updateData();
            dss.readData();

            dss.deleteData();
            dss.readData();
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

    private void dropTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            int result = statement.executeUpdate(sqlCommands.getProperty("drop.table"));
            logger.info("Drop table command was successful with result {}.", result);
        } catch (SQLException ex) {
            logger.warn("Error while dropping table as it does not probably exist.");
        }
    }

    private void createTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            logger.info("Created table command was successful with result {}.",
                    statement.executeUpdate(sqlCommands.getProperty("create.table")));
        } catch (SQLException ex) {
            logger.error("Error while creating table.", ex);
            exit(-1);
        }
    }

    private void insertData() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            //@formatter:off
            // Classic insert statements
            runInsertCommands(statement,
                    sqlCommands.getProperty("insert.table.001"),
                    sqlCommands.getProperty("insert.table.002"),
                    sqlCommands.getProperty("insert.table.003"),
                    sqlCommands.getProperty("insert.table.004"),
                    sqlCommands.getProperty("insert.table.005"));
            //@formatter:on

            // Insert data in batch mode
            statement.addBatch(sqlCommands.getProperty("insert.table.001"));
            statement.addBatch(sqlCommands.getProperty("insert.table.002"));
            statement.addBatch(sqlCommands.getProperty("insert.table.003"));
            statement.addBatch(sqlCommands.getProperty("insert.table.004"));
            statement.addBatch(sqlCommands.getProperty("insert.table.005"));
            statement.addBatch(sqlCommands.getProperty("insert.table.006"));
            statement.addBatch(sqlCommands.getProperty("insert.table.007"));
            statement.addBatch(sqlCommands.getProperty("insert.table.008"));
            statement.addBatch(sqlCommands.getProperty("insert.table.009"));
            statement.addBatch(sqlCommands.getProperty("insert.table.010"));
            statement.addBatch(sqlCommands.getProperty("insert.table.011"));
            statement.addBatch(sqlCommands.getProperty("insert.table.012"));
            statement.addBatch(sqlCommands.getProperty("insert.table.013"));
            statement.addBatch(sqlCommands.getProperty("insert.table.014"));
            statement.addBatch(sqlCommands.getProperty("insert.table.015"));
            statement.addBatch(sqlCommands.getProperty("insert.table.016"));
            statement.addBatch(sqlCommands.getProperty("insert.table.017"));
            statement.addBatch(sqlCommands.getProperty("insert.table.018"));
            statement.addBatch(sqlCommands.getProperty("insert.table.019"));
            statement.addBatch(sqlCommands.getProperty("insert.table.020"));
            statement.addBatch(sqlCommands.getProperty("insert.table.021"));
            statement.addBatch(sqlCommands.getProperty("insert.table.022"));
            statement.addBatch(sqlCommands.getProperty("insert.table.023"));
            statement.addBatch(sqlCommands.getProperty("insert.table.024"));
            statement.addBatch(sqlCommands.getProperty("insert.table.025"));
            statement.addBatch(sqlCommands.getProperty("insert.table.026"));
            statement.addBatch(sqlCommands.getProperty("insert.table.027"));
            statement.addBatch(sqlCommands.getProperty("insert.table.028"));
            statement.addBatch(sqlCommands.getProperty("insert.table.029"));
            statement.addBatch(sqlCommands.getProperty("insert.table.030"));
            statement.addBatch(sqlCommands.getProperty("insert.table.031"));
            statement.addBatch(sqlCommands.getProperty("insert.table.032"));
            statement.addBatch(sqlCommands.getProperty("insert.table.033"));
            statement.addBatch(sqlCommands.getProperty("insert.table.034"));
            statement.addBatch(sqlCommands.getProperty("insert.table.035"));


            int[] rowsAffectedArray = statement.executeBatch();
            logger.info("Insert commands were successful with {} row(s) affected.", Arrays.stream(rowsAffectedArray)
                    .summaryStatistics()
                    .getSum());
        } catch (SQLException ex) {
            logger.error("Error while inserting data.", ex);
        }
    }

    private void runInsertCommands(Statement statement, String... commands) throws SQLException {
        for (String command : commands) {
            logger.info("Insert command was successful with {} row(s) affected.", statement.executeUpdate(command));
        }
    }

    private void insertGeneratedData(int howManyStatements) {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                sqlCommands.getProperty("insert.table.000"))) {

            batchInsert(preparedStatement, howManyStatements);

            int[] rowsAffectedArray = preparedStatement.executeBatch();
            logger.info("Insert batch command were successful with {} row(s) affected.", Arrays.stream(
                    rowsAffectedArray).summaryStatistics().getSum());

        } catch (SQLException ex) {
            logger.error("Error while inserting data.", ex);
        }
    }

    private void batchInsert(PreparedStatement preparedStatement, int howManyStatements) throws SQLException {
        int maximumIdValue = findMaximumIdValue();

        for (int i = 1; i <= howManyStatements; i++) {
            // Clear parameters from the statement so it can be reused
            preparedStatement.clearParameters();

            preparedStatement.setInt(1, maximumIdValue + i);
            preparedStatement.setString(2, generator.getFirstName());
            preparedStatement.setString(3, generator.getLastName());
            // Add command to batch
            preparedStatement.addBatch();
        }
    }
    private void updateData() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            logger.info("Update command was successful with {} row(s) affected.");
        } catch (SQLException ex) {
            logger.error("Error while updating data.", ex);
        }
    }

    private void deleteData() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            logger.info("Delete command was successful with {} row(s) affected.");
        } catch (SQLException ex) {
            logger.error("Error while deleting data.", ex);
        }
    }

    private void readData() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery(sqlCommands.getProperty("select.table.001"))) {

            logger.info("---------------------------------------------------------------------");
            //@formatter:off
            int rowCount =1;
            while (resultSet.next()) {
                logger.info("{}. {}:{}, {}:{}, {}:{}, {}:{}", rowCount++,
                        resultSet.getMetaData().getColumnName(1), resultSet.getString(1),
                        resultSet.getMetaData().getColumnName(2), resultSet.getString(2),
                        resultSet.getMetaData().getColumnName(3), resultSet.getString(3),
                        resultSet.getMetaData().getColumnName(4), resultSet.getString(4));
            }
            //@formatter:on
            logger.info("---------------------------------------------------------------------");

        } catch (SQLException ex) {
            logger.error("Error while reading data.", ex);
        }
    }

    private int findMaximumIdValue() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlCommands.getProperty(""))) {

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                logger.info("Maximum id value found is {}.", resultSet.getInt(1));

                return resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            logger.error("Error while reading data.", ex);
        }

        return 0;
    }

    private void initializeHikariConnectionPooling() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_CONNECTION_URL_FILE_MODE);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);

        // This property controls the maximum number of milliseconds that a client (that's you) will wait for a
        // connection from the pool.
        // Defaults to 30000ms (30secs).
        config.setConnectionTimeout(10000);

        // This property controls the maximum amount of time that a connection is allowed to sit idle in the pool.
        // This setting only applies when minimumIdle is defined to be less than maximumPoolSize.
        // Defaults to 600000ms (10mins).
        config.setIdleTimeout(300000);

        // This property controls the maximum lifetime of a connection in the pool. An in-use connection will never be
        // retired, only when it is closed will it then be removed.
        // Defaults to 1800000ms (30mins).
        config.setMaxLifetime(1800000);

        // This property controls the minimum number of idle connections that HikariCP tries to maintain in the pool.
        // Defaults to maximumPoolSize value.
        config.setMinimumIdle(2);

        // This property controls the maximum size that the pool is allowed to reach, including both idle and in-use
        // connections.
        // Defaults to 10.
        config.setMaximumPoolSize(10);

        // This property controls the default auto-commit behavior of connections returned from the pool.
        // Defaults to true.
        config.setAutoCommit(true);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        hikariDataSource = new HikariDataSource(config);
    }

    private Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private void loadDatabaseDriver() {
        // Traditional way of loading database driver
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        logger.info("Oracle JDBC driver server has been successfully loaded.");
    }

    }

