package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.viewModel.ListViewModel;

import java.util.List;

public class ListViewController {
    private final ListViewModel listViewModel;

    public ListViewController(ListViewModel listViewModel){
        this.listViewModel=listViewModel;
    }

    private void addListToTable(List<ListViewTour> list){
        for (ListViewTour t : list){
            //do stuff
        }
    }

}
