package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.models.Log;
import at.technikum_wien.tourPlanner.models.Tour;

import java.util.LinkedList;

public interface TourPlannerProvider {

    public void subscribeToTours(TourPlannerSubscriber t);
    public void subscribeToLogs(TourPlannerSubscriber t);
    public void unsubscribeTours(TourPlannerSubscriber t);
    public void unsubscribeLogs(TourPlannerSubscriber t);
    public void notifyTourSubscribers(LinkedList<Tour> l);
    public void notifyLogSubscribers(LinkedList<Log> l);

}
