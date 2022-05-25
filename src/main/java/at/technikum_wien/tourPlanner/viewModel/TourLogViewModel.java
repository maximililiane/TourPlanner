package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ObservableList;

public class TourLogViewModel {

    private TourLogService tourLogService;
    private TourService tourService;
    private ObservableList<TourLog> logs;
    private ObservableList<Tour> tours;

    public TourLogViewModel(TourLogService tourLogService, TourService tourService) {
        this.tourLogService = tourLogService;
        this.tourService=tourService;
        this.logs = tourLogService.getObservableLogList();
        this.tours = tourLogService.getObservableTourList();
    }

    public ObservableList<TourLog> getList() {
        return logs;
    }

    public void setList(ObservableList<TourLog> logs) {
        this.logs = logs;
    }


    public String getTourNameById(int id){
        return tourService.getTourNameById(id);
    }

    public void deleteLog(TourLog log) {
        tourLogService.deleteLog(log);
    }
}
