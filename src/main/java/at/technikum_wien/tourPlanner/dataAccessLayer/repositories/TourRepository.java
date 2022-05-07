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
        while (resultSet.next()) {
            uid = resultSet.getString(1);
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

    public void deleteTour(String name) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE tourname = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
    }

    //TODO: implement getTourById(String id)
    //TODO: implement editTour(Tour tour)
    //TODO: implement deleteTourById(String id)
    //TODO: implement addTour(Tour tour)
    //TODO: implement editTourChildFriendlinessAndPopularityById(int id, int childFriendliness, int popularity) -> this is called when adding a new log to a tour
}
