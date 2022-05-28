package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ListViewViewModelTest {

    static ListViewViewModel viewModel;
    static TourLogService logService;
    static TourService tourService;

    @BeforeAll
    static void initialze() {
        tourService = Mockito.mock(TourService.class);
        logService = Mockito.mock(TourLogService.class);
        viewModel = new ListViewViewModel(logService, tourService);
    }

    @Test
    void getTourNameById() {
        int test = Mockito.any(Integer.class);
        viewModel.getTourNameById(test);
        Mockito.verify(tourService).getTourNameById(test);
    }
}