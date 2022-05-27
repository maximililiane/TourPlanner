package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.FXMLDependencyInjection;
import at.technikum_wien.tourPlanner.LogViewUtils.LogViewRow;
import at.technikum_wien.tourPlanner.listViewUtils.ListViewRow;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.viewModel.TourLogViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class TourLogWindowController {

    private ObservableList<TourLog> logs;

    @FXML
    public Button addLogButton;
    @FXML
    public Button editLogButton;
    @FXML
    public Button deleteLogButton;
    @FXML
    public TableView logTable;
    @FXML
    public TableColumn tourNameColumn;
    @FXML
    public TableColumn logNrColumn;
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

    LogViewRow selectedRow;


    private final TourLogViewModel tourLogViewModel;

    public TourLogWindowController(TourLogViewModel tourLogViewModel) {
        this.tourLogViewModel = tourLogViewModel;
    }

    @FXML
    public void initialize() {
        initializeTable();
        setListToTable(tourLogViewModel.getLogList());
        tourLogViewModel.getLogList().addListener(new ListChangeListener<TourLog>() {
            @Override
            public void onChanged(Change<? extends TourLog> change) {
                ObservableList<TourLog> newList = tourLogViewModel.getLogList();
                setListToTable(newList);
                if (selectedRow == null) {
                    return;
                }
                for (TourLog l : newList) {
                    if (l.getUid() == selectedRow.getUid()) {
                        selectedRow = new LogViewRow(l, tourLogViewModel.getTourNameById(l.getTourID()));
                        setValuesToFields(selectedRow);
                        return;
                    }
                }
            }
        });
        unHideButtons(false);
    }

    public void unHideButtons(boolean unHideButtons) {
        editLogButton.setVisible(unHideButtons);
        deleteLogButton.setVisible(unHideButtons);
    }

    public void initializeTable() {
        associateColumns();
        logTable.setRowFactory(tv -> {
            TableRow<LogViewRow> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (row.getItem() != null) {
                    setValuesToFields(row.getItem());
                    unHideButtons(true);
                    selectedRow = row.getItem();
                }
            });
            return row;
        });
    }


    public void associateColumns() {

        logNrColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("uid"));
        tourNameColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("tourName"));

    }

    private void addLogToTable(TourLog l) {
        LogViewRow dataRow = new LogViewRow(l, tourLogViewModel.getTourNameById(l.getTourID()));
        logTable.getItems().add(dataRow);
    }

    private void setListToTable(ObservableList<TourLog> list) {
        logTable.getItems().removeAll(logTable.getItems());
        for (TourLog t : list) {
            addLogToTable(t);
        }

    }

    private void setValuesToFields(LogViewRow r) {
        commentField.setText(r.getComment());
        difficultyField.setText(Integer.toString(r.getDifficulty()));
        tourNameField.setText(r.getTourName());
        dateField.setText(r.getDate().toString());
        timeField.setText(r.getTotalTime().toString());
        ratingField.setText(Integer.toString(r.getRating()));
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
            int hours = selectedRow.getTotalTime().toLocalTime().getHour();
            int minutes = selectedRow.getTotalTime().toLocalTime().getMinute();

            controller.titleLabel.setText("Edit Log");
            controller.hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, hours));
            controller.minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, minutes));
            controller.difficultySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, selectedRow.getDifficulty()));
            controller.commentArea.setText(selectedRow.getComment());
            controller.datePicker.setValue(selectedRow.getDate().toLocalDate());
            controller.ratingPicker.setValue(selectedRow.getRating());
            controller.tourNamePicker.setValue(selectedRow.getTourName());
            controller.setOldLog(selectedRow.getLog());

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
        tourLogViewModel.deleteLog(selectedRow.getLog());
        selectedRow = null;
        removeValuesFromFields();
    }
}
