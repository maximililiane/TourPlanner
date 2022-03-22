package at.technikum_wien.tourplanner.DataClasses;

import java.time.LocalDateTime;

public class Log {

    LocalDateTime date;
    String LogMessage;
    String TourID;

    public Log(String logMessage, String tourID) {
        LogMessage = logMessage;
        TourID = tourID;
        date= LocalDateTime.now();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLogMessage() {
        return LogMessage;
    }

    public void setLogMessage(String logMessage) {
        LogMessage = logMessage;
    }

    public String getTourID() {
        return TourID;
    }

    public void setTourID(String tourID) {
        TourID = tourID;
    }
}
