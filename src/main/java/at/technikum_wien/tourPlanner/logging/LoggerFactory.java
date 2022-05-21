package at.technikum_wien.tourPlanner.logging;

import org.apache.logging.log4j.Logger;

public class LoggerFactory {

    public static LoggerWrapper getLogger() {
        var logger = new Log4jWrapper();
        logger.initialize();
        return logger;

    }
}
