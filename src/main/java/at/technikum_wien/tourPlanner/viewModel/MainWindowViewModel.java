package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public class MainWindowViewModel {
    private final StringProperty searchString = new SimpleStringProperty("");
    private final BooleanBinding isSearchDisabledBinding = Bindings.createBooleanBinding(() -> searchString.get().isEmpty());
    private TourService tourService;
    private TourLogService tourLogService;

    public MainWindowViewModel(TourService tourService, TourLogService tourLogService) {
        this.tourService = tourService;
        this.tourLogService = tourLogService;
        searchString.addListener((arg, oldVal, newVal) -> isSearchDisabledBinding.invalidate());
    }

    public BooleanBinding searchDisabledBinding() {
        return isSearchDisabledBinding;
    }

    public StringProperty searchStringProperty() {
        return searchString;
    }

    public ObservableList<String> searchTours() {
        return FXCollections.observableList(tourService.searchTours(searchStringProperty().get()));
    }

}
