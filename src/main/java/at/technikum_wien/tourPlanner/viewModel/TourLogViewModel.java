package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ObservableList;

public class TourLogViewModel {


    private final TourLogService tourLogService;
    private final TourService tourService;
    private final ObservableList<TourLog> logs;
    private final ObservableList<Tour> tours;
    private final LoggerWrapper logger = LoggerFactory.getLogger();


    public TourLogViewModel(TourLogService tourLogService, TourService tourService) {
        this.tourLogService = tourLogService;
        this.tourService = tourService;
        this.logs = tourLogService.getObservableLogList();
        this.tours = tourService.getObservableTourList();
    }

    public ObservableList<TourLog> getLogList() {
        return logs;
    }

    public ObservableList<Tour> getTourList() {
        return tours;
    }

    public void deleteLog(TourLog log) {
        logger.debug("User tries to delete log from database; LogID: " + log.getUid());
        tourLogService.deleteLog(log);
    }

    public ObservableList<String> getTourNames(){
        return tourService.getTourNames();
    }

    public int getTourIdByName(String filteredTour) {
        return tourService.getTourId(filteredTour);
    }
}
