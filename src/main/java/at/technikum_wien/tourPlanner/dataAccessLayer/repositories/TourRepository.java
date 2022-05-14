package at.technikum_wien.tourPlanner.dataAccessLayer.repositories;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableNames;
import at.technikum_wien.tourPlanner.models.Tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class TourRepository {

    private final String TABLE_NAME = TableNames.getTourTableName();
    private Connection connection;

    public TourRepository(Connection connection) {
        this.connection = connection;
    }

    public LinkedList<Tour> getTours() throws SQLException {
        PreparedStatement preparedStatement;
        LinkedList<Tour> returnList = new LinkedList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
        ResultSet resultSet = preparedStatement.executeQuery();
        int uid;
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
        while (resultSet.next()) {
            uid = resultSet.getInt(1);
            name = resultSet.getString(2);
            description = resultSet.getString(3);
            startingPoint = resultSet.getString(4);
            destination = resultSet.getString(5);
            distance = resultSet.getFloat(6);
            duration = resultSet.getString(7);
            transportType = resultSet.getString(8);
            mapImage = resultSet.getString(9);
            childFriendliness = resultSet.getInt(10);
            popularity = resultSet.getInt(11);
            returnList.add(new Tour(uid, name, startingPoint, destination, duration, transportType, description,
                    popularity, distance, childFriendliness, mapImage));
        }
        return returnList;

    }

    public void deleteTour(int uid) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE uid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, uid);
        preparedStatement.executeUpdate();
    }

    public void editTour(Tour tour) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET tourname = ?, description = ?, startingPoint = ?, destination = ?, " +
                "distance = ?, duration = ?, transporttype = ?, mapimage = ?, childfriendliness = ?, popularity = ? " +
                "WHERE uid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, tour.getName());
        preparedStatement.setString(2, tour.getDescription());
        preparedStatement.setString(3, tour.getStartingPoint());
        preparedStatement.setString(4, tour.getDestination());
        preparedStatement.setFloat(5, tour.getLength());
        preparedStatement.setString(6, tour.getDuration());
        preparedStatement.setString(7, tour.getTransportType());
        preparedStatement.setString(8, tour.getMapImage());
        preparedStatement.setInt(9, tour.getChildFriendly());
        preparedStatement.setInt(10, tour.getPopularity());
        preparedStatement.setInt(11, tour.getUid());
        preparedStatement.executeUpdate();
    }

    public int addTour(Tour tour) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "(tourname, description, startingpoint, destination, distance," +
                "duration, transporttype, mapimage, childfriendliness, popularity) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, tour.getName());
        preparedStatement.setString(2, tour.getDescription());
        preparedStatement.setString(3, tour.getStartingPoint());
        preparedStatement.setString(4, tour.getDestination());
        preparedStatement.setFloat(5, tour.getLength());
        preparedStatement.setString(6, tour.getDuration());
        preparedStatement.setString(7, tour.getTransportType());
        preparedStatement.setString(8, tour.getMapImage());
        preparedStatement.setInt(9, tour.getChildFriendly());
        preparedStatement.setInt(10, tour.getPopularity());
        preparedStatement.executeUpdate();

        // get serialized UID from database
        String getTourIdSql = "SELECT * FROM " + TABLE_NAME + " WHERE name = ?";
        PreparedStatement getTourIdStatement = connection.prepareStatement(getTourIdSql);
        getTourIdStatement.setString(1, tour.getName());
        ResultSet resultSet = getTourIdStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return -1;
    }

    //TODO: implement editTourChildFriendlinessAndPopularityById(int id, int childFriendliness, int popularity) -> this is called when adding a new log to a tour
}