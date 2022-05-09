package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.LinkedList;

public class TourService {

    private TourRepository tourRepository;
    private ObservableList<Tour> tours;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
        this.tours = FXCollections.observableList(getTours());
    }

    public LinkedList<Tour> getTours() {
        try {
            return tourRepository.getTours();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteTour(String name) {
        if (tours.stream().anyMatch(tour -> tour.getName().equals(name))) {
            try {
                tourRepository.deleteTour(name);
                tours.removeIf(tour -> tour.getName().equals(name));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTour(Tour tour) {
        // the given tour has no map image or childFriendliness value, so it needs one

        // TODO: save image
        tour.setMapImage("");

        // TODO: calculate childFriendliness

    }


    public ObservableList<Tour> getObservableTourList() {
        return this.tours;
    }
}
