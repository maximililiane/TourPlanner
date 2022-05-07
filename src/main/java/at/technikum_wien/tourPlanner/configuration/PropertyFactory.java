package at.technikum_wien.tourPlanner.configuration;

public class PropertyFactory {
    public static Configuration getConfiguration(String env) {
        if (env.equals("app.properties")) {
            return new PropertiesConfiguration(env);
        }
        throw new RuntimeException(env + "Configuration not found");
    }
}
