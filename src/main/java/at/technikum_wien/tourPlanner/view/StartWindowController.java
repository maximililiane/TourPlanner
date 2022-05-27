package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.FXMLDependencyInjection;
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

    public StartWindowController(StartWindowViewModel startWindowViewModel) {
        this.startWindowViewModel = startWindowViewModel;
    }

    public void startRegularTourPlanner(ActionEvent actionEvent) {
        startWindowViewModel.startRegularTourPlanner();
        openMainWindow();
        closeWindow();
    }

    public void startDemoTourPlanner(ActionEvent actionEvent) {
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
            e.printStackTrace();
        }

    }
}
