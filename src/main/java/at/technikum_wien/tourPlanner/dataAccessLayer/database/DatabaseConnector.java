package at.technikum_wien.tourPlanner.dataAccessLayer.database;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.configuration.Configuration;
import at.technikum_wien.tourPlanner.models.Tour;

import java.sql.*;
import java.util.LinkedList;

public class DatabaseConnector {
    private final boolean tablesExist = false;
    private DatabaseSetup dataBaseSetup;
    private Connection connection;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public DatabaseConnector() {
        Configuration conf = Injector.getConfig("app.properties");
        this.DB_URL = conf.get("db.dbLink");
        this.DB_USER = conf.get("db.user");
        this.DB_PASSWORD = conf.get("db.password");
    }

    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        DatabaseSetup.setUp();
    }

    public void disconnect() throws SQLException {
        this.connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}