package at.technikum_wien.tourPlanner.database;


import java.sql.*;

public class DataBaseSetup {

    public static void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(DBAuthentication.getDBLink(), DBAuthentication.getDBUser(), DBAuthentication.getDBPassword());
        try {
            createTourTable(connection);
            //TODO: createLogTable()
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    // TODO: change mapImage to link where mapImage is
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
                "mapImage BYTEA NOT NULL, " +
                "childFriendliness INTEGER NOT NULL, " +
                "popularity INTEGER NOT NULL, " +
                "PRIMARY KEY(uid))";
        statement.executeUpdate(createSql);
        statement.close();
    }
}
