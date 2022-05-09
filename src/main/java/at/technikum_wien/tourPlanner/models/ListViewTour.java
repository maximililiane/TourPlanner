package at.technikum_wien.tourPlanner.models;

public class ListViewTour {
    private int uid;
    private String name;
    private String startingPoint;
    private String destination;
    private String duration;

    public ListViewTour(int uid, String name, String startingPoint, String destination, String duration) {
        this.uid = uid;
        this.name = name;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.duration = duration;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
