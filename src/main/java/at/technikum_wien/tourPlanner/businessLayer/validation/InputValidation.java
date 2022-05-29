package at.technikum_wien.tourPlanner.businessLayer.validation;

import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;

public abstract class InputValidation {

    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    // blank string -> string that only consists of whitespaces
    public boolean isBlankString(String string) {
        if (string == null || string.trim().length() == 0) {
            logger.info("String only consists of white spaces, please enter valid string with characters");
            return true;
        }
        return false;
    }

}
