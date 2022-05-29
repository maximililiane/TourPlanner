package at.technikum_wien.tourPlanner.businessLayer.validation;

import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;

public class TourInputValidation extends InputValidation {

    private final int NAME_LENGTH = 16;
    private final int LOCATION_LENGTH = 256;
    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    public TourInputValidation() {
        super();
    }

    public boolean validNameLength(String name) {
        if (name.length() > NAME_LENGTH) {
            logger.info("Tour name is too long, the tour name is only allowed to be 16 characters");
            return false;
        }
        return true;
    }

    public boolean validLocationLength(String location) {
        if (location.length() > LOCATION_LENGTH) {
            logger.info("Location is too long, a location is only allowed to be 256 characters.");
            return false;
        }
        return true;
    }

    public boolean sameLocation(String from, String to) {
        if (from.trim().equalsIgnoreCase(to.trim())) {
            logger.info("Start & end point are the same, these need to be different for a valid tour.");
            return true;
        }
        return false;
    }

}
