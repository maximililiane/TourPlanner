package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.TourLog;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AddLogWindowViewModelTest {
    static AddLogWindowViewModel viewModel;
    static TourLogService tourLogService;
    static TourService tourService;

    @BeforeAll
    static void initialize() {
        tourLogService = Mockito.mock(TourLogService.class);
        tourService = Mockito.mock(TourService.class);
        viewModel = new AddLogWindowViewModel(tourLogService, tourService);
        viewModel.setOldLog(Mockito.mock(TourLog.class));
    }

    @Test
    void addLog() {
        viewModel.addLog(12, 12, LocalDate.now(), 2, "tourname", 1);
        Mockito.verify(tourLogService).addTourLog(Mockito.any(TourLog.class));
    }

    @Test
    void editLog() {
        viewModel.editLog(12, 12, LocalDate.now(), 2, "tourname", 1);
        Mockito.verify(tourLogService).editTourLog(Mockito.any(TourLog.class));
    }
}