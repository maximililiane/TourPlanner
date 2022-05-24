package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.LogViewUtils.LogViewRow;
import at.technikum_wien.tourPlanner.listViewUtils.ListViewRow;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.viewModel.TourLogViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class TourLogWindowController {

    public Button addLogButton;
    private ObservableList<TourLog> logs;

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


    private final TourLogViewModel tourLogViewModel;

    public TourLogWindowController(TourLogViewModel tourLogViewModel) {
        this.tourLogViewModel = tourLogViewModel;
        this.logs = tourLogViewModel.getList();
        this.logs.addListener(new ListChangeListener<TourLog>() {
            @Override
            public void onChanged(Change<? extends TourLog> change) {
                setListToTable(logs);
            }
        });
    }

    @FXML
    public void initialize() {
        initializeTable();
        lockFields();
        setListToTable(tourLogViewModel.getList());
    }

    public void initializeTable() {
        associateColumns();
        logTable.setRowFactory(tv -> {
            TableRow<LogViewRow> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if(row.getItem()!=null) {
                    rowClicked(row.getItem());
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

    private void addListToTable(List<TourLog> list) {
        for (TourLog l : list) {
            addLogToTable(l);
        }
    }

    private void addLogToTable(TourLog l) {
        LogViewRow dataRow = new LogViewRow(l, tourLogViewModel.getTourNameById(l.getUid()));
        logTable.getItems().add(dataRow);
    }

    private void setListToTable(ObservableList<TourLog> list) {
        logTable.getItems().removeAll(logTable.getItems());
        for (TourLog t : list) {
            addLogToTable(t);
            System.out.println(t.toString());
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
}
