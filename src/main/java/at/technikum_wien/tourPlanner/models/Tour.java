package at.technikum_wien.tourPlanner.models;

import java.time.LocalTime;

public class Tour{
    private String uid;
    private String name;
    private String startingPoint;
    private String destination;
    private String duration;
    private String transportType;
    private String descriptionLong;
    private String descriptionShort;
    private int popularity;
    private float length;
    private int childFriendly;

    public Tour(String uid, String name, String startingPoint, String destination, String duration, String transportType, String descriptionLong, String descriptionShort, int popularity, float length, int childFriendly) {
        this.uid = uid;
        this.name = name;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.duration = duration;
        this.transportType = transportType;
        this.descriptionLong = descriptionLong;
        this.descriptionShort = descriptionShort;
        this.popularity = popularity;
        this.length = length;
        this.childFriendly = childFriendly;
    }

    public ListViewTour getListViewTour(){
        return new ListViewTour(getUid(), getName(), getStartingPoint(), getDestination(), getDuration());

    }

    @Override
    public String toString() {
        return "Tour{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", startingPoint='" + startingPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", duration='" + duration + '\'' +
                ", transportType='" + transportType + '\'' +
                ", descriptionLong='" + descriptionLong + '\'' +
                ", descriptionShort='" + descriptionShort + '\'' +
                ", popularity=" + popularity +
                ", length=" + length +
                ", childFriendly=" + childFriendly +
                '}';
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getChildFriendly() {
        return childFriendly;
    }

    public void setChildFriendly(int childFriendly) {
        this.childFriendly = childFriendly;
    }
}