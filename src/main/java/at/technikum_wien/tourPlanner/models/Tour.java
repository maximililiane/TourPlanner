package at.technikum_wien.tourPlanner.models;

import java.time.LocalTime;

public class Tour extends ListViewTour{
    String transportType;
    String descriptionLong;
    String descriptionShort;
    int popularity;
    double length;
    boolean childFriendly;

    public Tour(String name, String startingPoint, String destination, LocalTime duration, String uid, String transportType, String descriptionLong, String descriptionShort, int popularity, double length, boolean childFriendly) {
        super(name, startingPoint, destination, duration, uid);
        this.transportType = transportType;
        this.descriptionLong = descriptionLong;
        this.descriptionShort = descriptionShort;
        this.popularity = popularity;
        this.length = length;
        this.childFriendly = childFriendly;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public boolean isChildFriendly() {
        return childFriendly;
    }

    public void setChildFriendly(boolean childFriendly) {
        this.childFriendly = childFriendly;
    }
}