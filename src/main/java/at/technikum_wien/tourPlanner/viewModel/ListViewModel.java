package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.proxyUtils.DBProxy;
import at.technikum_wien.tourPlanner.proxyUtils.TourProvider;
import at.technikum_wien.tourPlanner.proxyUtils.TourSubscriber;

import java.util.LinkedList;

public class ListViewModel implements TourProvider, TourSubscriber {

    private LinkedList<Tour> list= new LinkedList<Tour>();
    private DBProxy dbProxy;
    private LinkedList<TourSubscriber> subscribers;

    public ListViewModel(DBProxy proxy) {
        this.list = new LinkedList<>();
        dbProxy= proxy;
        subscribers= new LinkedList<TourSubscriber>();
        dbProxy.subscribeToTours(this);
        dbProxy.getTours();
    }

    public LinkedList<Tour> getList() {
        return list;
    }
    public void addItem(Tour item){
        list.add(item);
    }
    public void setList(LinkedList<Tour> list){
        this.list=list;
    }

    public DBProxy getDbProxy() {
        return dbProxy;
    }


    @Override
    public void notify(LinkedList<Tour> l) {
        setList(l);
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
    public void notifyTourSubscribers(LinkedList<Tour> l) {
        for (TourSubscriber t : subscribers){
            t.notify(l);
        }
    }
}
