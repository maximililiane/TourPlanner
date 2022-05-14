package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourLogRepository;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.LinkedList;

public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private ObservableList<TourLog> logs;

    public TourLogService(TourLogRepository tourLogRepository) {
        this.tourLogRepository = tourLogRepository;
        this.logs= FXCollections.observableList(getLogs());
    }

    public LinkedList<TourLog> getLogs() {
        try {
            return tourLogRepository.getLogs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<TourLog> getObservableLogList(){
        return this.logs;
    }

}
