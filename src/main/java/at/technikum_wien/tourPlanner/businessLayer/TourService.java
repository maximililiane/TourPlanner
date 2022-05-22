package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService.MapQuestApi;
import at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService.Mapper;
import at.technikum_wien.tourPlanner.businessLayer.pdfGeneration.PdfGeneration;
import at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest.RouteResponse;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TourService extends Mapper {

    private TourRepository tourRepository;
    private ObservableList<Tour> tours;
    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    public TourService(TourRepository tourRepository) {
        super();
        this.tourRepository = tourRepository;
        this.tours = getTours();
        this.tours.addListener(new ListChangeListener<Tour>() {
            @Override
            public void onChanged(Change<? extends Tour> change) {
                while (change.next()) {
                    // a tour in the list has been updated
                    if (change.wasReplaced()) {
                        for (Tour tour : tours) {
                            tour.setPopularity();
                            tour.setChildFriendly(calculateChildFriendliness(tour.getLength(), tour.getLogs()));
                        }
                    }
                }
            }
        });
    }

    public ObservableList<Tour> getTours() {
        return tourRepository.getObservableTourList();
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

        tourWithMapQuestApiInfo.setChildFriendly(calculateChildFriendliness(
                tourWithMapQuestApiInfo.getLength(), tour.getLogs()));
        tourWithMapQuestApiInfo.setPopularity();

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
        int index = tours.indexOf(oldTour);

        try {
            // route hasn't been changed
            if (oldTour.getStartingPoint().equals(tour.getStartingPoint()) && oldTour.getDestination().equals(tour.getDestination()) &&
                    oldTour.getTransportType() == tour.getTransportType()) {
                oldTour.setName(tour.getName());
                oldTour.setDuration(tour.getDescription());
                tourRepository.editTour(oldTour);
                tours.set(index, oldTour);
            } else {

                // route has been changed
                Tour tourWithMapQuestApiInfo = saveImage(tour);
                tourWithMapQuestApiInfo.setChildFriendly(
                        calculateChildFriendliness(tourWithMapQuestApiInfo.getLength(), tour.getLogs()));

                tourRepository.editTour(tourWithMapQuestApiInfo);
                tours.set(index, tourWithMapQuestApiInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveReport(Tour tour) {
        try {
            PdfGeneration.generateTourReport(tour);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSummaryReport(Tour tour) {
        try {
            PdfGeneration.generateSummaryReport(tour);
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

            // also save image separately
            File sourceImage = new File("images/" + tour.getUid() + ".jpeg");
            File destination = new File("tours/");
            FileUtils.copyFileToDirectory(sourceImage, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tour importData(File file) {
        logger.debug("Importing Data from file: " + file.getName());

        try {
            Tour importedTour = getObjectMapper().readValue(file, Tour.class);
            if (importedTour == null) {
                logger.error("Couldn't parse imported tour.");
                return null;
            }

            // tour names are unique in the database
            if (tours.stream().anyMatch(t -> t.getName().equals(importedTour.getName()))) {
                logger.info("Tour with identical name already exists. Please change the name and try again.");
                return null;
            }

            addTour(importedTour);
            return tours.stream().filter(t -> t.getName().equals(importedTour.getName())).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateTourBasedOnNewLog(Tour tour) {

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

    // calculated based on the average difficulty and total time provided in tour logs & tour distance
    private int calculateChildFriendliness(double tourDistance, List<TourLog> tourLogs) {
        if (tourLogs.size() == 0) {
            // there are no tour logs so child friendliness cannot be computed
            return 0;
        }

        int averageDifficulty = 0;
        int averageTimeDifficulty = 0;
        int distanceDifficulty;

        for (TourLog log : tourLogs) {
            // average difficulty
            averageDifficulty += (100 - log.getDifficulty());

            // average time difficulty
            String[] values = log.getTotalTime().split(":");
            int hours = Integer.parseInt(values[0]);

            if (hours < 2) {
                // tour is shorter than two hours -> 100 child friendliness
                averageTimeDifficulty += 100;
            } else if (hours > 24) {
                // tour is longer than 24 hours -> 0 child friendliness
                averageTimeDifficulty += 0;
            } else {
                // after two hours, each tour loses 5 child friendliness points per hour
                averageTimeDifficulty += (110 - (hours * 5));
            }

        }

        // distance difficulty,
        if (tourDistance < 100) {
            distanceDifficulty = 100;
        } else if (tourDistance > 1000) {
            distanceDifficulty = 0;
        } else {
            distanceDifficulty = (int) ((1000 - tourDistance) / 10);
        }

        averageDifficulty = averageDifficulty / tourLogs.size();
        averageTimeDifficulty = averageTimeDifficulty / tourLogs.size();

        return (averageDifficulty + averageTimeDifficulty + distanceDifficulty) / 3;

    }
}

