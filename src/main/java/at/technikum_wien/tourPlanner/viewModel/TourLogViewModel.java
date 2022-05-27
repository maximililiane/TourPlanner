package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ObservableList;

public class TourLogViewModel {

    private TourLogService tourLogService;
    private TourService tourService;
    private ObservableList<TourLog> logs;
    private ObservableList<Tour> tours;
    private final LoggerWrapper logger = LoggerFactory.getLogger();


    public TourLogViewModel(TourLogService tourLogService, TourService tourService) {
        this.tourLogService = tourLogService;
        this.tourService = tourService;
        this.logs = tourLogService.getObservableLogList();
        this.tours = tourLogService.getObservableTourList();
    }

    public ObservableList<TourLog> getLogList() {
        return logs;
    }

    public ObservableList<Tour> getTourList() {
        return tours;
    }

    public void setList(ObservableList<TourLog> logs) {
        this.logs = logs;
    }


    public String getTourNameById(int id) {
        return tourService.getTourNameById(id);
    }

    public void deleteLog(TourLog log) {
        logger.info("User tries to delete log from database; LogID: " + log.getUid());
        tourLogService.deleteLog(log);
    }
}
