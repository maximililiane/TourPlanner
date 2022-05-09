package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public interface TourProvider {
    public void subscribeToTours(TourSubscriber t);

    public void unsubscribeTours(TourSubscriber t);

    public void notifyTourSubscribers(ObservableList<Tour> l);
}
