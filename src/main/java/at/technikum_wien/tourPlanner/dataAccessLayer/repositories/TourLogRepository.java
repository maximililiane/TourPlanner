package at.technikum_wien.tourPlanner.dataAccessLayer.repositories;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableNames;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class TourLogRepository {

    private Connection connection;
    private final String TABLE_NAME= TableNames.getLogTableName();

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
        String date;
        String comment;
        int difficulty;
        String totalTime;
        int rating;
        while (resultSet.next()) {
            uid = resultSet.getInt(1);
            tourID = resultSet.getInt(2);
            date = resultSet.getString(3);
            comment = resultSet.getString(4);
            difficulty = resultSet.getInt(5);
            totalTime = resultSet.getString(6);
            rating = resultSet.getInt(7);
            returnList.add(new TourLog(uid,tourID,date,comment,difficulty,totalTime,rating));
        }
        return returnList;

    }
    //TODO: getLogById()
    //TODO: editLog()
    //TODO: deleteLog()

}
