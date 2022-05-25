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
import java.util.List;
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
    public TextField dateField;
    @FXML
    public TextArea commentField;
    @FXML
    public TextField difficultyField;
    @FXML
    public TextField timeField;
    @FXML
    public TextField tourNameField;
    LogViewRow selectedRow;


    private final TourLogViewModel tourLogViewModel;

    public TourLogWindowController(TourLogViewModel tourLogViewModel) {
        this.tourLogViewModel = tourLogViewModel;
    }

    @FXML
    public void initialize() {
        initializeTable();
        lockFields();
        setListToTable(tourLogViewModel.getList());
        unHideButton(false);
    }

    public void unHideButton(boolean unHideButtons){
        editLogButton.setVisible(unHideButtons);
        deleteLogButton.setVisible(unHideButtons);

    }

    public void initializeTable() {
        associateColumns();
        logTable.setRowFactory(tv -> {
            TableRow<LogViewRow> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if(row.getItem()!=null) {
                    rowClicked(row.getItem());
                    unHideButton(true);
                    selectedRow=row.getItem();
                    System.out.println(row.getItem().toString());
                }
            });
            return row;
        });
    }

    public void lockFields(){
        dateField.setEditable(false);
        commentField.setEditable(false);
        difficultyField.setEditable(false);
        timeField.setEditable(false);
        tourNameField.setEditable(false);
    }

    public void unlockFields(){
        dateField.setEditable(true);
        commentField.setEditable(true);
        difficultyField.setEditable(true);
        timeField.setEditable(true);
        tourNameField.setEditable(true);
    }

    public void associateColumns() {

        logNrColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("uid"));
        tourNameColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("tourName"));

    }

    private void addLogToTable(TourLog l) {
        LogViewRow dataRow = new LogViewRow(l, tourLogViewModel.getTourNameById(l.getTourID()));
        System.out.println(dataRow);
        logTable.getItems().add(dataRow);
    }

    private void setListToTable(ObservableList<TourLog> list) {
        logTable.getItems().removeAll(logTable.getItems());
        for (TourLog t : list) {
            addLogToTable(t);
        }

    }

    private void rowClicked(LogViewRow r){
        setValuesToFields(r);
    }

    private void setValuesToFields(LogViewRow r){
        commentField.setText(r.getComment());
        difficultyField.setText(Integer.toString(r.getDifficulty()));
        tourNameField.setText(r.getTourName());
        dateField.setText(r.getDate().toString());
        timeField.setText(r.getTotalTime().toString());
    }

    public void openAddTourWindow() {
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

    public void openEditTourWindow() {

        try {
            FXMLLoader loader = FXMLDependencyInjection.getLoader("addLogWindow.fxml", Locale.ENGLISH);
            Parent root = loader.load();
            AddLogWindowController controller = loader.getController();
            int hours= selectedRow.getTotalTime().toLocalTime().getHour();
            int minutes= selectedRow.getTotalTime().toLocalTime().getMinute();

            controller.titleLabel.setText("Edit Log");
            controller.hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,hours));
            controller.minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,minutes));
            controller.difficultySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,selectedRow.getDifficulty()));
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

    public void deleteLog(){
        tourLogViewModel.deleteLog(selectedRow.getLog());
    }
}
