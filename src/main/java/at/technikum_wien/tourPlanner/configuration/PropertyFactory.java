package at.technikum_wien.tourPlanner.configuration;

import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;

public class PropertyFactory {

    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    public static Configuration getConfiguration(String env) {
        if (env.equals("app.properties")) {
            return new PropertiesConfiguration(env);
        }
        logger.fatal(env + "configuration not found");
        throw new RuntimeException(env + "Configuration not found");
    }
}
