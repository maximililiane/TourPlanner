package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableName;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;

public class StartWindowViewModel {

    private final TourService tourService;
    private final TourLogService tourLogService;
    private final LoggerWrapper logger = LoggerFactory.getLogger();

    public StartWindowViewModel(TourService tourService, TourLogService tourLogService) {
        this.tourService = tourService;
        this.tourLogService = tourLogService;
    }

    public void startRegularTourPlanner() {
        logger.debug("Starting regular tour planner application.");
        tourService.setTourTableName(TableName.REGULAR_TOUR_TABLE_NAME);
        tourLogService.setTourLogTableName(TableName.REGULAR_TOUR_LOG_TABLE_NAME);
    }

    public void startDemoTourPlanner() {
        logger.debug("Starting demo tour planner application.");
        tourService.setTourTableName(TableName.DEMO_TOUR_TABLE_NAME);
        tourLogService.setTourLogTableName(TableName.DEMO_TOUR_LOG_TABLE_NAME);
        addDemoData();
    }

    private void addDemoData() {
        tourService.resetTourTable();
        tourLogService.resetLogTable();

        tourService.addDemoData();
        tourLogService.addDemoLogs();
    }

}
