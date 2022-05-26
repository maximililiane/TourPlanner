package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.configuration.Configuration;
import at.technikum_wien.tourPlanner.configuration.PropertyFactory;
import at.technikum_wien.tourPlanner.dataAccessLayer.database.DatabaseConnector;

public class Injector {
    private DatabaseConnector db;


    public Injector() {

    }

    public static DatabaseConnector getDBConnector() {
        return new DatabaseConnector();
    }



    public static Configuration getConfig(String env) {
        return PropertyFactory.getConfiguration(env);
    }
}
