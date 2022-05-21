package at.technikum_wien.tourPlanner.logging;

import org.apache.logging.log4j.Logger;

public class InitializedLogger extends BaseStateLogger {

    private final Logger logger;

    public InitializedLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String message) {
        this.logger.debug(message);
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }

    @Override
    public void fatal(String message) {
        this.logger.fatal(message);
    }
}
