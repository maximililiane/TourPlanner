package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public class DescriptionViewModel {

    private Injector injector;

    public DescriptionViewModel(Injector injector) {
        this.injector = injector;
    }

    public ObservableList<Tour> getTours() {
        LinkedList<Tour> tours = injector.getProxy().getToursTemporary();
        return FXCollections.observableArrayList(tours);
    }
}
