package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.view.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLDependencyInjection {
    public static Parent load(String location, Locale locale) throws IOException {
        FXMLLoader loader = getLoader(location, locale);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location, Locale locale) {
        return new FXMLLoader(
                FXMLDependencyInjection.class.getResource("/at/technikum_wien/tourPlanner/view/" + location),
                ResourceBundle.getBundle("at.technikum_wien.tourPlanner.view." + "gui_strings", locale),
                new JavaFXBuilderFactory(),
                controllerClass -> ControllerFactory.getInstance().create(controllerClass)
        );
    }
}
