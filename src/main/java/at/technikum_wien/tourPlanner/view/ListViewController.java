package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.viewModel.ListViewModel;
import javafx.event.ActionEvent;

import java.util.List;

public class ListViewController {
    private final ListViewModel listViewModel;

    public ListViewController(ListViewModel listViewModel){
        this.listViewModel=listViewModel;
    }

    private void addListToTable(List<ListViewTour> list){
        for (ListViewTour t : list){
            addTourToTable(t);
        }
    }

    private void addTourToTable(ListViewTour t){
        //TODO: add new row
    }

    private void showDetailsButtonPressed(ActionEvent e){
        //TODO: show details
    }

    private void showLogsButtonPressed(ActionEvent e){
        //TODO: show logs
    }

    private void editTourButtonPressed(ActionEvent e){
        //TODO: edit Tour
    }

}
