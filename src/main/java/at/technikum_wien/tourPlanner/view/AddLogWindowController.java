package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.viewModel.AddLogWindowViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.LinkedList;

public class AddLogWindowController {

    private final AddLogWindowViewModel addLogWindowViewModel;

    @FXML
    public Button saveLogButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Spinner<Integer> hoursSpinner;
    @FXML
    public Spinner<Integer> minutesSpinner;
    @FXML
    public TextArea commentArea;
    @FXML
    public DatePicker datePicker;
    @FXML
    public ChoiceBox<Integer> ratingPicker;
    @FXML
    public ChoiceBox<String> tourNamePicker;
    @FXML
    public Spinner<Integer> difficultySpinner;

    public AddLogWindowController(AddLogWindowViewModel addLogWindowViewModel) {
        this.addLogWindowViewModel = addLogWindowViewModel;
    }

    @FXML
    public void initialize() {
        initializeView();
        bindProperties();
        initializeSpinner();

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

    private void initializeView() {
        prepareView();
        setChoicePickerValues();
    }

    private void bindProperties() {
        commentArea.textProperty().bindBidirectional(addLogWindowViewModel.getCommentField());
    }

    private void initializeSpinner() {
        hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,12));
        minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,30));
        difficultySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,50));
        hoursSpinner.setEditable(true);
        minutesSpinner.setEditable(true);
        difficultySpinner.setEditable(true);
    }

    public void prepareView(){
        tourNamePicker.getItems().removeAll(tourNamePicker.getItems());
        commentArea.setText("");
        datePicker.setValue(LocalDate.now());
        ratingPicker.setValue(1);
    }

    public void setChoicePickerValues(){
        tourNamePicker.setItems(addLogWindowViewModel.getTourNames());
        tourNamePicker.setValue(tourNamePicker.getItems().get(0));
        LinkedList<Integer> l= new LinkedList<>();
        for(int i=1; i<=5; i++){
            l.add(i);
        }
        ratingPicker.setItems(FXCollections.observableList(l));
    }

    public void addLog() {
        addLogWindowViewModel.addLog(hoursSpinner.getValue(), minutesSpinner.getValue(), datePicker.getValue(), ratingPicker.getValue(), tourNamePicker.getValue(), difficultySpinner.getValue());
        closeWindow();
        initializeView();
    }

    public void closeWindow() {
        Stage stage = (Stage) saveLogButton.getScene().getWindow();
        stage.close();
    }

}
