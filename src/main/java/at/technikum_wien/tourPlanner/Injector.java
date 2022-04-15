package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.database.DBAuthentication;
import at.technikum_wien.tourPlanner.database.DataBaseConnector;

public class Injector {
    public static DataBaseConnector getDBConnector(){
        return new DataBaseConnector();
    }
}
