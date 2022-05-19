package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;

public class ExportDataWindowViewModel {

    private final TourService tourService;

    public ExportDataWindowViewModel(TourService tourService) {
        this.tourService = tourService;
    }

    public ObservableList<Tour> getTours() {
        return tourService.getObservableTourList();
    }

    public void exportData(Tour tour) {
        tourService.exportData(tour);
    }
}
