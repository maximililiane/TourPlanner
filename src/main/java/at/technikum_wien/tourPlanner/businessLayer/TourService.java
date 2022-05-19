package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService.MapQuestApi;
import at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService.Mapper;
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

public class TourService extends Mapper {

    private TourRepository tourRepository;
    private ObservableList<Tour> tours;

    public TourService(TourRepository tourRepository) {
        super();
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


    public void addTour(Tour tour) {
        // TODO: validate user input

        // get tourID from database
        try {
            tour.setUid(tourRepository.getNextTourId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // the given tour has no map image or childFriendliness value, so it needs one
        Tour tourWithMapQuestApiInfo = saveImage(tour);

        // TODO: calculate childFriendliness
        tourWithMapQuestApiInfo.setChildFriendly(0);
        tourWithMapQuestApiInfo.setPopularity(0);

        try {
            tourRepository.addTour(tourWithMapQuestApiInfo);
            tours.add(tourWithMapQuestApiInfo);
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

    public void editTour(Tour tour) {
        Tour oldTour = tours.stream()
                .filter(t -> t.getUid() == tour.getUid())
                .findAny().get();
        tours.remove(oldTour);

        try {
            // route hasn't been changed
            if (oldTour.getStartingPoint().equals(tour.getStartingPoint()) && oldTour.getDestination().equals(tour.getDestination()) &&
                    oldTour.getTransportType() == tour.getTransportType()) {
                oldTour.setName(tour.getName());
                oldTour.setDuration(tour.getDescription());
                tourRepository.editTour(oldTour);
                tours.add(oldTour);
            } else {

                // route has been changed
                Tour tourWithMapQuestApiInfo = saveImage(tour);

                // TODO: calculate childFriendliness
                tourWithMapQuestApiInfo.setChildFriendly(0);
                tourWithMapQuestApiInfo.setPopularity(0);

                tourRepository.editTour(tourWithMapQuestApiInfo);
                tours.add(tourWithMapQuestApiInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // write image into images folder, return name of file that image is in
    private Tour saveImage(Tour tour) {
        MapQuestApi mapQuestApi = new MapQuestApi();
        RouteResponse response = mapQuestApi.getRoute(tour.getStartingPoint(), tour.getDestination(), tour.getTransportType());
        BufferedImage mapImage = mapQuestApi.getMap(response.getRoute().getBoundingBox(), response.getRoute().getSessionId());

        try {
            // check if file directory where images should be saved exists
            File imageDirectory = new File("images/");
            if (!imageDirectory.exists()) {
                imageDirectory.mkdir();
            }

            File outputFile = new File("images/" + tour.getUid() + ".jpeg");
            ImageIO.write(mapImage, "jpeg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tour.setDuration(response.getRoute().getFormattedTime());
        tour.setLength(response.getRoute().getDistance());
        tour.setMapImage(tour.getUid() + ".jpeg");

        return tour;
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

    public void exportData(Tour tour) {
        try {
            // TODO: get data folder from config
            // check if file directory where data should be saved exists
            File imageDirectory = new File("tours/");
            if (!imageDirectory.exists()) {
                imageDirectory.mkdir();
            }

            FileWriter outputFile = new FileWriter("tours/" + tour.getUid() + ".json");
            outputFile.write(json(tour));
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
