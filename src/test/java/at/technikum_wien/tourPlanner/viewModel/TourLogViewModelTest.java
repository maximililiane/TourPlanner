package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TourLogViewModelTest {
    static TourLogViewModel viewModel;
    static TourLogService logService;
    static TourService tourService;

    @BeforeAll
    static void initialize() {
        logService = Mockito.mock(TourLogService.class);
        tourService = Mockito.mock(TourService.class);
        viewModel = new TourLogViewModel(logService, tourService);
    }

    @Test
    void deleteLog() {
        viewModel.deleteLog(Mockito.mock(TourLog.class));
        Mockito.verify(logService).deleteLog(Mockito.any(TourLog.class));
    }

    @Test
    void getTourNames() {
        viewModel.getTourNames();
        Mockito.verify(tourService).getObservableTourList();
    }

    @Test
    void getTourIdByName() {
        String test = "test";
        viewModel.getTourIdByName(test);
        Mockito.verify(tourService).getTourId(test);
    }
}