package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.database.DBAuthentication;
import at.technikum_wien.tourPlanner.database.DataBaseConnector;
import at.technikum_wien.tourPlanner.proxyUtils.DBProxy;

public class Injector {
    DataBaseConnector db;
    DBProxy proxy;

    public Injector() {
        proxy= new DBProxy();
    }

    public static DataBaseConnector getDBConnector() {
        return new DataBaseConnector();
    }


    public DBProxy getProxy() {
        return proxy;
    }
}
