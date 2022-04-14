package at.technikum_wien.tourPlanner.models;

import java.time.LocalTime;

public class ListViewTour {
    private String name;
    private String startingPoint;
    private String destination;
    private String estimatedTime;
    private String uid;

    public ListViewTour(String name, String startingPoint, String destination, String duration, String uid) {
        this.name = name;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.estimatedTime = duration;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
