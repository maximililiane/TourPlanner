package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest.RouteResponse;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.service.MapQuestApi;
import at.technikum_wien.tourPlanner.viewModel.DescriptionViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class DescriptionWindowController implements Initializable {

    private final DescriptionViewModel descriptionViewModel;
    public Label popularityLabel;
    @FXML
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
    private Injector injector;

    public DescriptionWindowController(Injector injector, DescriptionViewModel descriptionViewModel) {
        this.injector = injector;
        this.descriptionViewModel = descriptionViewModel;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setUpListView();

        updateListView();

        //TODO: dynamic image
        MapQuestApi mapQuestApi = new MapQuestApi();
        RouteResponse response = mapQuestApi.getRoute("Wien", "Zeiselmauer,%20Austria");
        byte[] buffer = mapQuestApi.getMap(response);

        mapImage.setImage(new Image(new ByteArrayInputStream(buffer)));
    }

    private void setUpListView() {
        // set list data
        tourListView.setItems(getTours());

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
                titleLabel.setText(newTour.getName());
                popularityLabel.setText(String.valueOf(newTour.getPopularity()));
                childFriendlinessLabel.setText(String.valueOf(newTour.isChildFriendly()));
                fromLabel.setText(newTour.getStartingPoint());
                toLabel.setText(newTour.getDestination());
                distanceLabel.setText(String.valueOf(newTour.getLength()));
                estimatedTimeLabel.setText(newTour.getDuration());
                transportTypeLabel.setText(newTour.getTransportType());
                descriptionLabel.setText(newTour.getDescription());
            }
        });
    }

    private ObservableList<Tour> getTours() {
        //TODO: move database call to DescriptionWindowViewModel
        LinkedList<Tour> tours = injector.getProxy().getToursTemporary();
        return FXCollections.observableArrayList(tours);
    }

}
