package at.technikum_wien.tourPlanner.logging;

public interface LoggerWrapper {

    public void debug(String message);
    public void info(String message);
    public void warn(String message);
    public void error(String message);
    public void fatal(String message);

}
