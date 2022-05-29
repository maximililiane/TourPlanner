package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.businessLayer.validation.TourInputValidation;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class AddLogWindowViewModel {

    private final TourLogService logService;
    private final TourService tourService;
    private final SimpleStringProperty commentField;
    private TourLog oldLog;
    private final LoggerWrapper logger = LoggerFactory.getLogger();


    public AddLogWindowViewModel(TourLogService logService, TourService tourService) {
        this.logService = logService;
        this.tourService = tourService;
        commentField = new SimpleStringProperty();
    }

    public void setOldLog(TourLog l) {
        this.oldLog = l;
    }

    public void addLog(int hours, int minutes, LocalDate date, int rating, String tourName, int difficulty) {
        TourLog l = createLog(hours, minutes, date, rating, tourName, difficulty);
        logger.debug("User tries to add log to database; Log: " + l);
        logService.addTourLog(l);
    }

    public void editLog(int hours, int minutes, LocalDate date, int rating, String tourName, int difficulty) {
        TourLog l = createLog(hours, minutes, date, rating, tourName, difficulty);
        if (l != null) {
            l.setUid(oldLog.getUid());
            logger.debug("User tries to edit log in database; LogID: " + l.getUid());
            logService.editTourLog(l);
        }
    }

    private TourLog createLog(int hours, int minutes, LocalDate date, int rating, String tourName, int difficulty) {
        TourLog l = new TourLog();
        int tourId = getTourID(tourName);
        l.setTourID(tourId);
        l.setTotalTime(Time.valueOf(LocalTime.of(hours, minutes)));
        l.setDate(Date.valueOf(date));
        l.setRating(rating);
        l.setDifficulty(difficulty);
        l.setComment(getCommentField().get());
        return l;
    }

    public int getTourID(String tourName) {
        return tourService.getTourId(tourName);
    }

    public SimpleStringProperty getCommentField() {
        return commentField;
    }

    public ObservableList<String> getTourNames() {
        return tourService.getTourNames();
    }


}
