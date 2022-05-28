package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.FXMLDependencyInjection;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.viewModel.TourLogViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class TourLogWindowController {

    public ListView logListView;
    private ObservableList<TourLog> logs;

    @FXML
    public Button addLogButton;
    @FXML
    public Button editLogButton;
    @FXML
    public Button deleteLogButton;
    @FXML
    public Label dateField;
    @FXML
    public TextArea commentField;
    @FXML
    public Label difficultyField;
    @FXML
    public Label timeField;
    @FXML
    public Label tourNameField;
    @FXML
    public Label ratingField;
    @FXML
    public ChoiceBox<String> tourChooser;
    @FXML
    public Button filterTourButton;

    private int selectedLog;
    private int selectedTour;
    private ObservableList<Tour> tours;
    private boolean uninitialized;

    private final TourLogViewModel tourLogViewModel;

    public TourLogWindowController(TourLogViewModel tourLogViewModel) {
        this.tourLogViewModel = tourLogViewModel;
        this.selectedLog = -1;
        this.selectedTour = -1;
        this.uninitialized = true;
    }

    @FXML
    public void initialize() {
        this.tours = tourLogViewModel.getTourList();
        this.logs = tourLogViewModel.getLogList();
        this.tours.addListener((ListChangeListener<Tour>) change -> {
            setTourChooserValues();
            while (change.next()) {
                if (change.wasReplaced()) {
                    if (selectedLog != -1) {
                        logListView.getSelectionModel().select(selectedLog);
                        setValuesToFields(logs.get(selectedLog), tours.get(selectedTour));
                    }
                }
            }

        });

        this.logs.addListener(new ListChangeListener<TourLog>() {
            @Override
            public void onChanged(Change<? extends TourLog> change) {
                while (change.next()) {
                    if (change.wasReplaced()) {
                        if (selectedLog != -1) {
                            logListView.getSelectionModel().select(selectedLog);
                        }
                    }
                }

            }
        });

        setUpListView();

        updateListView();
        unHideButtons(false);
        initializeTourChooser();

    }

    private void initializeTourChooser(){
        tourChooser.getItems().add("Alle Touren");
        tourChooser.setValue("Alle Touren");
        setTourChooserValues();
        filterTourButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                filterTours(tourChooser.getValue());
            }
        });

    }

    private void setTourChooserValues(){
        tourChooser.getItems().removeAll(tourLogViewModel.getTourNames());
        tourChooser.getItems().addAll(tourLogViewModel.getTourNames());
    }

    // update view when new item on the list has been selected
    private void updateListView() {
        logListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TourLog>() {
            @Override
            public void changed(ObservableValue<? extends TourLog> observableValue, TourLog oldLog, TourLog newLog) {
                if (newLog != null) {
                    selectedLog = logListView.getSelectionModel().getSelectedIndex();
                    Tour newTour = tours.stream().filter(t -> t.getUid() == newLog.getTourID()).findFirst().get();
                    selectedTour = tours.indexOf(newTour);
                    setValuesToFields(newLog, newTour);
                    unHideButtons(true);
                } else {
                    // there are no more tours left in the database
                    selectedTour = -1;
                    selectedLog = -1;
                    removeValuesFromFields();
                    unHideButtons(false);
                }
            }
        });
    }

    private void setUpListView() {
        // set list data
        logListView.setItems(logs);

        // rename list cells to only show the name of the tour + log number
        logListView.setCellFactory(new Callback<ListView<TourLog>, ListCell<TourLog>>() {
            @Override
            public ListCell<TourLog> call(ListView<TourLog> tourListView) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(TourLog log, boolean empty) {
                        super.updateItem(log, empty);
                        if (empty || log == null) {
                            setText(null);
                        } else {
                            Tour tour = tours.stream().filter(t -> t.getUid() == log.getTourID()).findFirst().get();
                            setText(tour.getName() + " - Log: " + log.getUid());
                        }

                        // listen if tour has been edited
                        tours.addListener(new ListChangeListener<Tour>() {
                            @Override
                            public void onChanged(Change<? extends Tour> change) {
                                while (change.next()) {
                                    if (change.wasReplaced()) {
                                        if (empty || log == null) {
                                            setText(null);
                                        } else {
                                            Tour tour = tours.stream().filter(t -> t.getUid() == log.getTourID()).findFirst().get();
                                            setText(tour.getName() + " - Log: " + log.getUid());
                                        }
                                    }
                                }
                            }
                        });
                    }
                };
            }
        });
    }



    public void unHideButtons(boolean unHideButtons) {
        editLogButton.setVisible(unHideButtons);
        deleteLogButton.setVisible(unHideButtons);
    }

    private void setValuesToFields(TourLog log, Tour tour) {
        commentField.setText(log.getComment());
        difficultyField.setText(Integer.toString(log.getDifficulty()));
        tourNameField.setText(tour.getName());
        dateField.setText(log.getDate().toString());
        timeField.setText(log.getTotalTime().toString());
        ratingField.setText(Integer.toString(log.getRating()));
    }

    private void removeValuesFromFields() {
        commentField.setText("");
        difficultyField.setText("");
        tourNameField.setText("");
        dateField.setText("");
        timeField.setText("");
        ratingField.setText("");
        unHideButtons(false);
    }

    public void openAddLogWindow() {
        try {
            Parent root = FXMLDependencyInjection.load("addLogWindow.fxml", Locale.ENGLISH);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tour Planner");
            stage.setScene(scene);
            stage.setMinWidth(600.0);
            stage.setMinHeight(525.0);
            stage.setMaxHeight(525.0);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEditLogWindow() {

        try {
            FXMLLoader loader = FXMLDependencyInjection.getLoader("addLogWindow.fxml", Locale.ENGLISH);
            Parent root = loader.load();
            AddLogWindowController controller = loader.getController();
            TourLog log = (TourLog) logListView.getSelectionModel().getSelectedItem();
            int hours = log.getTotalTime().toLocalTime().getHour();
            int minutes = log.getTotalTime().toLocalTime().getMinute();

            controller.titleLabel.setText("Edit Log");
            controller.hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, hours));
            controller.minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, minutes));
            controller.difficultySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, log.getDifficulty()));
            controller.commentArea.setText(log.getComment());
            controller.datePicker.setValue(log.getDate().toLocalDate());
            controller.ratingPicker.setValue(log.getRating());

            Tour tour = tours.get(selectedTour);

            LinkedList<String> nameChoices = new LinkedList<String>();
            nameChoices.add(tour.getName());
            controller.tourNamePicker.setItems(FXCollections.observableList(nameChoices));
            controller.tourNamePicker.setValue(tour.getName());
            controller.setOldLog(log);

            controller.saveLogButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    controller.editLog();
                }
            });

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Tour Planner");
            stage.setScene(scene);
            stage.setMinWidth(600.0);
            stage.setMinHeight(525.0);
            stage.setMaxHeight(525.0);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLog() {
        tourLogViewModel.deleteLog((TourLog) logListView.getSelectionModel().getSelectedItem());
    }

    public void filterTours(String filteredTour){
        if(filteredTour.equals("Alle Touren")){
            logListView.setItems(logs);
        }
        else{
            logListView.setItems(logs.filtered(log -> log.getTourID()==tourLogViewModel.getTourIdByName(filteredTour)));
        }
    }
}
