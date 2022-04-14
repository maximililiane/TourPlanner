package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.models.ListViewTour;
import javafx.beans.property.ListProperty;

import java.util.LinkedList;

public class ListViewModel {
    private LinkedList<ListViewTour> list= new LinkedList<ListViewTour>();

    public LinkedList<ListViewTour> getList() {
        return list;
    }
    public void addItem(ListViewTour item){
        list.add(item);
    }
}
