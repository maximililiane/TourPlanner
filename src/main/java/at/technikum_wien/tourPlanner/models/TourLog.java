package at.technikum_wien.tourPlanner.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class TourLog {

    private int uid;
    private int tourID;
    private String date; //TODO: change type
    private String comment;
    private int difficulty;
    private String totalTime; //TODO: maybe change this data type
    private int rating;

    public TourLog(int uid, int tourID, String date, String comment, int difficulty, String totalTime, int rating) {
        this.uid = uid;
        this.tourID = tourID;
        this.date = date;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
