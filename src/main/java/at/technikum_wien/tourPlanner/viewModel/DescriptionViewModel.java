package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public class DescriptionViewModel {

    private Injector injector;
    private TourService tourService;

    public DescriptionViewModel(TourService tourService) {
        this.tourService = tourService;
    }

//    public DescriptionViewModel(Injector injector) {
//        this.injector = injector;
//    }

//    public ObservableList<Tour> getTours() {
//        LinkedList<Tour> tours = injector.getProxy().getToursTemporary();
//        return FXCollections.observableArrayList(tours);
//    }

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
