package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.database.DataBaseConnector;
import at.technikum_wien.tourPlanner.proxyUtils.DBProxy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import at.technikum_wien.tourPlanner.Injector;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class TourPlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load("mainWindow.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH

        Scene scene = new Scene(root);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}