package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TransportMode;
import at.technikum_wien.tourPlanner.viewModel.EditTourWindowViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTourWindowController {

    private final EditTourWindowViewModel editTourWindowViewModel;
    @FXML
    public TextField tourNameTextField;
    @FXML
    public TextField fromTextField;
    @FXML
    public TextField toTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public ChoiceBox transportModeCheckBox;
    @FXML
    public Button editTourButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Label hintLabel;
    @FXML
    public Label tourId;
    @FXML
    public Label transportTypeLabel;

    public EditTourWindowController(EditTourWindowViewModel editTourWindowViewModel) {
        this.editTourWindowViewModel = editTourWindowViewModel;
    }

    @FXML
    public void initialize() {
        tourId.setVisible(false);
        // initialize checkbox choices
        // transportModeCheckBox.getItems().removeAll(transportModeCheckBox.getItems());

        editTourWindowViewModel.setNameStringProperty(tourNameTextField.getText());
        editTourWindowViewModel.setFromString(fromTextField.getText());
        editTourWindowViewModel.setToString(fromTextField.getText());
        editTourWindowViewModel.setDescriptionString(descriptionTextField.getText());

        transportModeCheckBox.getItems().addAll(FXCollections.observableArrayList(
                TransportMode.FASTEST.name(), TransportMode.SHORTEST.name(),
                TransportMode.PEDESTRIAN.name(), TransportMode.BICYCLE.name()));
        transportModeCheckBox.getSelectionModel().select(0);

        tourNameTextField.textProperty().bindBidirectional(editTourWindowViewModel.nameStringProperty());
        fromTextField.textProperty().bindBidirectional(editTourWindowViewModel.fromStringProperty());
        toTextField.textProperty().bindBidirectional(editTourWindowViewModel.toStringProperty());
        descriptionTextField.textProperty().bindBidirectional(editTourWindowViewModel.descriptionStringProperty());
        editTourButton.disableProperty().bind(editTourWindowViewModel.editDisabledBinding());

        hintLabel.visibleProperty().bind(editTourWindowViewModel.editDisabledBinding());
    }

    public void editTour() {
        Tour tour = new Tour(tourNameTextField.getText(), fromTextField.getText(),
                toTextField.getText(), (TransportMode.valueOf(transportModeCheckBox.getSelectionModel().getSelectedItem().toString())),
                descriptionTextField.getText());
        tour.setUid(Integer.parseInt(tourId.getText()));
        editTourWindowViewModel.editTour(tour);
        closeWindow();
    }

    public void closeWindow() {
        // reset values
        tourNameTextField.setText("");
        fromTextField.setText("");
        toTextField.setText("");
        descriptionTextField.setText("");
        transportModeCheckBox.getSelectionModel().select(0);

        // close window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
