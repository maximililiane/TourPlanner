package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AddTourWindowViewModelTest {

    static TourService tourService;
    static AddTourWindowViewModel viewModel;

    @BeforeAll
    static void initialize(){
        tourService= Mockito.mock(TourService.class);
        viewModel=new AddTourWindowViewModel(tourService);
    }

    @Test
    void addTour() {
        Tour tour= Mockito.mock(Tour.class);
        viewModel.addTour(tour);
        Mockito.verify(tourService).addTour(tour);
    }
}