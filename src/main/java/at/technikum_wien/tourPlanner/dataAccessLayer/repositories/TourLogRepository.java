package at.technikum_wien.tourPlanner.dataAccessLayer.repositories;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableNames;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;

import java.sql.*;
import java.util.LinkedList;

public class TourLogRepository {

    private Connection connection;
    private final String TABLE_NAME = TableNames.getLogTableName();

    public TourLogRepository(Connection connection) {
        this.connection = connection;
    }

    public LinkedList<TourLog> getLogs() throws SQLException {
        PreparedStatement preparedStatement;
        LinkedList<TourLog> returnList = new LinkedList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
        ResultSet resultSet = preparedStatement.executeQuery();
        int uid;
        int tourID;
        Date date;
        String comment;
        int difficulty;
        Time totalTime;
        int rating;
        while (resultSet.next()) {
            uid = resultSet.getInt(1);
            tourID = resultSet.getInt(2);
            date = resultSet.getDate(3);
            comment = resultSet.getString(4);
            difficulty = resultSet.getInt(5);
            totalTime = resultSet.getTime(6);
            rating = resultSet.getInt(7);
            returnList.add(new TourLog(uid, tourID, date, comment, difficulty, totalTime, rating));
        }
        return returnList;

    }

    //TODO: getLogById()
    //TODO: addLog()
    //TODO: editLog()
    //TODO: deleteLog()

}
