package at.technikum_wien.tourPlanner.viewModel;


import at.technikum_wien.tourPlanner.businessLayer.TourService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MainWindowViewModel {
    private final StringProperty searchString = new SimpleStringProperty("");
    private final BooleanBinding isSearchDisabledBinding = Bindings.createBooleanBinding(() -> searchString.get().isEmpty());
    private final TourService tourService;


    public MainWindowViewModel(TourService tourService) {
        this.tourService = tourService;
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
