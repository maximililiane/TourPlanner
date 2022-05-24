package at.technikum_wien.tourPlanner.viewModel.view;

import at.technikum_wien.tourPlanner.viewModel.AddLogWindowViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.LinkedList;

public class AddLogWindowController {

    private final AddLogWindowViewModel addLogWindowViewModel;

    @FXML
    public Button saveLogButton;
    @FXML
    public Button cancelButton;
    @FXML
    public TextField hoursField;
    @FXML
    public TextField minutesField;
    @FXML
    public TextArea commentArea;
    @FXML
    public DatePicker datePicker;
    @FXML
    public ChoiceBox ratingPicker;
    @FXML
    public ChoiceBox tourNamePicker;
    @FXML
    public TextField difficultyField;

    public AddLogWindowController(AddLogWindowViewModel addLogWindowViewModel) {
        this.addLogWindowViewModel = addLogWindowViewModel;
    }

    @FXML
    public void initialize() {
        clearView();
        setChoicePickerValues();
        //initializeDifficultySpinner();

        /*
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
        descriptionHintLabel.visibleProperty().setValue(false);*/
    }

    /*private void initializeDifficultySpinner() {
        difficultySpinner=new Spinner<Integer>();
        SpinnerValueFactory<Integer> values= new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,50);
        difficultySpinner.setValueFactory(values);
    }*/

    public void clearView(){
        tourNamePicker.getItems().removeAll(tourNamePicker.getItems());
        hoursField.setText("");
        minutesField.setText("");
        commentArea.setText("");
        datePicker.setValue(null);
        ratingPicker.setValue(1);
        tourNamePicker.setValue(null);
        difficultyField.setText("50");
    }

    public void setChoicePickerValues(){
        tourNamePicker.setItems(addLogWindowViewModel.getTourNames());
        LinkedList<Integer> l= new LinkedList<>();
        for(int i=1; i<=5; i++){
            l.add(i);
        }
        ratingPicker.setItems(FXCollections.observableList(l));
    }

    public void addLog() {
        this.addLogWindowViewModel.addLog(datePicker.getValue(), (Integer) ratingPicker.getValue(), Integer.valueOf(hoursField.getText()), Integer.valueOf(minutesField.getText()), Integer.valueOf(difficultyField.getText()), commentArea.getText());
        closeWindow();
    }

    public void closeWindow() {
        Stage stage = (Stage) saveLogButton.getScene().getWindow();
        stage.close();
    }

}
