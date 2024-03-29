package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ImportWindowViewModel {

    private TourService tourService;
    private TourLogService tourLogService;
    private final LoggerWrapper logger = LoggerFactory.getLogger();


    public ImportWindowViewModel(TourService tourService, TourLogService tourLogService) {
        this.tourService = tourService;
        this.tourLogService = tourLogService;
    }

    public List<File> getJsonFileNamesFromDirectory() {
        File directory = new File("/importData");

        if (!directory.exists()) {
            directory.mkdir();
        }

        File dir = new File(directory.getName());
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        if (files != null)
            return Arrays.asList(files);
        else {
            logger.info("There are no JSON files in directory");
            return null;
        }
    }

    public void importData(File file) {
        Tour importedTour = tourService.importData(file);
        if (importedTour != null) {
            if (importedTour.getLogs() != null) {
                // imported tour has logs to be parsed
                tourLogService.importLogsByTourId(importedTour, importedTour.getLogs());
                logger.debug("User tries to import tour via file; Filepath: " + file.getPath());
            }
        }
    }
}
