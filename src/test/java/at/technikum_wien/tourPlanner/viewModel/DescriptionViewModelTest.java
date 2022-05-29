package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionViewModelTest {

    static DescriptionViewModel viewModel;
    static TourService tourService;
    static Tour tour;

    @BeforeAll
    static void initialize(){
        tourService= Mockito.mock(TourService.class);
        viewModel=new DescriptionViewModel(tourService);
        tour = Mockito.mock(Tour.class);
    }

    @Test
    void deleteTour() {
        viewModel.deleteTour(tour.getName());
        Mockito.verify(tourService).deleteTour(tour.getName());
    }

    @Test
    void saveReport() {
        viewModel.saveReport(tour);
        Mockito.verify(tourService).saveReport(tour);

    }

    @Test
    void saveSummaryReport() {
        viewModel.saveSummaryReport(tour);
        Mockito.verify(tourService).saveSummaryReport(tour);

    }
}