package at.technikum_wien.tourPlanner.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class TourLog {

    private String uid;
    private String tourID;
    private Date date;
    private String comment;
    private int difficulty;
    private String totalTime; //TODO: maybe change this data type
    private int rating;

    public TourLog(String uid, String tourID, Date date, String comment, int difficulty, String totalTime, int rating) {
        this.uid = uid;
        this.tourID = tourID;
        this.date = date;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
