package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.models.ListViewTour;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.proxyUtils.DBProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ListViewModelTest {

    static ListViewModel listViewModel;
    static ListViewTour tour;
    static LinkedList<Tour> list;
    @BeforeAll
    static void initialSetup(){
        tour= Mockito.mock(ListViewTour.class);
        list= new LinkedList<Tour>();
        list.add(Mockito.mock(Tour.class));
        list.add(Mockito.mock(Tour.class));
    }

    @BeforeEach
    void testSetup(){
        listViewModel= new ListViewModel(Mockito.mock(DBProxy.class));
    }

    @Test
    void addItem() {
        Assertions.assertEquals(0, listViewModel.getList().size());
        listViewModel.addItem(tour);
        Assertions.assertEquals(1, listViewModel.getList().size());
    }

    @Test
    void setList() {
        Assertions.assertEquals(0, listViewModel.getList().size());
        listViewModel.setList(list);
        Assertions.assertEquals(2, listViewModel.getList().size());
    }

    @Test
    void testNotify() {
        Assertions.assertEquals(0, listViewModel.getList().size());
        listViewModel.notify(list);
        Assertions.assertEquals(2, listViewModel.getList().size());
    }
}