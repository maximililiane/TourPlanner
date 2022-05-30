package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.FXMLDependencyInjection;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TransportMode;
import at.technikum_wien.tourPlanner.viewModel.DescriptionViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class DescriptionWindowController implements Initializable {

    private final DescriptionViewModel descriptionViewModel;
    public Button deleteButton;
    public Label popularityLabel;
    public ImageView mapImage;
    public Label titleLabel;
    public ListView<Tour> tourListView;
    public Label childFriendlinessLabel;
    public Label fromLabel;
    public Label toLabel;
    public Label distanceLabel;
    public Label estimatedTimeLabel;
    public Label transportTypeLabel;
    public Label descriptionLabel;
    private ObservableList<Tour> tourObservableList;
    private final LoggerWrapper logger = LoggerFactory.getLogger();
    @FXML
    private int selectedTour;

    public DescriptionWindowController(DescriptionViewModel descriptionViewModel) {
        this.descriptionViewModel = descriptionViewModel;
        this.selectedTour = -1;
    }


    public void deleteTour() {
        descriptionViewModel.deleteTour(titleLabel.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tourObservableList = getTours();
        this.tourObservableList.addListener(new ListChangeListener<Tour>() {
            @Override
            public void onChanged(Change<? extends Tour> change) {
                while (change.next()) {
                    if (change.wasReplaced()) {
                        if (selectedTour != -1) {
                            tourListView.getSelectionModel().select(selectedTour);
                            updateLabels(tourObservableList.get(selectedTour));
                        }
                    }
                }

            }
        });

        setUpListView();

        hideButtonsAndText();

        updateListView();

    }

    public void openAddTourWindow() {
        try {
            Parent root = FXMLDependencyInjection.load("addTourWindow.fxml", Locale.ENGLISH);
            setUpWindow(root);
        } catch (IOException e) {
            logger.error("An error occurred when opening the add tour window.\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void openEditTourWindow() {
        Tour tour = tourListView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = FXMLDependencyInjection.getLoader("addTourWindow.fxml", Locale.ENGLISH);
            Parent root = loader.load();
            AddTourWindowController controller = loader.getController();

            controller.setTourId(tour.getUid());
            controller.tourNameTextField.setText(tour.getName());
            controller.descriptionTextField.setText(tour.getDescription());
            controller.fromTextField.setText(tour.getStartingPoint());
            controller.toTextField.setText(tour.getDestination());
            controller.transportModeCheckBox.setValue(tour.getTransportType());
            controller.titleLabel.setText("Edit Tour");
            controller.addTourButton.setText("update tour");

            controller.addTourButton.setOnAction(t -> controller.editTour());

            setUpWindow(root);
        } catch (IOException e) {
            logger.error("An error occurred when opening the edit tour window.\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setUpWindow(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.setMinWidth(600.0);
        stage.setMinHeight(525.0);
        stage.setMaxHeight(525.0);
        stage.setResizable(false);
        stage.show();
    }

    private void setUpListView() {
        // set list data
        tourListView.setItems(tourObservableList);

        // rename list cells to only show the name of the tour
        tourListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Tour> call(ListView<Tour> tourListView) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Tour tour, boolean empty) {
                        super.updateItem(tour, empty);
                        if (empty || tour == null) {
                            setText(null);
                        } else {
                            setText(tour.getName());
                        }
                    }
                };
            }
        });

    }

    public void saveReport() {
        descriptionViewModel.saveReport(tourObservableList.get(selectedTour));
    }

    private ObservableList<Tour> getTours() {
        return descriptionViewModel.getTours();
    }

    // update view when new item on the list has been selected
    private void updateListView() {
        tourListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTour, newTour) -> {
            if (newTour != null) {
                selectedTour = tourListView.getSelectionModel().getSelectedIndex();
                updateLabels(newTour);
            } else {
                // there are no more tours left in the database
                selectedTour = -1;
                titleLabel.setText("");
                popularityLabel.setText("");
                childFriendlinessLabel.setText("");
                fromLabel.setText("");
                toLabel.setText("");
                distanceLabel.setText("");
                estimatedTimeLabel.setText("");
                transportTypeLabel.setText("");
                descriptionLabel.setText("");
                mapImage.setImage(null);
            }
        });
    }

    public void saveSummaryReport() {
        descriptionViewModel.saveSummaryReport(tourObservableList.get(selectedTour));
    }

    public Button saveTourButton;
    public Button editButton;
    public MenuButton saveReportButton;
    public Separator topSeparator;
    public Label popularityTitle;
    public Label childFriendlinessTitle;
    public Label fromTitle;
    public Label toTitle;
    public Label distanceTitle;
    public Label estimatedTimeTitle;
    public Label transportTypeTitle;
    public Separator bottomSeparator;
    public Label descriptionTitle;

    private void updateLabels(Tour tour) {
        titleLabel.setText(tour.getName());
        popularityLabel.setText(String.valueOf(tour.getPopularity()));
        childFriendlinessLabel.setText(
                tour.getChildFriendly() == 0 ? "N/A" : String.valueOf(tour.getChildFriendly()));
        fromLabel.setText(tour.getStartingPoint());
        toLabel.setText(tour.getDestination());
        distanceLabel.setText(tour.getLength() + " miles");
        estimatedTimeLabel.setText(tour.getDuration());
        transportTypeLabel.setText(tour.getTransportType().name());
        descriptionLabel.setText(tour.getDescription());
        mapImage.setImage(new Image("file:images/" + tour.getMapImage()));
    }

    private void hideButtonsAndText() {
        editButton.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        deleteButton.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        saveReportButton.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        topSeparator.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        popularityTitle.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        childFriendlinessTitle.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        fromTitle.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        toTitle.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        distanceTitle.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        estimatedTimeTitle.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        transportTypeTitle.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        bottomSeparator.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
        descriptionTitle.visibleProperty().bind(tourListView.getSelectionModel().selectedItemProperty().isNotNull());
    }
}
