package at.technikum_wien.tourPlanner.models;

import java.time.LocalTime;

public class Tour extends ListViewTour{
    String transportType;
    String descriptionLong;
    String descriptionShort;
    int popularity;
    float length;
    int childFriendly;

    public Tour(String uid, String name, String startingPoint, String destination, String duration,  String transportType, String descriptionLong, String descriptionShort, int popularity, float length, int childFriendly) {
        super(uid, name, startingPoint, destination, duration);
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

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int isChildFriendly() {
        return childFriendly;
    }

    public void setChildFriendly(int childFriendly) {
        this.childFriendly = childFriendly;
    }

    public ListViewTour getListViewTour(){
        return new ListViewTour(getUid(), getName(), getStartingPoint(), getDestination(), getDuration());
    }
}