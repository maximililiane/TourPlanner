package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;

public class TourLogViewModel {

    private TourLogService tourLogService;

    public TourLogViewModel(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
    }
}
