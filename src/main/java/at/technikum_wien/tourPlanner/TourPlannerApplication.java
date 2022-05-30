package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.DatabaseConnector;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Logger;

public class TourPlannerApplication extends Application {

    private static final LoggerWrapper logger = LoggerFactory.getLogger();

    @Override
    public void start(Stage stage) throws IOException {

        if(!checkConnection()){
            openConnectionErrorWindow(stage);
        } else {
            Parent root = FXMLDependencyInjection.load("startWindow.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH

            Scene scene = new Scene(root);
            stage.setTitle("Tour Planner");
            stage.setScene(scene);
            stage.setMinHeight(200.0);
            stage.setMinWidth(300.0);
            stage.show();
            stage.setResizable(false);
        }
    }

    public static void main(String[] args) {
        logger.debug("Starting Tour Planner Application.");
        launch();
    }

    public static boolean checkConnection(){
        try {
            new DatabaseConnector().connect();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.fatal("Could not connect to the database; " + e.getMessage());
            return false;
        }
        return true;
    }

    public static void openConnectionErrorWindow(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load("connectionErrorWindow.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH

        Scene scene = new Scene(root);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.setMinHeight(200.0);
        stage.setMinWidth(300.0);
        stage.show();
        stage.setResizable(false);
    }
}