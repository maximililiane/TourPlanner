package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.models.TourLog;

import java.util.LinkedList;

public interface LogProvider {
    public void subscribeToLogs(LogSubscriber t);

    public void unsubscribeLogs(LogSubscriber t);

    public void notifyLogSubscribers(LinkedList<TourLog> l);
}
