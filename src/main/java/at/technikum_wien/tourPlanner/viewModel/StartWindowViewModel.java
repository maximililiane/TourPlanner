package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableName;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TransportMode;

public class StartWindowViewModel {

    private final TourService tourService;
    private final TourLogService tourLogService;

    public StartWindowViewModel(TourService tourService, TourLogService tourLogService) {
        this.tourService = tourService;
        this.tourLogService = tourLogService;
    }

    public void startRegularTourPlanner() {
        tourService.setTourTableName(TableName.REGULAR_TOUR_TABLE_NAME);
        tourLogService.setTourLogTableName(TableName.REGULAR_TOUR_LOG_TABLE_NAME);
    }

    public void startDemoTourPlanner() {
        tourService.setTourTableName(TableName.DEMO_TOUR_TABLE_NAME);
        tourLogService.setTourLogTableName(TableName.DEMO_TOUR_LOG_TABLE_NAME);
        addDemoData();
    }

    private void addDemoData() {
        if (!tourService.getTours().isEmpty() || !tourLogService.getTours().isEmpty()) {
            // there is still test data from the last application call, this needs to be deleted
            tourService.deleteAllTours();
            tourLogService.deleteAllLogs();
        }

        // TODO: change test data so it doesn't have to do an API call every time
        Tour testTourA = new Tour("Tour A", "Vienna", "Bratislava", TransportMode.BICYCLE, "This is a test description");
        tourService.addTour(testTourA);

    }

}
