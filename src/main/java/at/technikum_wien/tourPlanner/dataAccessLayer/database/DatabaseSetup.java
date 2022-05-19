package at.technikum_wien.tourPlanner.dataAccessLayer.database;


import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.configuration.Configuration;

import java.sql.*;

public class DatabaseSetup {

    public static void setUp() throws SQLException {
        Configuration conf = Injector.getConfig("app.properties");
        Connection connection = DriverManager.getConnection(conf.get("db.dbLink"), conf.get("db.user"), conf.get("db.password"));
        try {
            createTourTable(connection);
            createLogTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    private static void createTourTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createSql = "CREATE TABLE IF NOT EXISTS " + TableNames.getTourTableName() + "( " +
                "uid SERIAL NOT NULL, " +
                "tourName VARCHAR(256) NOT NULL, " +
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

    private static void createLogTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createSql = "CREATE TABLE IF NOT EXISTS " + TableNames.getLogTableName() + "(" +
                "uid SERIAL NOT NULL, " +
                "tourId INTEGER NOT NULL, " +
                "date DATE NOT NULL, " +
                "comment VARCHAR(256) NOT NULL, " +
                "difficulty INTEGER NOT NULL, " +
                "totalTime VARCHAR(256) NOT NULL, " +
                "rating INTEGER NOT NULL, " +
                "PRIMARY KEY(uid), " +
                "CONSTRAINT tourIdForeignKey FOREIGN KEY(tourId) REFERENCES tours(uid))";
        statement.executeUpdate(createSql);
        statement.close();
    }

}
