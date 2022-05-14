package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.DatabaseConnector;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.LinkedList;

//public class DBProxy implements TourProvider, LogProvider {
public class DBProxy {
    DatabaseConnector db;
    LinkedList<TourSubscriber> tourSubscribers;
    LinkedList<LogSubscriber> logSubscribers;


    public DBProxy(DatabaseConnector db) {
        this.db = db;
        tourSubscribers = new LinkedList<>();
        logSubscribers = new LinkedList<>();
        try {
            db.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    @Override
//    public void subscribeToTours(TourSubscriber t) {
//        tourSubscribers.add(t);
//    }
//
//    @Override
//    public void subscribeToLogs(LogSubscriber t) {
//        logSubscribers.add(t);
//    }
//
//    @Override
//    public void unsubscribeTours(TourSubscriber t) {
//        tourSubscribers.remove(t);
//    }
//
//    @Override
//    public void unsubscribeLogs(LogSubscriber t) {
//        logSubscribers.remove(t);
//    }
//
//    @Override
    public void notifyTourSubscribers(ObservableList<Tour> l) {
        for (TourSubscriber t : tourSubscribers) {
            t.notify(l);
        }
    }
//
//    @Override
//    public void notifyLogSubscribers(LinkedList<TourLog> l) {
//        for (LogSubscriber t : logSubscribers) {
//            t.notify(l);
//        }
//    }

    public void getTours() {
        TourRepository tourRepository = new TourRepository(db.getConnection());
        try {
            notifyTourSubscribers(FXCollections.observableList(tourRepository.getTours()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<TourSubscriber> getTourSubscribers() {
        return tourSubscribers;
    }

    public LinkedList<LogSubscriber> getLogSubscribers() {
        return logSubscribers;
    }

}