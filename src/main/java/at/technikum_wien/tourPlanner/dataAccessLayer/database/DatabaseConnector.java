package at.technikum_wien.tourPlanner.dataAccessLayer.database;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.configuration.Configuration;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;


import java.sql.*;


public class DatabaseConnector {
    private Connection connection;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;
    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    public DatabaseConnector() {
        Configuration conf = Injector.getConfig("app.properties");
        this.DB_URL = conf.get("db.dbLink");
        this.DB_USER = conf.get("db.user");
        this.DB_PASSWORD = conf.get("db.password");
    }

    public void connect() throws SQLException {
        logger.debug("Connecting to database");
        this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        DatabaseSetup.setUp();
    }

    public Connection getConnection() {
        return connection;
    }
}