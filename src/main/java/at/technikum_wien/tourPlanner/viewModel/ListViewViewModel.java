package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ObservableList;


public class ListViewViewModel {

    private ObservableList<TourLog> list;
    private ObservableList<Tour> tours;
    private TourLogService logService;
    private TourService tourService;

    // public ListViewModel(DBProxy proxy){
    public ListViewViewModel(TourLogService logService, TourService tourService) {
        this.logService = logService;
        this.list = logService.getObservableLogList();
        this.tours = tourService.getTours();
        this.tourService = tourService;
    }

    public String getTourNameById(int id) {
        return tourService.getTourNameById(id);
    }

    public ObservableList<TourLog> getList() {
        return list;
    }

    public void setList(ObservableList<TourLog> list) {
        this.list = list;
    }

    public ObservableList<Tour> getTours() {
        return tours;
    }

    public void addItem(TourLog item) {
        list.add(item);
    }
}
