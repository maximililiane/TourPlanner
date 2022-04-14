package at.technikum_wien.tourPlanner.listViewUtils;

import at.technikum_wien.tourPlanner.models.ListViewTour;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

public class ListViewRow {

    private SimpleStringProperty name;
    private SimpleStringProperty startingPoint;
    private SimpleStringProperty destination;
    private SimpleStringProperty estimatedTime;
    private SimpleStringProperty uid;

    public ListViewRow(ListViewTour t) {
        this.name = new SimpleStringProperty(t.getName());
        this.startingPoint = new SimpleStringProperty(t.getStartingPoint());
        this.destination = new SimpleStringProperty(t.getDestination());
        this.estimatedTime = new SimpleStringProperty(t.getEstimatedTime());
        this.uid = new SimpleStringProperty(t.getUid());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getStartingPoint() {
        return startingPoint.get();
    }

    public SimpleStringProperty startingPointProperty() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint.set(startingPoint);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getEstimatedTime() {
        return estimatedTime.get();
    }

    public SimpleStringProperty estimatedTimeProperty() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime.set(estimatedTime);
    }

    public String getUid() {
        return uid.get();
    }

    public SimpleStringProperty uidProperty() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid.set(uid);
    }

}
