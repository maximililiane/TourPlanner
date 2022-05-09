package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.proxyUtils.DBProxy;
import at.technikum_wien.tourPlanner.proxyUtils.TourProvider;
import at.technikum_wien.tourPlanner.proxyUtils.TourSubscriber;
import javafx.collections.ObservableList;

import java.util.LinkedList;

// public class ListViewModel implements TourProvider, TourSubscriber {
public class ListViewModel {
    //private LinkedList<Tour> list = new LinkedList<Tour>();
    //private DBProxy dbProxy;
    //private LinkedList<TourSubscriber> subscribers;

    private ObservableList<Tour> list;
    private TourService tourService;

    // public ListViewModel(DBProxy proxy){
    public ListViewModel(TourService tourService) {
        this.tourService = tourService;
        this.list = tourService.getObservableTourList();
//        dbProxy = proxy;
//        subscribers = new LinkedList<TourSubscriber>();
//        //dbProxy.subscribeToTours(this);
//        dbProxy.getTours();
    }

    public ObservableList<Tour> getList() {
        return list;
    }

    public void setList(ObservableList<Tour> list) {
        this.list = list;
    }

    public void addItem(Tour item) {
        list.add(item);
    }

    //public DBProxy getDbProxy() {
    //    return dbProxy;
    //}

    /*

    @Override
    public void notify(ObservableList<Tour> l) {
        // setList(l);
        notifyTourSubscribers(l);
    }

    @Override
    public void subscribeToTours(TourSubscriber t) {
        subscribers.add(t);
    }

    @Override
    public void unsubscribeTours(TourSubscriber t) {
        subscribers.remove(t);
    }

    @Override
    public void notifyTourSubscribers(ObservableList<Tour> l) {
        for (TourSubscriber t : subscribers) {
            t.notify(l);
        }
    }

     */
}
