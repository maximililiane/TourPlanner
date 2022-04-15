package at.technikum_wien.tourPlanner.database;

public class DBAuthentication {
    public static String getDBLink(){
        return "jdbc:postgresql://localhost:5432/postgres";
    }
    public static String getDBUser(){
        return "postgres";
    }
    public static String getDBPassword(){
        return "";
    }
}
