package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ImportWindowViewModel {

    private TourService tourService;
    private TourLogService tourLogService;

    public ImportWindowViewModel(TourService tourService, TourLogService tourLogService) {
        this.tourService = tourService;
        this.tourLogService = tourLogService;
    }

    public List<File> getJsonFileNamesFromDirectory() {
        //TODO: get path from config?
        File directory = new File("/importData");

        if (!directory.exists()) {
            directory.mkdir();
        }

        File dir = new File(directory.getName());
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        if (files != null)
            return Arrays.asList(files);
        else
            return null;
    }

    public void importData(File file) {
        Tour importedTour = tourService.importData(file);
        if (importedTour != null) {
            if (importedTour.getLogs() != null) {
                // imported tour has logs to be parsed
                tourLogService.importLogsByTourId(importedTour.getUid(), importedTour.getLogs());
            }
        }
    }
}
