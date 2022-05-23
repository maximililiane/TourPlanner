package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TransportMode;
import at.technikum_wien.tourPlanner.viewModel.AddTourWindowViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddTourWindowController {

    private final AddTourWindowViewModel addTourWindowViewModel;
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
    public Button addTourButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Label hintLabel;
    public Label nameHintLabel;
    public Label fromHintLabel;
    public Label toHintLabel;
    public Label descriptionHintLabel;

    public AddTourWindowController(AddTourWindowViewModel addTourWindowViewModel) {
        this.addTourWindowViewModel = addTourWindowViewModel;
    }

    @FXML
    public void initialize() {
        // initialize checkbox choices
        transportModeCheckBox.getItems().removeAll(transportModeCheckBox.getItems());
        transportModeCheckBox.getItems().addAll(FXCollections.observableArrayList(
                TransportMode.FASTEST.name(), TransportMode.SHORTEST.name(),
                TransportMode.PEDESTRIAN.name(), TransportMode.BICYCLE.name()));
        transportModeCheckBox.getSelectionModel().select(0);

        tourNameTextField.textProperty().bindBidirectional(addTourWindowViewModel.nameStringProperty());
        fromTextField.textProperty().bindBidirectional(addTourWindowViewModel.fromStringProperty());
        toTextField.textProperty().bindBidirectional(addTourWindowViewModel.toStringProperty());
        descriptionTextField.textProperty().bindBidirectional(addTourWindowViewModel.descriptionStringProperty());
        addTourButton.disableProperty().bind(addTourWindowViewModel.addDisabledBinding());

        hintLabel.visibleProperty().bind(addTourWindowViewModel.addDisabledBinding());
        nameHintLabel.visibleProperty().setValue(false);
        fromHintLabel.visibleProperty().setValue(false);
        toHintLabel.visibleProperty().setValue(false);
        descriptionHintLabel.visibleProperty().setValue(false);
    }

    public void addTour() {
        if (addTourWindowViewModel.invalidName()) {
            nameHintLabel.visibleProperty().setValue(true);
        }
        if (addTourWindowViewModel.invalidStartingPoint()) {
            fromHintLabel.visibleProperty().setValue(true);
        }
        if (addTourWindowViewModel.invalidEndPoint()) {
            toHintLabel.visibleProperty().setValue(true);
        }
        if (addTourWindowViewModel.sameLocation()) {
            fromHintLabel.visibleProperty().setValue(true);
            toHintLabel.visibleProperty().setValue(true);
        }
        if (addTourWindowViewModel.invalidDescription()) {
            descriptionHintLabel.visibleProperty().setValue(true);
        }
        if (!addTourWindowViewModel.invalidName() && !addTourWindowViewModel.sameLocation()
                && !addTourWindowViewModel.invalidStartingPoint() && !addTourWindowViewModel.invalidEndPoint()
                && !addTourWindowViewModel.invalidDescription()) { // valid input
            addTourWindowViewModel.addTour(new Tour(tourNameTextField.getText(), fromTextField.getText(),
                    toTextField.getText(), (TransportMode.valueOf(transportModeCheckBox.getSelectionModel().getSelectedItem().toString())),
                    descriptionTextField.getText()));
            closeWindow();
        }

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
