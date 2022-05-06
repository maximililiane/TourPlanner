package at.technikum_wien.tourPlanner.database;


import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.configuration.Configuration;

import java.sql.*;

public class DataBaseSetup {

    public static void setUp() throws SQLException {
        Configuration conf = Injector.getConfig("app.properties");
        Connection connection = DriverManager.getConnection(conf.get("db.dbLink"), conf.get("db.user"), conf.get("db.password"));
        try {
            createTourTable(connection);
            //TODO: createLogTable()
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    private static void createTourTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createSql = "CREATE TABLE IF NOT EXISTS " + TableNames.getTourTableName() + "( " +
                "uid VARCHAR(256) NOT NULL, " +
                "tourName VARCHAR(256) NOT NULL, " +
                "description VARCHAR(256) NOT NULL, " +
                "startingPoint VARCHAR(256) NOT NULL, " +
                "destination VARCHAR(256) NOT NULL, " +
                "distance FLOAT NOT NULL, " +
                "duration VARCHAR(256) NOT NULL, " +
                "transportType VARCHAR(256) NOT NULL, " +
                "mapImage VARCHAR(256) NOT NULL, " +
                "childFriendliness INTEGER NOT NULL, " +
                "popularity INTEGER NOT NULL, " +
                "PRIMARY KEY(uid))";
        statement.executeUpdate(createSql);
        statement.close();
    }

}
