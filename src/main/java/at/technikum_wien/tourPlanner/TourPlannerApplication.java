package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

public class TourPlannerApplication extends Application {

    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    @Override
    public void start(Stage stage) throws IOException {
//        Parent root = FXMLDependencyInjection.load("mainWindow.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH
//
//        Scene scene = new Scene(root);
//        stage.setTitle("Tour Planner");
//        stage.setScene(scene);
//        stage.setMinHeight(600.0);
//        stage.setMinWidth(900.0);
//        stage.show();

        Parent root = FXMLDependencyInjection.load("startWindow.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH

        Scene scene = new Scene(root);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.setMinHeight(200.0);
        stage.setMinWidth(300.0);
        stage.show();
    }

    public static void main(String[] args) {
//        logger.debug("Starting Tour Planner Application.");
        launch();
    }
}