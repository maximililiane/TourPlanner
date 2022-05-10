package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService.MapQuestApi;
import at.technikum_wien.tourPlanner.businessLayer.pdfGeneration.PdfGeneration;
import at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest.RouteResponse;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.LinkedList;

public class TourService {

    private TourRepository tourRepository;
    private ObservableList<Tour> tours;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
        this.tours = FXCollections.observableList(getTours());

        // TODO: delete test data once addTour has been implemented
        // saveImage(1, "Vienna", "Bratislava");
        // saveImage(2, "Berlin", "Madrid");
    }

    public LinkedList<Tour> getTours() {
        try {
            return tourRepository.getTours();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addTour(Tour tour) {
        // the given tour has no map image or childFriendliness value, so it needs one
        tour.setMapImage(saveImage(tour.getUid(), tour.getStartingPoint(), tour.getDestination()));

        // TODO: calculate childFriendliness
        tour.setChildFriendly(0);

        try {
            int tourId = tourRepository.addTour(tour);
            tour.setUid(tourId);
            tours.add(tour);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTour(String name) {
        Tour tour = tours.stream()
                .filter(t -> t.getName().equals(name))
                .findAny()
                .orElse(null);

        if (tour != null) {
            try {
                tourRepository.deleteTour(tour.getUid());
                tours.removeIf(t -> t.getUid() == (tour.getUid()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // write image into images folder, return name of file that image is in
    private String saveImage(int tourId, String startingPoint, String destination) {
        MapQuestApi mapQuestApi = new MapQuestApi();
        RouteResponse response = mapQuestApi.getRoute(startingPoint, destination);
        BufferedImage mapImage = mapQuestApi.getMap(response.getRoute().getBoundingBox(), response.getRoute().getSessionId());

        try {
            // check if file directory where images should be saved exists
            File imageDirectory = new File("images/");
            if (!imageDirectory.exists()) {
                imageDirectory.mkdir();
            }

            File outputFile = new File("images/" + tourId + ".jpeg");
            ImageIO.write(mapImage, "jpeg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.toString(tourId) + ".jpeg";

    }

    public void saveReport(Tour tour) {
        try {
            PdfGeneration.generateTourReport(tour);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Tour> getObservableTourList() {
        return this.tours;
    }
}
