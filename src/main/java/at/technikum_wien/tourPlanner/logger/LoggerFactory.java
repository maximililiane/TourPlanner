package at.technikum_wien.tourPlanner.logger;

public class LoggerFactory {
    public static LoggerWrapper getLogger() {
        return new Log4jWrapper();
    }
}
