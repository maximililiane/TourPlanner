package at.technikum_wien.tourPlanner.view;
import at.technikum_wien.tourPlanner.listViewUtils.CustomContextMenu;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.listViewUtils.ListViewRow;
import at.technikum_wien.tourPlanner.viewModel.ListViewModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.util.LinkedList;
import java.util.List;

public class ListViewController {

    @FXML
    public TableView tourTable;
    public TableColumn uidColumn;
    public TableColumn nameColumn;
    public TableColumn fromColumn;
    public TableColumn toColumn;
    public TableColumn durationColumn;

    public ListViewTour test= new ListViewTour("testname", "startingpint", "destination", "time", "1234");
    public ListViewTour test2= new ListViewTour("testname", "startingpint", "destination", "time", "4567");

    private final ListViewModel listViewModel;

    @FXML
    public void addRow(){
        initializeTable();
        LinkedList<ListViewTour> l= new LinkedList<>();
        l.add(test);
        l.add(test2);
        addListToTable(l);
    }

    public ListViewController(ListViewModel listViewModel){
        this.listViewModel=listViewModel;
        initializeTable();

    }

    private void addListToTable(List<ListViewTour> list){
        for (ListViewTour t : list){
            System.out.println(t.getUid());
            addTourToTable(t);
        }

    }

    private void addTourToTable(ListViewTour t){

        ListViewRow dataRow= new ListViewRow(t);
        tourTable.getItems().add(dataRow);
    }

    public void initializeTable(){

        createColumns();
        tourTable.getColumns().addAll(uidColumn, nameColumn, fromColumn, toColumn, durationColumn);
        tourTable.setRowFactory(tv -> {
            TableRow<ListViewRow> row = new TableRow<>();
            CustomContextMenu cm= new CustomContextMenu();
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
            return row ;
        });
    }

    public void createColumns(){

        uidColumn= new TableColumn("uid");
        nameColumn= new TableColumn("name");
        fromColumn= new TableColumn("from");
        toColumn= new TableColumn("to");
        durationColumn= new TableColumn("duration");

        uidColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("uid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("startingPoint"));
        toColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("destination"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("estimatedTime"));
    }

    private void detailsButtonPressed(String uid){
        System.out.println("pressed details button: " + uid);
        //TODO: show details
    }

    private void logsButtonPressed(String uid){
        System.out.println("pressed logs button: " +uid);
        //TODO: show logs

    }

    private void editTourButtonPressed(String uid){
        System.out.println("pressed edit button: " + uid);
        //TODO: edit Tour
    }

}
