package at.technikum_wien.tourPlanner.models;

import java.time.LocalTime;

public class Tour extends ListViewTour {
    String transportType;
    String description;
    int popularity;
    float length;
    int childFriendly;
    byte[] mapImage;

    public Tour(String uid, String name, String description, String startingPoint, String destination, float length,
                String duration, String transportType, byte[] mapImage, int childFriendly, int popularity) {
        super(uid, name, startingPoint, destination, duration);
        this.transportType = transportType;
        this.description = description;
        this.popularity = popularity;
        this.length = length;
        this.childFriendly = childFriendly;
        this.mapImage = mapImage;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getChildFriendly() {
        return childFriendly;
    }

    public byte[] getMapImage() {
        return mapImage;
    }

    public void setMapImage(byte[] mapImage) {
        this.mapImage = mapImage;
    }

    public ListViewTour getListViewTour() {
        return new ListViewTour(getUid(), getName(), getStartingPoint(), getDestination(), getDuration());
    }
}