package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.models.Tour;

import java.util.LinkedList;

public interface TourPlannerSubscriber {
    public void notify(LinkedList<Tour> l);
}
