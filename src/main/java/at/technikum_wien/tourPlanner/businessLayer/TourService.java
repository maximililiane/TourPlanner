package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService.MapQuestApi;
import at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService.Mapper;
import at.technikum_wien.tourPlanner.businessLayer.pdfGeneration.PdfGeneration;
import at.technikum_wien.tourPlanner.businessLayer.validation.RouteValidation;
import at.technikum_wien.tourPlanner.businessLayer.validation.TourInputValidation;
import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableName;
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
import java.util.stream.Collectors;

public class TourService extends Mapper {

    private TourRepository tourRepository;
    private ObservableList<Tour> tours;
    private static final LoggerWrapper logger = LoggerFactory.getLogger();
    private RouteValidation routeValidation;
    private TourInputValidation tourInputValidation;

    public TourService(TourRepository tourRepository) {
        super();
        this.tourRepository = tourRepository;
        this.routeValidation = new RouteValidation();
        this.tourInputValidation = new TourInputValidation();
        this.tours = getTours();
    }

    public void setTourTableName(TableName tableName) {
        tourRepository.setTableName(tableName);
    }

    public void deleteAllTours() {
        try {
            tourRepository.deleteAllTours();
            tours.removeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Tour> getTours() {
        return tourRepository.getObservableTourList();
    }

    public String getTourNameById(int id) {
        try {
            return tourRepository.getTourNameById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to retrieve a tourname; TourID: " + id + ";\n" + e.getMessage());
        }
        return null;
    }

    public void addTour(Tour tour) {
        // get tourID from database
        try {
            tour.setUid(tourRepository.getNextTourId());
            System.out.println(tour.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to retrieve the next tourID;\n" + e.getMessage());
        }

        // the given tour has no map image or childFriendliness value, so it needs one
        Tour tourWithMapQuestApiInfo = saveImage(tour);

        if (tourWithMapQuestApiInfo == null) {
            logger.error("Unable to add tour with invalid information. Please try again.");
            return;
        }
        tourWithMapQuestApiInfo.setChildFriendly(0);
        tourWithMapQuestApiInfo.setLogs(new LinkedList<TourLog>());
        tourWithMapQuestApiInfo.setPopularity();

        try {
            tourRepository.addTour(tourWithMapQuestApiInfo);
            tours.add(tourWithMapQuestApiInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to add a new tour to the database; tour: " + tourWithMapQuestApiInfo + ";\n" + e.getMessage());
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
                logger.error("An error occurred while trying to remove a tour from the database; tourID: " + tour.getUid() + ";\n" + e.getMessage());

            }
        }
    }

    public void editTour(Tour tour) {
        Tour oldTour = tours.stream()
                .filter(t -> t.getUid() == tour.getUid())
                .findAny().get();
        int index = tours.indexOf(oldTour);

        try {
            oldTour.setName(tour.getName());
            oldTour.setDescription(tour.getDescription());
            // route hasn't been changed
            if (oldTour.getStartingPoint().equals(tour.getStartingPoint()) && oldTour.getDestination().equals(tour.getDestination()) &&
                    oldTour.getTransportType() == tour.getTransportType()) {
                tourRepository.editTour(oldTour);
                tours.set(index, oldTour);
            } else {
                // route has been changed
                oldTour = saveImage(tour);

                if (oldTour != null) {
                    oldTour.setLogs(tour.getLogs());
                    oldTour.setPopularity();
                    tourRepository.editTour(oldTour);
                    tours.set(index, oldTour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to edit a tour in the database; tourID: " + tour.getUid() + ";\n" + e.getMessage());

        }
    }


    public void saveReport(Tour tour) {
        try {
            PdfGeneration.generateTourReport(tour);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to save a report;\n" + e.getMessage());

        }
    }

    public void saveSummaryReport(Tour tour) {
        try {
            PdfGeneration.generateSummaryReport(tour);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to save a summary-report;\n" + e.getMessage());
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
            logger.error("An error occurred while trying to export a tour; Tour: " + tour + ";\n" + e.getMessage());
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
                logger.error("Tour with identical name already exists. Please change the name and try again.");
                return null;
            }
            String tourName = importedTour.getName();
            String startingPoint = importedTour.getStartingPoint();
            String destination = importedTour.getDestination();

            // validate string lengths & contents
            if (tourInputValidation.isBlankString(tourName) || tourInputValidation.isBlankString(startingPoint) ||
                    tourInputValidation.isBlankString(destination) || !tourInputValidation.validNameLength(tourName) ||
                    !tourInputValidation.validLocationLength(startingPoint) ||
                    !tourInputValidation.validLocationLength(destination) ||
                    tourInputValidation.sameLocation(startingPoint, destination)) {
                logger.error("Unable to add tour with invalid data. Please try again with valid data.");
                return null;
            }

            addTour(importedTour);
            return tours.stream().filter(t -> t.getName().equals(importedTour.getName())).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to import data;\n" + e.getMessage());
        }

        return null;
    }


    // write image into images folder, return name of file that image is in
    private Tour saveImage(Tour tour) {
        MapQuestApi mapQuestApi = new MapQuestApi();
        RouteResponse response = mapQuestApi.getRoute(tour.getStartingPoint(), tour.getDestination(), tour.getTransportType());

        String statusCode = response.getRouteInfo().getStatusCode();
        String[] messages = response.getRouteInfo().getMessages();

        // check for invalid input
        if (routeValidation.invalidLocations(statusCode, messages) || routeValidation.invalidPedestrianDistance(statusCode) ||
                routeValidation.invalidDistance(response.getRoute().getDistance()) || routeValidation.isUnknownError(statusCode)) {
            return null;
        }

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
            logger.error("An error occurred while trying to save an image;\n" + e.getMessage());
        }

        tour.setDuration(response.getRoute().getFormattedTime());
        tour.setLength(response.getRoute().getDistance());
        tour.setMapImage(tour.getUid() + ".jpeg");

        return tour;
    }

    public ObservableList<String> getTourNames() {
        List<String> namesList = tours.stream()
                .map(Tour::getName)
                .collect(Collectors.toList());
        return FXCollections.observableList(namesList);
    }

}

