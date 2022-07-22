import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class DatabaseConnectionPooling {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionPooling.class);
    private static final String DB_CONNECTION_URL_FILE_MODE = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
    private static final String DB_USERNAME = "jdbcdemo";
    private static final String DB_PASSWORD = "jdbcdemo123";
    private final Properties sqlCommands = new Properties();
    private HikariDataSource hikariDataSource;
    public static void main(String[] args) {
            if (args.length == 0) {
                logger.debug("No arguments passed.");
            }

            var demo = new DatabaseConnectionPooling();

            demo.loadSqlCommands();
            demo.loadDatabaseDriver();

            // Initializing Connection Pooling mechanism
            demo.initializeHikariConnectionPooling();

            demo.dropTable();

            demo.createTable();
            demo.insertData();
            demo.insertGeneratedData(20);
            demo.readData();

            demo.updateData();
            demo.readData();

            demo.deleteData();
            demo.readData();
        }


    }

