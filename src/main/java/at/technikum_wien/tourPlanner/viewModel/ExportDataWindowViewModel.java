package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;

public class ExportDataWindowViewModel {

    private final TourService tourService;
    private final TourLogService tourLogService;

    private final LoggerWrapper logger = LoggerFactory.getLogger();

    public ExportDataWindowViewModel(TourService tourService, TourLogService tourLogService) {
        this.tourService = tourService;
        this.tourLogService = tourLogService;
    }

    public ObservableList<Tour> getTours() {
        return tourService.getObservableTourList();
    }

    public void exportData(Tour tour) {
        logger.info("User tries to export tour data for tour: " + tour.getName());
        tour.setLogs(tourLogService.getLogsByTourId(tour.getUid()));

        tourService.exportData(tour);
    }
}
