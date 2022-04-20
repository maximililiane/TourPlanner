package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.models.Log;
import at.technikum_wien.tourPlanner.models.Tour;

import java.util.LinkedList;

public interface TourPlannerProvider {

    public void subscribeToTours(TourSubscriber t);
    public void subscribeToLogs(LogSubscriber t);
    public void unsubscribeTours(TourSubscriber t);
    public void unsubscribeLogs(LogSubscriber t);
    public void notifyTourSubscribers(LinkedList<Tour> l);
    public void notifyLogSubscribers(LinkedList<Log> l);
}
