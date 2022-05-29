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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TourService extends Mapper {

    private final TourRepository tourRepository;
    private final ObservableList<Tour> tours;
    private static final LoggerWrapper logger = LoggerFactory.getLogger();
    private final RouteValidation routeValidation;
    private final TourInputValidation tourInputValidation;

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

    public LinkedList<String> searchTours(String searchString) {
        try {
            return tourRepository.searchTours(searchString);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Couldn't search in tour table for: " + searchString);
        }
        return null;
    }

    public void resetTourTable() {
        try {
            tourRepository.resetTourTable();
            this.tours.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTourId(String tourName) {
        return tours.stream().filter(tour -> tour.getName().equals(tourName)).findAny().get().getUid();
    }

    public ObservableList<Tour> getTours() {
        return tourRepository.getObservableTourList();
    }

    public String getTourNameById(int id) {
        try {
            return tourRepository.getTourNameById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to retrieve a tour name; TourID: " + id + ";\n" + e.getMessage());
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
        tourWithMapQuestApiInfo.setLogs(tour.getLogs());
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
            String json = FileUtils.readFileToString(file, "UTF-8");
            Tour importedTour = toObject(json, Tour.class);
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

    public void addDemoData() {
        Tour tourA = toObject("{\"name\":\"Tour A\",\"startingPoint\":\"Vienna\",\"destination\":\"Eisenstadt\",\"duration\":\"00:41:32\",\"transportType\":\"FASTEST\",\"description\":\"Perfect tour for a short trip out of the city.\",\"popularity\":3,\"length\":33.035,\"childFriendly\":97,\"mapImage\":\"tourA.jpeg\",\"logs\":[{\"date\":1653343200000,\"comment\":\"Some light traffic on the way there, but still had a great time.\",\"difficulty\":5,\"totalTime\":{\"totalTime\":\"01:04:00\"},\"rating\":5},{\"date\":1653429600000,\"comment\":\"\",\"difficulty\":2,\"totalTime\":{\"totalTime\":\"00:52:00\"},\"rating\":5},{\"date\":1652220000000,\"comment\":\"There's construction on the highway, took us a lot longer to get there than expected.\",\"difficulty\":15,\"totalTime\":{\"totalTime\":\"01:20:00\"},\"rating\":4}]}", Tour.class);
        tourA.setUid(1);
        Tour tourB = toObject("{\"name\":\"Tour B\",\"startingPoint\":\"Graz\",\"destination\":\"Venice\",\"duration\":\"04:12:10\",\"transportType\":\"FASTEST\",\"description\":\"Cross the border for a trip to the European boot!\",\"popularity\":0,\"length\":272.139,\"childFriendly\":0,\"mapImage\":\"tourB.jpeg\",\"logs\":[]}", Tour.class);
        tourB.setUid(2);
        Tour tourC = toObject("{\"name\":\"Tour C\",\"startingPoint\":\"Vienna\",\"destination\":\"Schwechat\",\"duration\":\"00:31:10\",\"transportType\":\"BICYCLE\",\"description\":\"Take a short bike trip out of the city for when you feel like exercising.\",\"popularity\":1,\"length\":6.994,\"childFriendly\":83,\"mapImage\":\"tourC.jpeg\",\"logs\":[{\"date\":1652479200000,\"comment\":\"Exactly what I needed after a long day of sitting at the office.\",\"difficulty\":50,\"totalTime\":{\"totalTime\":\"00:48:00\"},\"rating\":5}]}", Tour.class);
        tourC.setUid(3);
        Tour tourD = toObject("{\"name\":\"Tour D\",\"startingPoint\":\"Vienna\",\"destination\":\"Zadar\",\"duration\":\"06:15:27\",\"transportType\":\"FASTEST\",\"description\":\"Escape the hustle and bustle of the big city and come to the beach.\",\"popularity\":2,\"length\":393.919,\"childFriendly\":65,\"mapImage\":\"tourD.jpeg\",\"logs\":[{\"date\":1651788000000,\"comment\":\"The long journey was worth this destination.\",\"difficulty\":40,\"totalTime\":{\"totalTime\":\"07:13:00\"},\"rating\":5},{\"date\":1652997600000,\"comment\":\"There was a lot of traffic, but I also left on a Friday after work so...\",\"difficulty\":37,\"totalTime\":{\"totalTime\":\"07:26:00\"},\"rating\":4}]}", Tour.class);
        tourD.setUid(4);
        Tour tourE = toObject("{\"name\":\"Tour E\",\"startingPoint\":\"Wales\",\"destination\":\"Dublin\",\"duration\":\"07:57:54\",\"transportType\":\"FASTEST\",\"description\":\"Experience your own Brexit with this tour.\",\"popularity\":1,\"length\":233.029,\"childFriendly\":62,\"mapImage\":\"tourE.jpeg\",\"logs\":[{\"date\":1653688800000,\"comment\":\"I drove my car into the water! Because the GPS told me to!\",\"difficulty\":74,\"totalTime\":{\"totalTime\":\"05:30:00\"},\"rating\":1}]}", Tour.class);
        tourE.setUid(5);

        System.out.println(tourA);
        try {
            tourRepository.addTour(tourA);
            tourRepository.addTour(tourB);
            tourRepository.addTour(tourC);
            tourRepository.addTour(tourD);
            tourRepository.addTour(tourE);
            tours.add(tourA);
            tours.add(tourB);
            tours.add(tourC);
            tours.add(tourD);
            tours.add(tourE);
        } catch (SQLException e) {
            logger.error("Error saving demo data");
            e.printStackTrace();
        }
    }

}

