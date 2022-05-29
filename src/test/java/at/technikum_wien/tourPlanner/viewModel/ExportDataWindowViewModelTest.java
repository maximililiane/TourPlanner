package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ExportDataWindowViewModelTest {

    static TourService tourService;
    static TourLogService logService;
    static ExportDataWindowViewModel viewModel;

    @BeforeAll
    static void initialize() {
        tourService = Mockito.mock(TourService.class);
        logService = Mockito.mock(TourLogService.class);
        viewModel = new ExportDataWindowViewModel(tourService, logService);
    }

    @Test
    void getTours() {
        viewModel.getTours();
        Mockito.verify(tourService).getObservableTourList();
    }

    @Test
    void exportData() {
        Tour tour = Mockito.mock(Tour.class);
        viewModel.exportData(tour);
        Mockito.verify(tourService).exportData(tour);
    }
}