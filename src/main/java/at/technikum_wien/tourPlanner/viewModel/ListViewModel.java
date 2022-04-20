package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.proxyUtils.DBProxy;
import at.technikum_wien.tourPlanner.proxyUtils.TourSubscriber;

import java.util.LinkedList;

public class ListViewModel implements TourSubscriber {

    private LinkedList<ListViewTour> list= new LinkedList<ListViewTour>();
    private DBProxy dbProxy;

    public ListViewModel(DBProxy proxy) {
        this.list = new LinkedList<>();
        dbProxy= proxy;
        dbProxy.subscribeToTours(this);
        dbProxy.getTours();
    }

    public LinkedList<ListViewTour> getList() {
        return list;
    }
    public void addItem(ListViewTour item){
        list.add(item);
    }
    public void setList(LinkedList<Tour> l){
        LinkedList<ListViewTour> newList= convertTourToListViewTour(l);
        list= newList;
    }

    public DBProxy getDbProxy() {
        return dbProxy;
    }


    @Override
    public void notify(LinkedList<Tour> l) {
        setList(l);
    }

    private LinkedList<ListViewTour> convertTourToListViewTour(LinkedList<Tour> list){
        LinkedList<ListViewTour> newList= new LinkedList<>();
        for(ListViewTour t : list){
            newList.add(t);
        }
        return newList;
    }
}
