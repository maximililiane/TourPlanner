package at.technikum_wien.tourPlanner.database;

import at.technikum_wien.tourPlanner.models.Tour;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.LinkedList;

public class DataBaseConnector {
    private Connection connection;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public DataBaseConnector(){
        this.DB_URL=DBAuthentication.getDBLink();
        this.DB_USER=DBAuthentication.getDBUser();
        this.DB_PASSWORD=DBAuthentication.getDBPassword();
    }

    public void connect() throws SQLException{
        this.connection= DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    public void disconnect() throws SQLException{
        this.connection.close();
    }

    public LinkedList<Tour> getTours() throws SQLException{
        PreparedStatement ps;
        LinkedList<Tour> l= new LinkedList<>();
        ps=connection.prepareStatement("SELECT * FROM "+ TableNames.getTourTableName());
        ResultSet rs= ps.executeQuery();
        String uid;
        String name;
        String startingPoint;
        String destination;
        String duration;
        String transportType;
        String descLong;
        String descShort;
        int popularity;
        float length;
        int childFriendly;
        while(rs.next()){
            uid=rs.getString(1);
            name=rs.getString(2);
            startingPoint=rs.getString(3);
            destination=rs.getString(4);
            duration=rs.getString(5);
            transportType=rs.getString(6);
            descLong=rs.getString(7);
            descShort=rs.getString(8);
            popularity=rs.getInt(9);
            length=rs.getFloat(10);
            childFriendly=rs.getInt(11);
            System.out.println("name: " + name);
            l.add(new Tour(uid, name, startingPoint, destination, duration, transportType, descLong, descShort, popularity, length, childFriendly));
        }
        return l;
    }

}
