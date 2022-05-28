package at.technikum_wien.tourPlanner.models;

import at.technikum_wien.tourPlanner.models.serializers.TimeDeserializer;
import at.technikum_wien.tourPlanner.models.serializers.TimeSerializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Date;
import java.sql.Time;


public class TourLog {

    @JsonIgnore
    private int uid;
    @JsonIgnore
    private int tourID;
    @JsonAlias({"date"})
    private Date date;
    @JsonAlias({"comment"})
    private String comment;
    @JsonAlias({"difficulty"})
    private int difficulty;

    @JsonAlias({"totalTime"})
    @JsonSerialize(using = TimeSerializer.class)
    @JsonDeserialize(using = TimeDeserializer.class)
    private Time totalTime; // stored in the format of hh:mm
    @JsonAlias({"rating"})
    private int rating;

    public TourLog() {
    }

    public TourLog(int uid, int tourID, Date date, String comment, int difficulty, Time totalTime, int rating) {
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

    public Time getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Time totalTime) {
        this.totalTime = totalTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "TourLog{" +
                "uid=" + uid +
                ", tourID=" + tourID +
                ", date='" + date + '\'' +
                ", comment='" + comment + '\'' +
                ", difficulty=" + difficulty +
                ", totalTime='" + totalTime + '\'' +
                ", rating=" + rating +
                '}';
    }
}
