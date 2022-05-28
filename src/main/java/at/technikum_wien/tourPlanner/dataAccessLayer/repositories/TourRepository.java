package at.technikum_wien.tourPlanner.dataAccessLayer.repositories;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableName;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.models.TransportMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class TourRepository {

    private String TABLE_NAME;
    private Connection connection;
    private ObservableList<Tour> tours;

    public TourRepository(Connection connection) {
        this.connection = connection;
        this.tours = FXCollections.observableList(new LinkedList<Tour>());
    }

    public void setTableName(TableName tableName) {
        this.TABLE_NAME = tableName.getName();
        try {
            this.tours.addAll(getTours());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Tour> getObservableTourList() {
        return this.tours;
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
        double distance;
        TransportMode transportType;
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
            distance = resultSet.getDouble(6);
            duration = resultSet.getString(7);
            transportType = TransportMode.valueOf(resultSet.getString(8));
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
        preparedStatement.setDouble(5, tour.getLength());
        preparedStatement.setString(6, tour.getDuration());
        preparedStatement.setString(7, tour.getTransportType().name());
        preparedStatement.setString(8, tour.getMapImage());
        preparedStatement.setInt(9, tour.getChildFriendly());
        preparedStatement.setInt(10, tour.getPopularity());
        preparedStatement.setInt(11, tour.getUid());
        preparedStatement.executeUpdate();
    }

    public void addTour(Tour tour) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "(tourname, description, startingpoint, destination, distance," +
                "duration, transporttype, mapimage, childfriendliness, popularity) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, tour.getName());
        preparedStatement.setString(2, tour.getDescription());
        preparedStatement.setString(3, tour.getStartingPoint());
        preparedStatement.setString(4, tour.getDestination());
        preparedStatement.setDouble(5, tour.getLength());
        preparedStatement.setString(6, tour.getDuration());
        preparedStatement.setString(7, tour.getTransportType().name());
        preparedStatement.setString(8, tour.getMapImage());
        preparedStatement.setInt(9, tour.getChildFriendly());
        preparedStatement.setInt(10, tour.getPopularity());
        preparedStatement.executeUpdate();
    }

    public LinkedList<String> searchTours(String searchString) throws SQLException {
        String logTable = "";
        logTable = TABLE_NAME.equals("tours") ? "logs" : "demo_logs";
        String sql = "SELECT  tourName, description, startingPoint, destination, comment FROM " + TABLE_NAME
                + " JOIN " + logTable
                + " ON " + TABLE_NAME + ".uid=" + logTable + ".tourId"
                + " WHERE tourname like ? " +
                "OR description like ? OR startingPoint like ? OR destination like ? OR comment like ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + searchString + "%");
        preparedStatement.setString(2, "%" + searchString + "%");
        preparedStatement.setString(3, "%" + searchString + "%");
        preparedStatement.setString(4, "%" + searchString + "%");
        preparedStatement.setString(5, "%" + searchString + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        LinkedList<String> returnList = new LinkedList<>();
        while (resultSet.next()) {
            // format string for pattern "tourName - description - from - to - comment"
            String tourName = resultSet.getString(1);
            String description = resultSet.getString(2);
            String startingPoint = resultSet.getString(3);
            String destination = resultSet.getString(4);
            String descriptionString = description.length() > 24 ? description.substring(0, 24) + "..." : description;
            String comment = resultSet.getString(5) == null ? "" : resultSet.getString(5);
            String commentString = comment.length() > 24 ? comment.substring(0, 24) + "..." : comment;
            String listString = tourName + " - " + descriptionString + " - " + startingPoint + " - " + destination +
                    (commentString.isEmpty() ? "" : " - " + commentString);
            returnList.add(listString);
        }
        return returnList;
    }

    public int getNextTourId() throws SQLException {
        int nextId = 0;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("Select nextval(pg_get_serial_sequence('" + TABLE_NAME + "', 'uid')) as new_id;");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            nextId = resultSet.getInt(1);
        }
        return nextId + 1;
    }

    public String getTourNameById(int id) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT tourname FROM " + TABLE_NAME + " WHERE uid = " + id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public void updatePopularity(int tourId, int popularity) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET popularity = ? " +
                "WHERE uid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, popularity);
        preparedStatement.setInt(2, tourId);
        preparedStatement.executeUpdate();
    }

    public void updateChildFriendliness(int tourId, int childFriendliness) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET childfriendliness = ? " +
                "WHERE uid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, childFriendliness);
        preparedStatement.setInt(2, tourId);
        preparedStatement.executeUpdate();
    }

    public void deleteAllTours() throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

}
