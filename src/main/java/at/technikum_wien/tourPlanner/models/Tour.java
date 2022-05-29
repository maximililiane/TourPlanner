package at.technikum_wien.tourPlanner.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

public class Tour {
    @JsonIgnore
    private int uid;
    @JsonAlias("name")
    private String name;
    @JsonAlias("from")
    private String startingPoint;
    @JsonAlias("to")
    private String destination;
    @JsonAlias("duration")
    private String duration;
    @JsonAlias("transportType")
    private TransportMode transportType;
    @JsonAlias("description")
    private String description;
    @JsonAlias("popularity")
    private int popularity; // popularity is defined by how many log entries a tour has
    @JsonAlias("length")
    private double length;
    @JsonAlias("childFriendliness")
    private int childFriendly;
    @JsonAlias("mapImage")
    private String mapImage;
    @JsonAlias("logs")
    private List<TourLog> logs;

    public Tour() {
    }

    public Tour(int uid, String name, String startingPoint, String destination, String duration,
                TransportMode transportType, String description, int popularity, double length, int childFriendly,
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
        this.logs = new LinkedList<TourLog>();
    }

    public Tour(int uid, String name, String startingPoint, String destination, String duration,
                TransportMode transportType, String description, int popularity, double length, int childFriendly,
                String mapImage, List<TourLog> logs) {
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
        this.logs = logs;
    }

    // used when adding new tour via gui
    public Tour(String name, String startingPoint, String destination, TransportMode transportType,
                String description) {
        this.name = name;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.transportType = transportType;
        this.description = description;
        this.logs = new LinkedList<TourLog>();
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

    public TransportMode getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportMode transportType) {
        this.transportType = transportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public void setPopularity() {
        this.popularity = logs.size();
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
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

    public List<TourLog> getLogs() {
        return logs;
    }

    public void setLogs(List<TourLog> logs) {
        this.logs = logs;
    }

    public void insertLog(TourLog log) {
        if (!this.logs.stream().anyMatch(l -> l.getUid() == log.getUid())) {
            this.logs.add(log);
        }
    }

    public void deleteLog(TourLog log) {
        TourLog tourLog = this.logs.stream().filter(l -> l.getUid() == log.getUid()).findFirst().get();
        this.logs.remove(tourLog);
    }

    public void updateLog(TourLog newLog) {
        TourLog oldLog = this.logs.stream().filter(l -> l.getUid() == newLog.getUid()).findFirst().get();
        int index = logs.indexOf(oldLog);
        this.logs.set(index, newLog);
    }

    @JsonIgnore
    public ListViewTour getListViewTour() {
        return new ListViewTour(uid, name, startingPoint, destination, duration);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", startingPoint='" + startingPoint + '\'' +
                ", destination='" + destination + '\'' +
                ", duration='" + duration + '\'' +
                ", transportType=" + transportType +
                ", description='" + description + '\'' +
                ", popularity=" + popularity +
                ", length=" + length +
                ", childFriendly=" + childFriendly +
                ", mapImage='" + mapImage + '\'' +
                ", logs=" + logs +
                '}';
    }
}