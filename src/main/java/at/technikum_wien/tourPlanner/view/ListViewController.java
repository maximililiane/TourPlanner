package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.LogViewUtils.LogViewRow;
import at.technikum_wien.tourPlanner.listViewUtils.CustomContextMenu;
import at.technikum_wien.tourPlanner.listViewUtils.ListViewRow;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.viewModel.ListViewViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

// public class ListViewController implements TourSubscriber {

public class ListViewController {
    private ObservableList<TourLog> logs;
    private ObservableList<Tour> tours;
    @FXML
    public TableView tourTable;
    @FXML
    public TableColumn uidColumn;
    @FXML
    public TableColumn tourNameColumn;
    @FXML
    public TableColumn difficultyColumn;
    @FXML
    public TableColumn ratingColumn;
    @FXML
    public TableColumn totalTimeColumn;
    @FXML
    public TableColumn dateColumn;
    @FXML

    private final ListViewViewModel listViewViewModel;

    public ListViewController(ListViewViewModel listViewViewModel) {
        this.listViewViewModel = listViewViewModel;
        this.logs = listViewViewModel.getList();
        this.logs.addListener(new ListChangeListener<TourLog>() {
            @Override
            public void onChanged(Change<? extends TourLog> change) {
                setListToTable(logs);
            }
        });
        this.tours = listViewViewModel.getTours();
        this.tours.addListener(new ListChangeListener<Tour>() {
            @Override
            public void onChanged(Change<? extends Tour> change) {
                setListToTable(logs);
            }
        });
        // listViewModel.subscribeToTours(this);
    }

    @FXML
    public void initialize() {
        initializeTable();
        setListToTable(listViewViewModel.getList());
    }

    public void initializeTable() {
        associateColumns();
        tourTable.setRowFactory(tv -> {
            TableRow<LogViewRow> row = new TableRow<>();
            CustomContextMenu cm = new CustomContextMenu();
            cm.getShowLogDetailsItem().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(row.getItem()!=null) {
                        showLogButtonPressed(row.getItem().getUid());
                    }
                }
            });
            row.setContextMenu(cm.getCm());
            return row;
        });
    }

    public void associateColumns() {
        uidColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("uid"));
        tourNameColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("tourName"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("difficulty"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("rating"));
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("totalTime"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("date"));
    }

    private void addListToTable(List<TourLog> list) {
        for (TourLog t : list) {
            addLogToTable(t);
        }
    }

    private void addLogToTable(TourLog t) {
        LogViewRow row= new LogViewRow(t, listViewViewModel.getTourNameById(t.getTourID()));
        tourTable.getItems().add(row);
    }

    private void setListToTable(ObservableList<TourLog> list) {
        tourTable.getItems().removeAll(tourTable.getItems());
        for (TourLog t : list) {
            addLogToTable(t);
        }

    }

    private void showLogButtonPressed(int uid) {
    }

}
