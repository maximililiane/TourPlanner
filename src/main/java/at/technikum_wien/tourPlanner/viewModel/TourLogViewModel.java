package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ObservableList;

public class TourLogViewModel {

    private TourLogService tourLogService;
    private ObservableList<TourLog> list;

    public TourLogViewModel(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
        this.list = tourLogService.getObservableLogList();
    }

    public ObservableList<TourLog> getList() {
        return list;
    }

    public void setList(ObservableList<TourLog> list) {
        this.list = list;
    }

    public void addItem(TourLog item) {
        list.add(item);
    }
}
