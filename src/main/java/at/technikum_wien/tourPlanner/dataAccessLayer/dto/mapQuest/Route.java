package at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Route {
    @JsonAlias({"boundingBox"})
    private BoundingBox boundingBox;
    @JsonAlias({"distance"})
    private double distance;
    @JsonAlias({"sessionId"})
    private String sessionId;
    @JsonAlias({"formattedTime"})
    private String formattedTime;

    public Route() {
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    @Override
    public String toString() {
        return "Route{" +
                "boundingBox=" + boundingBox +
                ", distance=" + distance +
                ", sessionId='" + sessionId + '\'' +
                ", formattedTime='" + formattedTime + '\'' +
                '}';
    }
}
