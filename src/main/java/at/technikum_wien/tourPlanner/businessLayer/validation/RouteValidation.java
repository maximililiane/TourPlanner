package at.technikum_wien.tourPlanner.businessLayer.validation;

import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;

public class RouteValidation {

    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    public boolean invalidLocations(String statusCode, String[] messages) {
        if (statusCode.equals("402")) {
            logger.info(messages[0] + " Please try again.");
            return true;
        }
        return false;
    }

    public boolean invalidDistance(double distance) {
        if (distance == 0) {
            logger.info("Invalid location(s). The start & end point need to be existent and different from each other.");
            return true;
        }
        return false;
    }

    public boolean invalidPedestrianDistance(String statusCode) {
        if (statusCode.equals("607") || statusCode.equals("608")) { // 607 & 608 exceeded maximum gross distance for locations
            logger.info("Exceeded pedestrian maximum gross distance (200.0 miles) for locations.");
            return true;
        }

        return false;
    }

    public boolean isUnknownError(String statusCode) {
        if (statusCode.equals("500")) {
            logger.info("An unknown error has occurred while parsing tour data. Please try again with valid data.");
            return true;
        }
        return false;
    }

}
