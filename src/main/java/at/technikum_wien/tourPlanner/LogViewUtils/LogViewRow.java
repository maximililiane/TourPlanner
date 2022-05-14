package at.technikum_wien.tourPlanner.LogViewUtils;

import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LogViewRow {
    private SimpleIntegerProperty uid;
    private SimpleIntegerProperty tourUID;
    private SimpleStringProperty date;
    private SimpleStringProperty comment;
    private SimpleIntegerProperty difficulty;
    private SimpleStringProperty totalTime;
    private SimpleIntegerProperty rating;

    public LogViewRow(TourLog l) {
        uid= new SimpleIntegerProperty(l.getUid());
        tourUID= new SimpleIntegerProperty(l.getTourID());
        date= new SimpleStringProperty(l.getDate());
        comment= new SimpleStringProperty(l.getComment());
        difficulty= new SimpleIntegerProperty(l.getDifficulty());
        totalTime= new SimpleStringProperty(l.getTotalTime());
        rating= new SimpleIntegerProperty(l.getRating());
    }

    @Override
    public String toString() {
        return "LogViewRow{" +
                "uid=" + uid +
                ", tourUID=" + tourUID +
                ", date=" + date +
                ", comment=" + comment +
                ", difficulty=" + difficulty +
                ", totalTime=" + totalTime +
                ", rating=" + rating +
                '}';
    }

    public int getUid() {
        return uid.get();
    }

    public SimpleIntegerProperty uidProperty() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid.set(uid);
    }

    public int getTourUID() {
        return tourUID.get();
    }

    public SimpleIntegerProperty tourUIDProperty() {
        return tourUID;
    }

    public void setTourUID(int tourUID) {
        this.tourUID.set(tourUID);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public int getDifficulty() {
        return difficulty.get();
    }

    public SimpleIntegerProperty difficultyProperty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty.set(difficulty);
    }

    public String getTotalTime() {
        return totalTime.get();
    }

    public SimpleStringProperty totalTimeProperty() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime.set(totalTime);
    }

    public int getRating() {
        return rating.get();
    }

    public SimpleIntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }
}
