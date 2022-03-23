package at.technikum_wien.tourPlanner.models;

import java.time.LocalTime;

public class Tour {
    String name;
    String uid;
    String startingPoint;
    String endPoint;
    String transportType;
    String descriptionLong;
    String descriptionShort;
    int popularity;
    LocalTime estimatedTime;
    double length;
    boolean childFriendly;

    public Tour(String name, String uid, String startingPoint, String endPoint, String transportType,
                String descriptionLong, String descriptionShort, int popularity, LocalTime estimatedTime,
                double length, boolean childFriendly) {
        this.name = name;
        this.uid = uid;
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.transportType = transportType;
        this.descriptionLong = descriptionLong;
        this.descriptionShort = descriptionShort;
        this.popularity = popularity;
        this.estimatedTime = estimatedTime;
        this.length = length;
        this.childFriendly = childFriendly;
    }

    public Tour() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
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

    public LocalTime getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(LocalTime estimatedTime) {
        this.estimatedTime = estimatedTime;
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
