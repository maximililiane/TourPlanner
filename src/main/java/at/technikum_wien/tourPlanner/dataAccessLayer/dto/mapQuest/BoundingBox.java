package at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest;

import com.fasterxml.jackson.annotation.JsonAlias;

public class BoundingBox {

    @JsonAlias({"ul"})
    private Ul upperLeft;
    @JsonAlias({"lr"})
    private Lr lowerRight;

    public BoundingBox() {
    }

    public BoundingBox(Ul upperLeft, Lr lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
    }

    public Ul getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Ul upperLeft) {
        this.upperLeft = upperLeft;
    }

    public Lr getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(Lr lowerRight) {
        this.lowerRight = lowerRight;
    }

    @Override
    public String toString() {
        return "boundingBox=" + upperLeft.getLat() + "," + upperLeft.getLng() + "," +
                lowerRight.getLat() + "," + lowerRight.getLng();
    }
}

class Ul {
    @JsonAlias({"lng"})
    private double lng;
    @JsonAlias({"lat"})
    private double lat;

    public Ul() {
    }

    public Ul(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Ul{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}

class Lr {
    @JsonAlias({"lng"})
    private double lng;
    @JsonAlias({"lat"})
    private double lat;

    public Lr() {
    }

    public Lr(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Lr{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
