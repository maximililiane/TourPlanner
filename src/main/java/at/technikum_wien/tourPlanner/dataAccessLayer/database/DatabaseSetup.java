package at.technikum_wien.tourPlanner.dataAccessLayer.database;


import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.configuration.Configuration;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;

import java.sql.*;

public class DatabaseSetup {

    private final static LoggerWrapper logger = LoggerFactory.getLogger();

    public static void setUp() throws SQLException {
        Configuration conf = Injector.getConfig("app.properties");
        Connection connection = DriverManager.getConnection(conf.get("db.dbLink"), conf.get("db.user"), conf.get("db.password"));
        try {
            logger.debug("Initialising database.");
            createTourTable(connection, TableName.REGULAR_TOUR_TABLE_NAME.getName());
            createTourTable(connection, TableName.DEMO_TOUR_TABLE_NAME.getName());
            createLogTable(connection, TableName.REGULAR_TOUR_LOG_TABLE_NAME.getName(), TableName.REGULAR_TOUR_TABLE_NAME.getName());
            createLogTable(connection, TableName.DEMO_TOUR_LOG_TABLE_NAME.getName(), TableName.DEMO_TOUR_TABLE_NAME.getName());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to set up the database; " + e.getMessage());
        }
        connection.close();
    }

    private static void createTourTable(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        String createSql = "CREATE TABLE IF NOT EXISTS " + tableName + "( " +
                "uid SERIAL NOT NULL, " +
                "tourName VARCHAR(16) NOT NULL, " +
                "description TEXT NOT NULL, " +
                "startingPoint VARCHAR(256) NOT NULL, " +
                "destination VARCHAR(256) NOT NULL, " +
                "distance DOUBLE PRECISION NOT NULL, " +
                "duration VARCHAR(256) NOT NULL, " +
                "transportType VARCHAR(256) NOT NULL, " +
                "mapImage VARCHAR(256) NOT NULL, " +
                "childFriendliness INTEGER NOT NULL, " +
                "popularity INTEGER NOT NULL, " +
                "UNIQUE (tourName), " +
                "PRIMARY KEY(uid))";
        statement.executeUpdate(createSql);
        statement.close();
    }

    private static void createLogTable(Connection connection, String logTableName, String tourTableName) throws SQLException {
        Statement statement = connection.createStatement();
        String createSql = "CREATE TABLE IF NOT EXISTS " + logTableName + "(" +
                "uid SERIAL NOT NULL, " +
                "tourId INTEGER NOT NULL, " +
                "date DATE NOT NULL, " +
                "comment VARCHAR(256), " +
                "difficulty INTEGER NOT NULL, " +
                "totalTime VARCHAR(256) NOT NULL, " +
                "rating INTEGER NOT NULL, " +
                "PRIMARY KEY(uid), " +
                "CONSTRAINT tourIdForeignKey FOREIGN KEY(tourId) REFERENCES " + tourTableName + "(uid) ON DELETE CASCADE)";
        statement.executeUpdate(createSql);
        statement.close();
    }

}
