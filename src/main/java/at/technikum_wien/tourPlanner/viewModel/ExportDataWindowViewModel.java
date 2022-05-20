package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;

public class ExportDataWindowViewModel {

    private final TourService tourService;
    private final TourLogService tourLogService;

    public ExportDataWindowViewModel(TourService tourService, TourLogService tourLogService) {
        this.tourService = tourService;
        this.tourLogService = tourLogService;
    }

    public ObservableList<Tour> getTours() {
        return tourService.getObservableTourList();
    }

    public void exportData(Tour tour) {
        tour.setLogs(tourLogService.getLogsByTourId(tour.getUid()));
        tourService.exportData(tour);
    }
}
