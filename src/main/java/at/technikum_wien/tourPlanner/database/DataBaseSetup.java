package at.technikum_wien.tourPlanner.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseSetup {
    /*public static void setUp() throws SQLException {
        Connection connection= DriverManager.getConnection(DBAuthentication.getDBLink(), DBAuthentication.getDBUser(), DBAuthentication.getDBPassword());
        createUserTable(connection);
        connection.close();
    }

    private static void createUserTable(Connection connection){
        PreparedStatement ps;
        try{
            ps=connection.prepareStatement("CREATE TABLE " + TableNames.getUserListTableName() +" (username varchar(255) not null , password varchar(255) not null, coins int default 20, elo int default 100, wins int default 0, losses int default 0, battleready int default 0, collection varchar(255), token varchar(255), name varchar(255) default 'EMPTY_NAME', bio varchar(255) default 'EMPTY BIO', image varchar(255) default 'EMPTY IMAGE')");
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            ps=connection.prepareStatement("CREATE UNIQUE INDEX users_username_uindex ON users (username);");
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }*/
}
