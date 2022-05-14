package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.listViewUtils.CustomContextMenu;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.listViewUtils.ListViewRow;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.proxyUtils.TourProvider;
import at.technikum_wien.tourPlanner.proxyUtils.TourSubscriber;
import at.technikum_wien.tourPlanner.viewModel.ListViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

// public class ListViewController implements TourSubscriber {

public class ListViewController {
    private ObservableList<Tour> tours;
    @FXML
    public TableView tourTable;
    @FXML
    public TableColumn uidColumn;
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn fromColumn;
    @FXML
    public TableColumn toColumn;
    @FXML
    public TableColumn durationColumn;

    private final ListViewModel listViewModel;

    public ListViewController(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;
        this.tours = listViewModel.getList();
        this.tours.addListener(new ListChangeListener<Tour>() {
            @Override
            public void onChanged(Change<? extends Tour> change) {
                setListToTable(tours);
            }
        });
        // listViewModel.subscribeToTours(this);
    }

    @FXML
    public void initialize() {
        initializeTable();
        setListToTable(listViewModel.getList());
    }

    public void initializeTable() {
        associateColumns();
        tourTable.setRowFactory(tv -> {
            TableRow<ListViewRow> row = new TableRow<>();
            CustomContextMenu cm = new CustomContextMenu();
            cm.getDetailsMenuItem().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    detailsButtonPressed(row.getItem().getUid());
                }
            });
            cm.getLogsMenuItem().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    logsButtonPressed(row.getItem().getUid());
                }
            });
            cm.getEditMenuItem().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    editTourButtonPressed(row.getItem().getUid());
                }
            });
            row.setContextMenu(cm.getCm());
            return row;
        });
    }

    public void associateColumns() {

        uidColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("uid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("startingPoint"));
        toColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("destination"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("estimatedTime"));
    }

    private void addListToTable(List<Tour> list) {
        for (Tour t : list) {
            addTourToTable(t.getListViewTour());
        }
    }

    private void addTourToTable(ListViewTour t) {
        ListViewRow dataRow = new ListViewRow(t);
        tourTable.getItems().add(dataRow);
    }

    private void setListToTable(ObservableList<Tour> list) {
        tourTable.getItems().removeAll(tourTable.getItems());
        for (Tour t : list) {
            addTourToTable(t.getListViewTour());
        }

    }

    private void detailsButtonPressed(String uid) {
        System.out.println("pressed details button: " + uid);
        //TODO: show details
    }

    private void logsButtonPressed(String uid) {
        System.out.println("pressed logs button: " + uid);
        //TODO: show logs

    }

    private void editTourButtonPressed(String uid) {
        System.out.println("pressed edit button: " + uid);
        //TODO: edit Tour
    }

//    @Override
//    public void notify(LinkedList<Tour> l) {
//        setListToTable(l);
//    }

    /*
    @Override
    public void notify(ObservableList<Tour> l) {
        setListToTable(l);
    }

     */
}
