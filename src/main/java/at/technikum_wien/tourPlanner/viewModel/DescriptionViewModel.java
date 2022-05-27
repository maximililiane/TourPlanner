package at.technikum_wien.tourPlanner.viewModel;


import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;

import java.sql.SQLException;


public class DescriptionViewModel {

    private final LoggerWrapper logger= LoggerFactory.getLogger();

    private TourService tourService;
    private TourLogService logService;

    public DescriptionViewModel(TourService tourService, TourLogService logService) {
        this.tourService = tourService;
        this.logService= logService;
    }

    public ObservableList<Tour> getTours() {
        return tourService.getObservableTourList();
    }

    public void deleteTour(String name) {
        logger.info("User tries to delete tour from database; TourName: " + name);
        tourService.deleteTour(name);
        try {
            logService.updateLogList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveReport(Tour tour) {
        logger.info("User tries to save a report");
        tourService.saveReport(tour);
    }

    public void saveSummaryReport(Tour tour) {
        logger.info("User tries to save a summary-report");
        tourService.saveSummaryReport(tour);
    }

}
