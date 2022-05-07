package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public interface TourSubscriber {
    // public void notify(LinkedList<Tour> l);
    public void notify(ObservableList<Tour> l);
}
