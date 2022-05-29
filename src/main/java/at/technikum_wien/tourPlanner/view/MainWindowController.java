package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.FXMLDependencyInjection;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.viewModel.MainWindowViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainWindowController {

    private final MainWindowViewModel mainViewModel;
    public Button searchButton;
    public TextField searchTextField;
    private final LoggerWrapper logger = LoggerFactory.getLogger();

    public MainWindowController(MainWindowViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @FXML
    void initialize() {
        searchTextField.textProperty().bindBidirectional(mainViewModel.searchStringProperty());
        searchButton.disableProperty().bind(mainViewModel.searchDisabledBinding());
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
            logger.error("An error occurred when opening the export window.\n" + e.getMessage());
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
            logger.error("An error occurred when opening the import window.\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void doSearch(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = FXMLDependencyInjection.getLoader("searchWindow.fxml", Locale.ENGLISH);
            Parent root = loader.load();
            SearchViewController controller = loader.getController();

            ObservableList<String> formattedTours = mainViewModel.searchTours();

            controller.resultsLabel.setText(formattedTours.size() + " results for: " + searchTextField.getText());
            controller.searchResultListView.setItems(formattedTours);
            controller.searchResultListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> tourListView) {
                    return new ListCell<>() {
                        @Override
                        public void updateItem(String tour, boolean empty) {
                            super.updateItem(tour, empty);
                            if (empty || tour == null) {
                                setText(null);
                            } else {
                                setText(tour);
                            }
                        }
                    };
                }
            });

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tour Planner");
            stage.setScene(scene);
            stage.setMinWidth(550.0);
            stage.setMinHeight(307.0);
            stage.show();

            searchTextField.setText("");

        } catch (IOException e) {
            logger.error("An error occurred when opening the search window.\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}