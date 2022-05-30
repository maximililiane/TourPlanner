package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.FXMLDependencyInjection;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.viewModel.StartWindowViewModel;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class StartWindowController {

    private final StartWindowViewModel startWindowViewModel;
    public Button startRegularTourPlannerButton;
    public Button startDemoTourPlannerButton;
    private final LoggerWrapper logger = LoggerFactory.getLogger();

    public StartWindowController(StartWindowViewModel startWindowViewModel) {
        this.startWindowViewModel = startWindowViewModel;
    }

    public void startRegularTourPlanner() {
        startWindowViewModel.startRegularTourPlanner();
        openMainWindow();
        closeWindow();
    }

    public void startDemoTourPlanner() {
        startWindowViewModel.startDemoTourPlanner();
        openMainWindow();
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) startRegularTourPlannerButton.getScene().getWindow();
        stage.close();
    }

    private void openMainWindow() {
        try {
            Parent root = FXMLDependencyInjection.load("mainWindow.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Tour Planner");
            stage.setScene(scene);
            stage.setMinHeight(600.0);
            stage.setMinWidth(900.0);
            stage.show();
        } catch (IOException e) {
            logger.error("An error occurred when opening the main tour application window.\n" + e.getMessage());
            e.printStackTrace();
        }

    }
}
