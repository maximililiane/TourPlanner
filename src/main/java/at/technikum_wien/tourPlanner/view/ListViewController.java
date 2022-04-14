package at.technikum_wien.tourPlanner.view;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.listViewUtils.ListViewRow;
import at.technikum_wien.tourPlanner.viewModel.ListViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ListViewController {

    @FXML
    public TableView TourTable;
    public TableColumn uidColumn;
    public TableColumn nameColumn;
    public TableColumn fromColumn;
    public TableColumn toColumn;
    public TableColumn durationColumn;

    public ListViewTour test= new ListViewTour("testname", "startingpint", "destination", "time", "1234");

    private final ListViewModel listViewModel;

    @FXML
    public void addRow(){
        initializeTable();
        addTourToTable(test);
    }

    public ListViewController(ListViewModel listViewModel){
        this.listViewModel=listViewModel;

    }

    private void addListToTable(List<ListViewTour> list){
        for (ListViewTour t : list){
            addTourToTable(t);
        }

    }

    private void addTourToTable(ListViewTour t){

        ListViewRow dataRow= new ListViewRow(t);

        TourTable.getItems().add(dataRow);
    }

    public void initializeTable(){

        createColumns();
        TourTable.getColumns().addAll(uidColumn, nameColumn, fromColumn, toColumn, durationColumn);
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
