package at.technikum_wien.tourPlanner.models;

import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalTime;

public class Tour {
    private int uid;
    private String name;
    private String startingPoint;
    private String destination;
    private String duration;
    private String transportType;
    private String description;
    private int popularity;
    private float length;
    private int childFriendly;
    private String mapImage;


    public Tour(int uid, String name, String startingPoint, String destination, String duration,
                String transportType, String description, int popularity, float length, int childFriendly,
                String mapImage) {
        this.uid = uid;
        this.name = name;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.duration = duration;
        this.transportType = transportType;
        this.description = description;
        this.popularity = popularity;
        this.length = length;
        this.childFriendly = childFriendly;
        this.mapImage = mapImage;
    }

    // used when adding new tour via gui
    public Tour(String name, String startingPoint, String destination, String duration, String transportType,
                String description, float length) {
        this.name = name;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.duration = duration;
        this.transportType = transportType;
        this.description = description;
        this.length = length;
        this.popularity = 0; // popularity = 0 because it doesn't have any logs yet
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

    public int getChildFriendly() {
        return childFriendly;
    }

    public void setChildFriendly(int childFriendly) {
        this.childFriendly = childFriendly;
    }

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public ListViewTour getListViewTour() {
        return new ListViewTour(uid, name, startingPoint, destination, duration);
    }
}