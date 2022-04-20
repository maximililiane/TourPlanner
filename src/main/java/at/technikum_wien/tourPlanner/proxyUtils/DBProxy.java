package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.database.DataBaseConnector;
import at.technikum_wien.tourPlanner.models.Log;
import at.technikum_wien.tourPlanner.models.Tour;

import java.sql.SQLException;
import java.util.LinkedList;

public class DBProxy implements TourPlannerProvider{
    DataBaseConnector db;
    LinkedList<TourPlannerSubscriber> tourSubscribers;
    LinkedList<TourPlannerSubscriber> logSubscribers;


    public DBProxy() {
        this.db= Injector.getDBConnector();
        tourSubscribers= new LinkedList<>();
        logSubscribers= new LinkedList<>();
        try {
            db.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    @Override
    public void subscribeToTours(TourPlannerSubscriber t) {
        tourSubscribers.add(t);
    }

    @Override
    public void subscribeToLogs(TourPlannerSubscriber t) {
        logSubscribers.add(t);
    }

    @Override
    public void unsubscribeTours(TourPlannerSubscriber t) {
        tourSubscribers.remove(t);
    }
    @Override
    public void unsubscribeLogs(TourPlannerSubscriber t){
        logSubscribers.remove(t);
    }

    @Override
    public void notifyTourSubscribers(LinkedList<Tour> l) {
        for(TourPlannerSubscriber t : tourSubscribers){
            t.notify(l);
        }
    }

    @Override
    public void notifyLogSubscribers(LinkedList<Log> l) {
        for (TourPlannerSubscriber t : logSubscribers){
            t.notify();
        }
    }

    public void getTours(){
        try {
            notifyTourSubscribers(db.getTours());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
