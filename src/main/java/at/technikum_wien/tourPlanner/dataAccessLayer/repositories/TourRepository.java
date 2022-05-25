package at.technikum_wien.tourPlanner.dataAccessLayer.repositories;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableNames;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TransportMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class TourRepository {

    private final String TABLE_NAME = TableNames.getTourTableName();
    private Connection connection;
    private ObservableList<Tour> tours;

    public TourRepository(Connection connection) {
        this.connection = connection;
        try {
            this.tours = FXCollections.observableList(getTours());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Tour> getObservableTourList() {
        return this.tours;
    }
    public ObservableList<String> getObersavbleNameList() throws SQLException {
        return FXCollections.observableList(getTourNames());
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
            transportType = TransportMode.valueOf(resultSet.getString(8)); // TODO: change enum handling?
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

    public int getNextTourId() throws SQLException {
        int nextId = 0;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT MAX(uid) FROM " + TABLE_NAME);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            nextId = resultSet.getInt(1);
        }
        return nextId + 1;
    }

    public String getTourNameById(int id) throws SQLException{
        PreparedStatement preparedStatement;
        preparedStatement= connection.prepareStatement("SELECT tourname FROM " + TABLE_NAME + " WHERE uid = " + id);
        ResultSet rs=preparedStatement.executeQuery();
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }

    public LinkedList<String> getTourNames() throws SQLException {
        LinkedList<String> l= new LinkedList<>();
        PreparedStatement preparedStatement;
        preparedStatement= connection.prepareStatement("SELECT DISTINCT tourname FROM " + TABLE_NAME);
        ResultSet rs=preparedStatement.executeQuery();
        while (rs.next()){
            l.add(rs.getString(1));
        }
        return l;
    }

    public int getTourIdByName(String tourName) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement= connection.prepareStatement("select uid from tours where tourname = ?");
        preparedStatement.setString(1, tourName);
        ResultSet rs=preparedStatement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    //TODO: implement editTourChildFriendlinessAndPopularityById(int id, int childFriendliness, int popularity) -> this is called when adding a new log to a tour
}
