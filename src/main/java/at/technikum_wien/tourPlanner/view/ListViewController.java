package at.technikum_wien.tourPlanner.view;
import at.technikum_wien.tourPlanner.listViewUtils.CustomContextMenu;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.listViewUtils.ListViewRow;
import at.technikum_wien.tourPlanner.viewModel.ListViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

public class ListViewController {

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

    public ListViewTour test= new ListViewTour("testname", "startingpint", "destination", "time", "1234");
    public ListViewTour test2= new ListViewTour("testname", "startingpint", "destination", "time", "4567");

    private final ListViewModel listViewModel;

    @FXML
    public void addRow(){
        LinkedList<ListViewTour> l= new LinkedList<>();
        l.add(test);
        l.add(test2);
        addListToTable(l);
    }

    public ListViewController(ListViewModel listViewModel){
        this.listViewModel=listViewModel;

    }
    @FXML
    public void initialize(){
        initializeTable();
    }

    public void initializeTable(){

        associateColumns();
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

    public void associateColumns(){

        uidColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("uid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("name"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("startingPoint"));
        toColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("destination"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("estimatedTime"));
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
