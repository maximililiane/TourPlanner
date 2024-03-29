package at.technikum_wien.tourPlanner.dataAccessLayer.repositories;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableName;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.LinkedList;

public class TourLogRepository {

    private Connection connection;
    private String TABLE_NAME;
    private ObservableList<TourLog> logs;

    public TourLogRepository(Connection connection) {
        this.connection = connection;
        this.logs = FXCollections.observableList(new LinkedList<TourLog>());
    }

    public void setTableName(TableName tableName) {
        this.TABLE_NAME = tableName.getName();
        try {
            logs.addAll(getLogs());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<TourLog> getObservableLogList() {
        return this.logs;
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

    public int getNewestLogId() throws SQLException {
        int nextId = 0;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("Select nextval(pg_get_serial_sequence('" + TABLE_NAME + "', 'uid')) as new_id;");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            nextId = resultSet.getInt(1);
        }
        return nextId + 1;
    }

    public void addLog(TourLog l) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "(tourid, date, comment, difficulty, totaltime," +
                "rating) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, l.getTourID());
        preparedStatement.setDate(2, l.getDate());
        preparedStatement.setString(3, l.getComment());
        preparedStatement.setInt(4, l.getDifficulty());
        preparedStatement.setTime(5, l.getTotalTime());
        preparedStatement.setInt(6, l.getRating());
        preparedStatement.executeUpdate();

    }

    public void editLog(TourLog l) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET tourid = ?, " +
                "date = ?, " +
                "comment = ?," +
                "difficulty = ?, " +
                "totaltime = ?," +
                "rating = ?" +
                "WHERE uid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, l.getTourID());
        preparedStatement.setDate(2, l.getDate());
        preparedStatement.setString(3, l.getComment());
        preparedStatement.setInt(4, l.getDifficulty());
        preparedStatement.setTime(5, l.getTotalTime());
        preparedStatement.setInt(6, l.getRating());
        preparedStatement.setInt(7, l.getUid());
        preparedStatement.executeUpdate();

    }

    public void deleteLog(int uid) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE uid= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, uid);
        preparedStatement.executeUpdate();
    }

    public void resetLogTable() throws SQLException {
        String sql = "DROP TABLE " + TABLE_NAME + " CASCADE";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

        Statement statement = connection.createStatement();
        String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "uid SERIAL NOT NULL, " +
                "tourId INTEGER NOT NULL, " +
                "date DATE NOT NULL, " +
                "comment VARCHAR(256), " +
                "difficulty INTEGER NOT NULL, " +
                "totalTime VARCHAR(256) NOT NULL, " +
                "rating INTEGER NOT NULL, " +
                "PRIMARY KEY(uid), " +
                "CONSTRAINT tourIdForeignKey FOREIGN KEY(tourId) REFERENCES demo_tours(uid) ON DELETE CASCADE)";
        statement.executeUpdate(createSql);
        statement.close();
    }

}
