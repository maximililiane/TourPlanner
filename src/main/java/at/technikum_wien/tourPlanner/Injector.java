package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.configuration.Configuration;
import at.technikum_wien.tourPlanner.configuration.PropertyFactory;

public class Injector {

    public Injector() {

    }


    public static Configuration getConfig(String env) {
        return PropertyFactory.getConfiguration(env);
    }
}
