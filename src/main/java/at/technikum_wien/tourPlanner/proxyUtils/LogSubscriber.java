package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.models.Log;

import java.util.LinkedList;

public interface LogSubscriber {
    public void notify(LinkedList<Log> l);
}

