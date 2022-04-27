package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.proxyUtils.DBProxy;
import at.technikum_wien.tourPlanner.proxyUtils.TourPlannerSubscriber;
import javafx.beans.property.ListProperty;

import java.util.LinkedList;

public class ListViewModel implements TourPlannerSubscriber {
    private LinkedList<ListViewTour> list = new LinkedList<ListViewTour>();
    private DBProxy dbProxy;

    public LinkedList<ListViewTour> getList() {
        return list;
    }

    public ListViewModel(Injector injector) {
        this.list = new LinkedList<>();
        dbProxy = injector.getProxy();
        dbProxy.subscribeToTours(this);
        dbProxy.getTours();
    }

    public void addItem(ListViewTour item) {
        list.add(item);
    }

    public DBProxy getDbProxy() {
        return dbProxy;
    }

    public void setList(LinkedList<ListViewTour> l) {
        list = l;
    }

    @Override
    public void notify(LinkedList<Tour> l) {
        LinkedList<ListViewTour> newList = new LinkedList<>();
        for (ListViewTour t : l) {
            newList.add(t);
        }
        setList(newList);
    }
}
