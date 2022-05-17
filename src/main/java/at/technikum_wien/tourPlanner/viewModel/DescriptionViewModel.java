package at.technikum_wien.tourPlanner.viewModel;


import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;


public class DescriptionViewModel {

    private TourService tourService;

    public DescriptionViewModel(TourService tourService) {
        this.tourService = tourService;
    }

    public ObservableList<Tour> getTours() {
        return tourService.getObservableTourList();
    }

    public void deleteTour(String name) {
        tourService.deleteTour(name);
    }

    public void saveReport(Tour tour) {
        tourService.saveReport(tour);
    }

}
