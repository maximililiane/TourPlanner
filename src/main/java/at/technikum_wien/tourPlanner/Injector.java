package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.configuration.Configuration;
import at.technikum_wien.tourPlanner.configuration.PropertyFactory;
import at.technikum_wien.tourPlanner.database.DatabaseConnector;
import at.technikum_wien.tourPlanner.proxyUtils.DBProxy;

public class Injector {
    DatabaseConnector db;
    DBProxy proxy;

    public Injector() {
        proxy = new DBProxy(Injector.getDBConnector());
    }

    public static DatabaseConnector getDBConnector() {
        return new DatabaseConnector();
    }


    public DBProxy getProxy() {
        return proxy;
    }

    public static Configuration getConfig(String env) {
        return PropertyFactory.getConfiguration(env);
    }
}
