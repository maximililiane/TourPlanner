package at.technikum_wien.tourPlanner.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jWrapper implements LoggerWrapper{

    private Logger logger;
    private BaseStateLogger state= new UnitializedLogger();

    @Override
    public void debug(String message) {
        this.state.debug(message);
    }

    @Override
    public void info(String message) {
        this.state.info(message);
    }

    @Override
    public void warn(String message) {
        this.state.warn(message);
    }

    @Override
    public void error(String message) {
        this.state.error(message);
    }

    @Override
    public void fatal(String message) {
        this.state.error(message);
    }

    public void initialize(){
        this.state= new InitializedLogger(LogManager.getLogger(this.getClass().getName()));
    }
}
