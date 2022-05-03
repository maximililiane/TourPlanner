package at.technikum_wien.tourPlanner.log;

public class LoggerFactory {
    public static LoggerWrapper getLogger() {
        return new Log4jWrapper();
    }
}
