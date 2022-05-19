package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.FXMLDependencyInjection;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.viewModel.DescriptionViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    @FXML
    public Button deleteButton;
    public Button saveReportButton;
    public Button saveTourButton;
    private Tour selectedTour;
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


    public DescriptionWindowController(DescriptionViewModel descriptionViewModel) {
        this.descriptionViewModel = descriptionViewModel;
        this.selectedTour = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.tourObservableList = getTours();
        this.tourObservableList.addListener(new ListChangeListener<Tour>() {
            @Override
            public void onChanged(Change<? extends Tour> change) {
                //TODO: potentially change this line
                System.out.println("Hey, a change occurred...");
            }
        });

        setUpListView();

        updateListView();
    }

    public void deleteTour() {
        descriptionViewModel.deleteTour(titleLabel.getText());
    }

    public void saveReport() {
        descriptionViewModel.saveReport(selectedTour);
    }

    public void openAddTourWindow() {

        try {
            Parent root = FXMLDependencyInjection.load("addTourWindow.fxml", Locale.ENGLISH);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tour Planner");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setUpListView() {
        // set list data
        tourListView.setItems(tourObservableList);

        // rename list cells to only show the name of the tour
        tourListView.setCellFactory(new Callback<ListView<Tour>, ListCell<Tour>>() {
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

    // update view when new item on the list has been selected
    private void updateListView() {
        tourListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue<? extends Tour> observableValue, Tour oldTour, Tour newTour) {
                if (newTour != null) {
                    selectedTour = newTour;
                    titleLabel.setText(newTour.getName());
                    popularityLabel.setText(String.valueOf(newTour.getPopularity()));
                    childFriendlinessLabel.setText(String.valueOf(newTour.getChildFriendly()));
                    fromLabel.setText(newTour.getStartingPoint());
                    toLabel.setText(newTour.getDestination());
                    distanceLabel.setText(String.valueOf(newTour.getLength()));
                    estimatedTimeLabel.setText(newTour.getDuration());
                    transportTypeLabel.setText(newTour.getTransportType().name());
                    descriptionLabel.setText(newTour.getDescription());
                    mapImage.setImage(new Image("file:images/" + newTour.getMapImage()));
                } else {
                    // there are no more tours left in the database
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
            }
        });
    }

    private ObservableList<Tour> getTours() {
        return descriptionViewModel.getTours();
    }

}
