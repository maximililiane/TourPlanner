package at.technikum_wien.tourPlanner.LogViewUtils;

import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.FormatStyle;

public class LogViewRow {
    private SimpleIntegerProperty uid;
    private SimpleIntegerProperty tourUID;
    private Date date;
    private SimpleStringProperty comment;
    private SimpleIntegerProperty difficulty;
    private Time totalTime;
    private SimpleIntegerProperty rating;
    private SimpleStringProperty tourName;

    public LogViewRow(TourLog l, String tourName) {
        uid = new SimpleIntegerProperty(l.getUid());
        tourUID = new SimpleIntegerProperty(l.getTourID());
        comment = new SimpleStringProperty(l.getComment());
        difficulty = new SimpleIntegerProperty(l.getDifficulty());
        rating = new SimpleIntegerProperty(l.getRating());
        this.tourName = new SimpleStringProperty(tourName);
        totalTime = l.getTotalTime();
        date = l.getDate();


    }

    public TourLog getLog() {
        TourLog l = new TourLog();
        l.setUid(uid.get());
        l.setTourID(tourUID.get());
        l.setComment(comment.get());
        l.setDifficulty(difficulty.get());
        l.setRating(rating.get());
        l.setTotalTime(totalTime);
        l.setDate(date);
        return l;
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
                ", tourName=" + tourName +
                '}';
    }

    public int getUid() {
        return uid.get();
    }

    public SimpleIntegerProperty uidProperty() {
        return uid;
    }

    public int getTourUID() {
        return tourUID.get();
    }

    public SimpleIntegerProperty tourUIDProperty() {
        return tourUID;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public int getDifficulty() {
        return difficulty.get();
    }

    public SimpleIntegerProperty difficultyProperty() {
        return difficulty;
    }

    public Time getTotalTime() {
        return totalTime;
    }

    public int getRating() {
        return rating.get();
    }

    public SimpleIntegerProperty ratingProperty() {
        return rating;
    }

    public String getTourName() {
        return tourName.get();
    }

    public SimpleStringProperty tourNameProperty() {
        return tourName;
    }
}
