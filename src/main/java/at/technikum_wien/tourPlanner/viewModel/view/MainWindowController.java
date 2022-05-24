package at.technikum_wien.tourPlanner.viewModel.view;

import at.technikum_wien.tourPlanner.FXMLDependencyInjection;
import at.technikum_wien.tourPlanner.viewModel.MainWindowViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class MainWindowController {

    private final MainWindowViewModel mainViewModel;

    public MainWindowController(MainWindowViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @FXML
    void initialize() {
    }

    public void openExportDataWindow() {
        try {
            Parent root = FXMLDependencyInjection.load("exportDataWindow.fxml", Locale.ENGLISH);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tour Planner");
            stage.setScene(scene);
            stage.setMinWidth(250.0);
            stage.setMinHeight(400.0);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openImportDataWindow() {
        try {
            Parent root = FXMLDependencyInjection.load("importWindow.fxml", Locale.ENGLISH);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tour Planner");
            stage.setScene(scene);
            stage.setMinWidth(250.0);
            stage.setMinHeight(400.0);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}