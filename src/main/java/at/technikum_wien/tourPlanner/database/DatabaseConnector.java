package at.technikum_wien.tourPlanner.database;

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

    //TODO: move to TourRepository class for repository pattern
    public LinkedList<Tour> getTours() throws SQLException {
        PreparedStatement ps;
        LinkedList<Tour> l = new LinkedList<>();
        ps = connection.prepareStatement("SELECT * FROM " + TableNames.getTourTableName());
        ResultSet rs = ps.executeQuery();
        String uid;
        String name;
        String startingPoint;
        String destination;
        String duration;
        float distance;
        String transportType;
        String description;
        String mapImage;
        int popularity;
        int childFriendliness;
        while (rs.next()) {
            uid = rs.getString(1);
            name = rs.getString(2);
            description = rs.getString(3);
            startingPoint = rs.getString(4);
            destination = rs.getString(5);
            distance = rs.getFloat(6);
            duration = rs.getString(7);
            transportType = rs.getString(8);
            mapImage = rs.getString(9);
            childFriendliness = rs.getInt(10);
            popularity = rs.getInt(11);
            l.add(new Tour(uid, name, startingPoint, destination, duration, transportType, description, popularity,
                    distance, childFriendliness, mapImage));
        }
        return l;

    }

}