package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ObservableList;

public class TourLogViewModel {

    private TourLogService tourLogService;
    private ObservableList<TourLog> logs;
    private ObservableList<Tour> tours;

    public TourLogViewModel(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
        this.logs = tourLogService.getObservableLogList();
        this.tours = tourLogService.getObservableTourList();
    }

    public ObservableList<TourLog> getList() {
        return logs;
    }

    public void setList(ObservableList<TourLog> logs) {
        this.logs = logs;
    }

    public void addItem(TourLog item) {
        logs.add(item);
    }
}
